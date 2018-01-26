package attendancetracker.ase.com.ase_attendancetracker.service;

import android.content.Context;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.restlet.data.Cookie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import attendancetracker.ase.com.ase_attendancetracker.util.PropertyUtil;
import attendancetracker.ase.com.ase_attendancetracker.view.adapter.ClassScheduleListAdapter;

/**
 * Created by Sangeeta on 23-01-2018.
 */

public class ClassScheduleRestService {

    private String url;

    public ClassScheduleRestService(Context context) throws IOException {
        url = PropertyUtil.getProperty("classScheduleURL", context);
    }

    public String getClassScheduleList(String studentId,String sessionId)
    {
        OkHttpClient client = new OkHttpClient();
        String rawAnswer = null;

        String attendanceUrl = url + "students/" + studentId + "/attendances";
        try {

            Cookie cookie = new Cookie();
            cookie.setValue(sessionId);
            Request request = new Request.Builder()
                    .url(attendanceUrl).addHeader("cookie","SESSION-COOKIE="+sessionId)
                    .build();
            Response response = client.newCall(request).execute();
            rawAnswer = response.body().string();
        } catch (Exception e) {
            Log.e("RetrievingAttendancesError", e.getMessage());
        }
        return rawAnswer;
    }
}
