package attendancetracker.ase.com.ase_attendancetracker.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import attendancetracker.ase.com.ase_attendancetracker.R;

public class HomeActivity extends AppCompatActivity {

    private LinearLayout qrCodeLayout, scheduleclassList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        qrCodeLayout = findViewById(R.id.qr_code_layout);
        scheduleclassList = findViewById(R.id.class_schedule_list_layout);

        qrCodeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AttendanceDetailsListActivity.class);
                startActivity(intent);
            }
        });

        scheduleclassList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AttendanceDetailsListActivity.class);
                startActivity(intent);
            }
        });
    }
}
