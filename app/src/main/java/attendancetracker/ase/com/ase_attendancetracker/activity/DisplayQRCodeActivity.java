package attendancetracker.ase.com.ase_attendancetracker.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import attendancetracker.ase.com.ase_attendancetracker.MainActivity;
import attendancetracker.ase.com.ase_attendancetracker.R;
import attendancetracker.ase.com.ase_attendancetracker.qrcode.service.Contents;
import attendancetracker.ase.com.ase_attendancetracker.qrcode.service.QRCodeEncoder;

public class DisplayQRCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_qrcode);
        Intent intent = getIntent();
        String token = intent.getStringExtra(TutorialListActivity.TOKEN);


        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 1/2;

        //Encode with a QR Code image
        QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(token,
                null,
                Contents.Type.TEXT,
                BarcodeFormat.QR_CODE.toString(),
                smallerDimension);
        try {
            Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
            ImageView myImage = (ImageView) findViewById(R.id.imageView1);
            myImage.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }


    }
}
