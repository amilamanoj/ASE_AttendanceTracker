package attendancetracker.ase.com.ase_attendancetracker.service;

import org.restlet.resource.ClientResource;

import java.io.IOException;

/**
 * Created by Sangeeta on 17-12-2017.
 */

public class StudentAttendanceRestService {

    private String url = "https://radiant-land-185414.appspot.com/rest/";

    public String getAttendanceDetails(String studentId) throws IOException {
        String str = new ClientResource(url + "students/" + studentId +"/attendances").get().getText();
        return str;
    }
}
