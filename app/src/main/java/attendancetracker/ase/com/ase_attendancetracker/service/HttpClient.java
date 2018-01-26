package attendancetracker.ase.com.ase_attendancetracker.service;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import attendancetracker.ase.com.ase_attendancetracker.util.PropertyUtil;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Tobias on 1/26/2018.
 */

public class HttpClient {

    private OkHttpClient client;
    private LoginService loginService;
    private ClassScheduleRestService classScheduleRestService;

   private static HttpClient instance;

    public static HttpClient getInstance(Context context) throws IOException {
        if(instance == null){
                instance = new HttpClient(context);
        }
        return instance;
    }

    private HttpClient(Context context) throws IOException {
        this.client = new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {
                    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(url.host(), cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(url.host());
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                })
                .build();
        this.classScheduleRestService = new ClassScheduleRestService(context, client);
        this.loginService = new LoginService(context, client);
    }

    public String authenticateUser(String email, String password) throws IOException, JSONException {
        return loginService.authenticateUser(email,password);
    }

    public String getClassScheduleList(String studentId,String sessionId){
        return  classScheduleRestService.getClassScheduleList(studentId, sessionId);
    }

    public byte[] getQRCode(URL url) {
        byte[] qrCodeByte = new byte[0];
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            qrCodeByte = response.body().bytes();
        } catch (Exception e) {
            Log.e("RetrievingAttendancesError", e.getMessage());
        }
        return qrCodeByte;
    }
}
