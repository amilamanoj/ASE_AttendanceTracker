package attendancetracker.ase.com.ase_attendancetracker.service;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import attendancetracker.ase.com.ase_attendancetracker.util.PropertyUtil;
import attendancetracker.ase.com.ase_attendancetracker.view.adapter.ClassScheduleListAdapter;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Sangeeta on 23-01-2018.
 */

public class ClassScheduleRestService {

    private String url;
    private OkHttpClient client;
    private Context context;

    protected ClassScheduleRestService(Context context, OkHttpClient client) throws IOException {
        this.context = context;
        this.client = client;
    }

    protected String getClassScheduleList(String studentId,String sessionId)
    {
        String rawAnswer = null;
        try {
            url = PropertyUtil.getProperty("classScheduleURL", context);
            String attendanceUrl = url + "students/" + studentId + "/attendances";
            Request request = new Request.Builder()
                    .url(attendanceUrl)
                    .build();
            Response response = client.newCall(request).execute();
            rawAnswer = response.body().string();
        } catch (Exception e) {
            Log.e("RetrievingAttendancesError", e.getMessage());
        }
        return rawAnswer;
    }
}
