package attendancetracker.ase.com.ase_attendancetracker.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.text.SimpleDateFormat;

import attendancetracker.ase.com.ase_attendancetracker.R;
import attendancetracker.ase.com.ase_attendancetracker.model.AttendanceDetails;
import attendancetracker.ase.com.ase_attendancetracker.service.HttpClient;
import attendancetracker.ase.com.ase_attendancetracker.util.PropertyUtil;

public class DisplayQRCodeActivity extends AppCompatActivity {

    URL url;

    TextView date, location, message;
    AttendanceDetails attendanceDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_qrcode);
        Intent intent = getIntent();
        date = findViewById(R.id.date);
        location = findViewById(R.id.location);
        message = findViewById(R.id.message);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("ASE", Context.MODE_PRIVATE);
        final String userId = sharedPref.getString("userId","");

        final String sessionId = sharedPref.getString("sessionId","");
        //String studentId = intent.getStringExtra(AttendanceListActivity.STUDENT_ID);
        //int weekId = intent.getIntExtra(AttendanceListActivity.WEEK_ID,0);


        attendanceDetails = (AttendanceDetails) intent.getSerializableExtra("attendanceDetails");
        String baseUrl = "";
        try {
            baseUrl = PropertyUtil.getProperty("classScheduleURL", this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String qrCodeUrl = baseUrl + "token/" + userId + "/week/" +attendanceDetails.getWeekId();
        url = null;
        try {
            url = new URL(qrCodeUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        new GetQRCodeHandler().execute(sessionId);


    }

    private class GetQRCodeHandler extends AsyncTask<String, String, Bitmap> {
        ProgressDialog progressDialog;

        protected Bitmap doInBackground(String... params) {
            Bitmap bmp = null;
            try {
                byte[] qrCode = HttpClient.getInstance(DisplayQRCodeActivity.this).getQRCode(url);
                 bmp = BitmapFactory.decodeByteArray(qrCode, 0, qrCode.length);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bmp;
        }

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(DisplayQRCodeActivity.this,
                    "ProgressDialog",
                    "Loading");
        }
        @Override
        protected void onPostExecute(Bitmap map) {
            SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd, yyyy hh:mm a");
            date.setText(sdf.format(attendanceDetails.getDate()));
            message.setText(message.getText() +" "+ attendanceDetails.getWeekId() + ".");
            ImageView imageView = (ImageView) findViewById(R.id.imageView1);
            imageView.setImageBitmap(map);
            progressDialog.dismiss();
        }
    }
}
