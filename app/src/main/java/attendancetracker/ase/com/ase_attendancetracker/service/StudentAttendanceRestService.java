package attendancetracker.ase.com.ase_attendancetracker.service;

import org.restlet.resource.ClientResource;

import java.io.IOException;

/**
 * Created by Sangeeta on 17-12-2017.
 */

public class StudentAttendanceRestService {

    private String url = "https://radiant-land-185414.appspot.com/rest/";

    public String getAttendanceDetails(String studentId) throws IOException {
        //String str = new ClientResource(url + "students/" + studentId +"/attendances").get().getText();
        return "[{\"id\":5642554087309312,\"studentId\":\"115816437810331465568\",\"groupId\":5639445604728832,\"weekId\":8,\"presented\":false,\"token\":\"\"},{\"id\":5648554290839552,\"studentId\":\"115816437810331465568\",\"groupId\":5639445604728832,\"weekId\":11,\"presented\":false,\"token\":\"\"},{\"id\":5649050225344512,\"studentId\":\"115816437810331465568\",\"groupId\":5639445604728832,\"weekId\":13,\"presented\":false,\"token\":\"\"},{\"id\":5668600916475904,\"studentId\":\"115816437810331465568\",\"groupId\":5639445604728832,\"weekId\":5,\"presented\":false,\"token\":\"\"},{\"id\":5676073085829120,\"studentId\":\"115816437810331465568\",\"groupId\":5639445604728832,\"weekId\":2,\"presented\":false,\"token\":\"\"},{\"id\":5697423099822080,\"studentId\":\"115816437810331465568\",\"groupId\":5639445604728832,\"weekId\":7,\"presented\":false,\"token\":\"\"},{\"id\":5710239819104256,\"studentId\":\"115816437810331465568\",\"groupId\":5639445604728832,\"weekId\":6,\"presented\":false,\"token\":\"\"},{\"id\":5739407210446848,\"studentId\":\"115816437810331465568\",\"groupId\":5639445604728832,\"weekId\":4,\"presented\":false,\"token\":\"\"},{\"id\":5741031244955648,\"studentId\":\"115816437810331465568\",\"groupId\":5639445604728832,\"weekId\":9,\"presented\":false,\"token\":\"\"},{\"id\":5742796208078848,\"studentId\":\"115816437810331465568\",\"groupId\":5639445604728832,\"weekId\":10,\"presented\":false,\"token\":\"\"},{\"id\":5749328048029696,\"studentId\":\"115816437810331465568\",\"groupId\":5639445604728832,\"weekId\":12,\"presented\":false,\"token\":\"\"},{\"id\":5760820306771968,\"studentId\":\"115816437810331465568\",\"groupId\":5639445604728832,\"weekId\":1,\"presented\":false,\"token\":\"\"},{\"id\":5769015641243648,\"studentId\":\"115816437810331465568\",\"groupId\":5639445604728832,\"weekId\":3,\"presented\":false,\"token\":\"\"}]";
    }
}
