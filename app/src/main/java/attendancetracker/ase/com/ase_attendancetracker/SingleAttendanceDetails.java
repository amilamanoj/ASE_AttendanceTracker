package attendancetracker.ase.com.ase_attendancetracker;

import android.support.annotation.NonNull;

/**
 * Created by Sangeeta on 17-12-2017.
 */

public class SingleAttendanceDetails implements Comparable{


    private String id;
    private String studentId;
    private int weekId;
    private boolean presented;
    private String token;

    @Override
    public String toString() {
        return "Week " + weekId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getId() {
        return id;

    }

    public void setId(String id) {
        this.id = id;
    }

    public int getWeekId() {
        return weekId;
    }

    public void setWeekId(int weekId) {
        this.weekId = weekId;
    }

    public boolean isPresented() {
        return presented;
    }

    public void setPresented(boolean presented) {
        this.presented = presented;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        int compareage=((SingleAttendanceDetails)o).getWeekId();
        /* For Ascending order*/
        return this.weekId-compareage;
    }

}
