package attendancetracker.ase.com.ase_attendancetracker.service;

import android.content.Context;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.resource.ClientResource;

import java.io.IOException;

import attendancetracker.ase.com.ase_attendancetracker.util.PropertyUtil;

/**
 * Created by Sangeeta on 15-01-2018.
 */

public class LoginService {
    String url;

    public LoginService(Context context) throws IOException {
        url = PropertyUtil.getProperty("classScheduleURL",context);
    }

    public String authenticateUser(String email, String password) throws IOException, JSONException {
        url = url +"login";
        JSONObject json = new JSONObject();
        json.put("email",email);
        json.put("password",password);
        RequestBody body = RequestBody.create(com.squareup.okhttp.MediaType.parse("application/json; charset=utf-8"), json.toString());
        OkHttpClient client = new OkHttpClient();
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
