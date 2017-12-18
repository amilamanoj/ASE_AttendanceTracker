package attendancetracker.ase.com.ase_attendancetracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

import org.restlet.resource.ClientResource;

import java.net.MalformedURLException;
import java.net.URL;

public class DisplayTutorialListActivity extends AppCompatActivity {

    public static String rawAnswer;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_tutorial_list);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        textView = findViewById(R.id.textView);
        URL url = null;
        try {
            url = new URL("https://radiant-land-185414.appspot.com/rest/students/115816437810331465568/attendances");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        new RESTCall().execute(url);
        //textView.setText(message);
        //textView.setText(rawAnswer);

    }

    private class RESTCall extends AsyncTask<URL, String, String> {
        private String resp;
        ProgressDialog progressDialog;
        protected String doInBackground(URL... urls) {
            publishProgress("Sleeping...");
            try{
                rawAnswer = new ClientResource(urls[0].toString()).get().getText();

            }
            catch (Exception e)
            {
                rawAnswer = e.getMessage();
            }
            return rawAnswer;
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            Gson gson = new Gson();
            SingleAttendanceDetails[] person = gson.fromJson(result, SingleAttendanceDetails[].class);
            textView.setText(person[0].getStudentId().toString());

        }

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(DisplayTutorialListActivity.this,
                    "ProgressDialog",
                    "Loading");
        }
    }
}
