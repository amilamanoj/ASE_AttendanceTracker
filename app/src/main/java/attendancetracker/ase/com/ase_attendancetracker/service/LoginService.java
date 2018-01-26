package attendancetracker.ase.com.ase_attendancetracker.service;

import android.content.Context;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import attendancetracker.ase.com.ase_attendancetracker.util.PropertyUtil;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Sangeeta on 15-01-2018.
 */

public class LoginService {
    private String url;
    private OkHttpClient client;

    protected LoginService(Context context, OkHttpClient client) throws IOException {
        url = PropertyUtil.getProperty("classScheduleURL",context);
        url = url +"login";
        this.client = client;
    }

    protected String authenticateUser(String email, String password) throws IOException, JSONException {
        JSONObject json = new JSONObject();
        json.put("email",email);
        json.put("password",password);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if(response.code() == 200)
            return response.body().string();
        else
            return null;
    }
}
