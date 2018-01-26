package attendancetracker.ase.com.ase_attendancetracker.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import attendancetracker.ase.com.ase_attendancetracker.R;
import attendancetracker.ase.com.ase_attendancetracker.model.AttendanceDetails;
import attendancetracker.ase.com.ase_attendancetracker.service.ClassScheduleRestService;
import attendancetracker.ase.com.ase_attendancetracker.view.adapter.ClassScheduleListAdapter;

public class HomeActivity extends AppCompatActivity {

    private LinearLayout qrCodeLayout, scheduleclassList, bonusAnalysis;
    private ArrayList<AttendanceDetails> attendanceDetailsArrayList;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("ASE", Context.MODE_PRIVATE);
        userId = sharedPref.getString("userId","");

        final String sessionId = sharedPref.getString("sessionId","");
        qrCodeLayout = findViewById(R.id.qr_code_layout);
        scheduleclassList = findViewById(R.id.class_schedule_list_layout);
        bonusAnalysis = findViewById(R.id.attendance_analysis);

        qrCodeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AttendanceListAsyncTask().execute(userId,sessionId);
            }
        });

        scheduleclassList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AttendanceDetailsListActivity.class);
                startActivity(intent);
            }
        });

        bonusAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SchedulePieChartActivity.class);
                startActivity(intent);
            }
        });
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

            AttendanceDetails attendanceDetails = getCurrentWeekDetails(attendanceDetailsArrayList);
            Intent intent = new Intent(HomeActivity.this, DisplayQRCodeActivity.class);
            intent.putExtra("attendanceDetails", (Serializable) attendanceDetails);

            progressDialog.dismiss();
            startActivity(intent);
        }



        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(HomeActivity.this,
                    "ProgressDialog",
                    "Loading");
        }
    }

    private AttendanceDetails getCurrentWeekDetails(ArrayList<AttendanceDetails> attendanceDetailsArrayList) {
        AttendanceDetails attendanceDetails = attendanceDetailsArrayList.get(0);
        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.setTime(new Date()); // sets calendar time/date
        //cal.add(Calendar.DAY_OF_YEAR, -7);
        cal.add(Calendar.HOUR, -3);

        for (AttendanceDetails a : attendanceDetailsArrayList)
        {
            if(a.getDate().compareTo(cal.getTime()) > 0)
                return a;
        }

        return  attendanceDetails;
    }
}

