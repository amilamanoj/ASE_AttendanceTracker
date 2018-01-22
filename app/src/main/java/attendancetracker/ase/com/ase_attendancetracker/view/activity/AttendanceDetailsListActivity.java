package attendancetracker.ase.com.ase_attendancetracker.view.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.ArrayList;
import java.util.Collections;

import attendancetracker.ase.com.ase_attendancetracker.R;
import attendancetracker.ase.com.ase_attendancetracker.model.AttendanceDetails;
import attendancetracker.ase.com.ase_attendancetracker.view.adapter.AttendancesListViewAdapter;
import attendancetracker.ase.com.ase_attendancetracker.view.adapter.ClassScheduleListAdapter;

public class AttendanceDetailsListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    private ArrayList<AttendanceDetails> attendanceDetailsArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_detials_list);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        new AttendanceDetailsListActivity.GetAttendancesHandler().execute("115816437810331465568");
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
            Gson gson = new GsonBuilder().setDateFormat("MM/dd/yy HH:mm a").setPrettyPrinting().create();

             attendanceDetailsArrayList = gson.fromJson(result, new TypeToken<ArrayList<AttendanceDetails>>() {
            }.getType());
            Collections.sort(attendanceDetailsArrayList);
            adapter = new ClassScheduleListAdapter(attendanceDetailsArrayList);
            recyclerView.setAdapter(adapter);
        }

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(AttendanceDetailsListActivity.this,
                    "ProgressDialog",
                    "Loading");
        }
    }
}
