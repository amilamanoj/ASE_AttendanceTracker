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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import attendancetracker.ase.com.ase_attendancetracker.R;
import attendancetracker.ase.com.ase_attendancetracker.model.AttendanceDetails;
import attendancetracker.ase.com.ase_attendancetracker.service.ClassScheduleRestService;
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
            try {
                return new ClassScheduleRestService(getApplicationContext()).getClassScheduleList(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }

        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            if(result != null) {
                Gson gson = new GsonBuilder().setDateFormat("MM/dd/yy HH:mm a").setPrettyPrinting().create();

                attendanceDetailsArrayList = gson.fromJson(result, new TypeToken<ArrayList<AttendanceDetails>>() {
                }.getType());
                Collections.sort(attendanceDetailsArrayList);
            }
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
