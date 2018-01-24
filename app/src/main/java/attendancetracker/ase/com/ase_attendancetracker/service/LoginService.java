package attendancetracker.ase.com.ase_attendancetracker.service;

import android.content.Context;
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

    public String authenticateUser(String user, String password) throws IOException {
        url = url +"/authenticateUser?_id="+ user + "&password=" +password;
        String rawAnswer = new ClientResource(url).get().getText();
        return rawAnswer;
    }
}
