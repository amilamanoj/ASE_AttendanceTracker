package attendancetracker.ase.com.ase_attendancetracker.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
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

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("ASE", Context.MODE_PRIVATE);
        String userId = sharedPref.getString("userId","");
        String sessionId = sharedPref.getString("sessionId","");

        new AttendanceDetailsListActivity.AttendanceListAsyncTask().execute(userId,sessionId);
    }



    private class AttendanceListAsyncTask extends AsyncTask<String, String, String> {

        ProgressDialog progressDialog;

        protected String doInBackground(String... params) {
            publishProgress("Sleeping...");
            try {
                return new ClassScheduleRestService(getApplicationContext()).getClassScheduleList(params[0],params[1]);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }

        protected void onPostExecute(String result) {

            try {
                if (result != null) {
                    Gson gson = new GsonBuilder().setDateFormat("MM/dd/yy HH:mm a").setPrettyPrinting().create();
                    attendanceDetailsArrayList = gson.fromJson(result, new TypeToken<ArrayList<AttendanceDetails>>() {
                    }.getType());
                    Collections.sort(attendanceDetailsArrayList);
                }
            }
            catch (JsonSyntaxException e)
            {
                e.printStackTrace();
            }
            adapter = new ClassScheduleListAdapter(attendanceDetailsArrayList);
            recyclerView.setAdapter(adapter);
            progressDialog.dismiss();
        }

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(AttendanceDetailsListActivity.this,
                    "ProgressDialog",
                    "Loading");
        }
    }
}
