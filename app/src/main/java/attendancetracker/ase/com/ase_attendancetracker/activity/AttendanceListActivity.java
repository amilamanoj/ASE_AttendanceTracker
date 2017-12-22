package attendancetracker.ase.com.ase_attendancetracker.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.ArrayList;
import java.util.Collections;

import attendancetracker.ase.com.ase_attendancetracker.R;
import attendancetracker.ase.com.ase_attendancetracker.model.Attendance;
import attendancetracker.ase.com.ase_attendancetracker.view.AttendancesListViewAdapter;

public class AttendanceListActivity extends AppCompatActivity {

    public static String TOKEN;
    public static String STUDENT_ID = "studentId";
    public static String WEEK_ID = "weekId";

    private ListView tutorialListView;
    private ArrayList<Attendance> appointments;
    ImageView myImageView;

    private String studentId = "115816437810331465568";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_list);
        tutorialListView = (ListView) findViewById(R.id.tutorialListId);
        new GetAttendancesHandler().execute("115816437810331465568");
        //myImageView = (ImageView) findViewById(R.id.imageView);
        //myImageView.setImageResource(R.drawable.ic_launcher_background);
        tutorialListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(AttendanceListActivity.this, DisplayQRCodeActivity.class);
                Attendance ad = appointments.get(position);
                intent.putExtra(STUDENT_ID, studentId);
                intent.putExtra(WEEK_ID, ad.getWeekId());
                startActivity(intent);
            }
        });
    }

    private class GetAttendancesHandler extends AsyncTask<String, String, String> {

        ProgressDialog progressDialog;
        private String url = "https://radiant-land-185414.appspot.com/rest/";

        protected String doInBackground(String... params) {
            publishProgress("Sleeping...");
            String studentId = params[0];
            String attendancesUrl = url + "students/" + studentId + "/attendances";
            OkHttpClient client = new OkHttpClient();
            String rawAnswer = null;
            try {
                Request request = new Request.Builder()
                        .url(attendancesUrl)
                        .build();
                Response response = client.newCall(request).execute();
                rawAnswer = response.body().string();
            } catch (Exception e) {
                Log.e("RetrievingAttendancesError", e.getMessage());
            }
            return rawAnswer;
        }

        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            Gson gson = new Gson();
            appointments = gson.fromJson(result, new TypeToken<ArrayList<Attendance>>() {
            }.getType());
            Collections.sort(appointments);
            //textView.setText(appointments[0].getStudentId().toString());
            AttendancesListViewAdapter adapter = new AttendancesListViewAdapter(AttendanceListActivity.this, appointments);
            tutorialListView.setAdapter(adapter);
        }

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(AttendanceListActivity.this,
                    "ProgressDialog",
                    "Loading");
        }
    }


}
