package attendancetracker.ase.com.ase_attendancetracker.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;

import attendancetracker.ase.com.ase_attendancetracker.R;
import attendancetracker.ase.com.ase_attendancetracker.service.HttpClient;
import attendancetracker.ase.com.ase_attendancetracker.util.PropertyUtil;

public class TutorActivity extends AppCompatActivity {

    URL url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor);
        String baseUrl = "";
        try {
            baseUrl = PropertyUtil.getProperty("classScheduleURL", this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String qrCodeUrl = baseUrl + "tutor/qrCode";
        try {
            url = new URL(qrCodeUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        new GetQRCodeHandler().execute();

    }

    private class GetQRCodeHandler extends AsyncTask<String, String, Bitmap> {
        ProgressDialog progressDialog;

        protected Bitmap doInBackground(String... params) {
            Bitmap bmp = null;
            try {
                byte[] qrCode = HttpClient.getInstance(TutorActivity.this).getQRCode(url);
                bmp = BitmapFactory.decodeByteArray(qrCode, 0, qrCode.length);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bmp;
        }

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(TutorActivity.this,
                    "ProgressDialog",
                    "Loading");
        }
        @Override
        protected void onPostExecute(Bitmap map) {
            ImageView imageView = (ImageView) findViewById(R.id.qr_code_tutor_image_view);
            imageView.setImageBitmap(map);
            progressDialog.dismiss();
        }
    }

}
