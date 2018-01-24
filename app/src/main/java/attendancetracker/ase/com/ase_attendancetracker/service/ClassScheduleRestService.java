package attendancetracker.ase.com.ase_attendancetracker.service;

import android.content.Context;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import attendancetracker.ase.com.ase_attendancetracker.util.PropertyUtil;
import attendancetracker.ase.com.ase_attendancetracker.view.adapter.ClassScheduleListAdapter;

/**
 * Created by Sangeeta on 23-01-2018.
 */

public class ClassScheduleRestService {

    private String url;

    public ClassScheduleRestService(Context context) throws IOException {
        url = PropertyUtil.getProperty("resURL", context);
    }

    public String getClassScheduleList(String studentId)
    {
        OkHttpClient client = new OkHttpClient();
        String rawAnswer = null;

        String attendancesUrl = url + "students/" + studentId + "/attendances";
        try {
            Request request = new Request.Builder()
                    .url(attendancesUrl)
                    .build();
            Response response = client.newCall(request).execute();
            rawAnswer = response.body().string();
        } catch (Exception e) {
            Log.e("RetrievingAttendancesError", e.getMessage());
        }
        return rawAnswer;
    }
}
