package attendancetracker.ase.com.ase_attendancetracker.model;

import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by Sangeeta on 17-12-2017.
 */

public class AttendanceDetails implements Comparable{


    private String id;
    private String studentId;
    private int weekId;
    private boolean presented;
    private String token;
    private Date date;
    private boolean attended;

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
        int compareage=((AttendanceDetails)o).getWeekId();
        /* For Ascending order*/
        return this.weekId-compareage;
    }

    public boolean isAttended() {
        return attended;
    }

    public void setAttended(boolean attended) {
        this.attended = attended;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
