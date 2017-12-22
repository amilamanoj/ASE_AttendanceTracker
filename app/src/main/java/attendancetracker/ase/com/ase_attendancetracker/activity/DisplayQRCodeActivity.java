package attendancetracker.ase.com.ase_attendancetracker.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import attendancetracker.ase.com.ase_attendancetracker.R;
import attendancetracker.ase.com.ase_attendancetracker.qrcode.service.Contents;
import attendancetracker.ase.com.ase_attendancetracker.qrcode.service.QRCodeEncoder;

public class DisplayQRCodeActivity extends AppCompatActivity {

    private String urlQR = "https://radiant-land-185414.appspot.com/rest/";
    URL url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_qrcode);
        Intent intent = getIntent();
        String studentId = intent.getStringExtra(AttendanceListActivity.STUDENT_ID);
        int weekId = intent.getIntExtra(AttendanceListActivity.WEEK_ID,0);
        String qrCodeUrl = urlQR + "token/" + studentId + "/week/" +weekId;
        url = null;
        try {
            url = new URL(qrCodeUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
//
//
//        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
//        Display display = manager.getDefaultDisplay();
//        Point point = new Point();
//        display.getSize(point);
//        int width = point.x;
//        int height = point.y;
//        int smallerDimension = width < height ? width : height;
//        smallerDimension = smallerDimension * 1/2;
//
//        //Encode with a QR Code image
//        QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(token,
//                null,
//                Contents.Type.TEXT,
//                BarcodeFormat.QR_CODE.toString(),
//                smallerDimension);
//        try {
////            Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
//
//            myImage.setImageBitmap(bmp);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        new GetQRCodeHandler().execute();


    }

    private class GetQRCodeHandler extends AsyncTask<String, String, Bitmap> {

        protected Bitmap doInBackground(String... params) {
            Bitmap bmp = null;
            try {
                 bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmp;
//            publishProgress("Sleeping...");
//            String studentId = params[0];
//            String weekId = params [1];
//            String qrCodeUrl = url + "token/" + studentId + "/week/" +weekId;
//            OkHttpClient client = new OkHttpClient();
//            String rawAnswer = null;
//            try {
//                Request request = new Request.Builder()
//                        .url(qrCodeUrl)
//                        .build();
//                Response response = client.newCall(request).execute();
//                rawAnswer = response.body().string();
//            } catch (Exception e) {
//                Log.e("RetrievingAttendancesError", e.getMessage());
//            }
//            return rawAnswer;
        }

        @Override
        protected void onPostExecute(Bitmap map) {
            ImageView imageView = (ImageView) findViewById(R.id.imageView1);
            imageView.setImageBitmap(map);
        }
    }
}
