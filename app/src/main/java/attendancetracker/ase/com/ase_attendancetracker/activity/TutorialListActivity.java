package attendancetracker.ase.com.ase_attendancetracker.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.google.gson.Gson;

import attendancetracker.ase.com.ase_attendancetracker.R;
import attendancetracker.ase.com.ase_attendancetracker.SingleAttendanceDetails;
import attendancetracker.ase.com.ase_attendancetracker.service.StudentAttendanceRestService;

public class TutorialListActivity extends AppCompatActivity {

    private ListView tutorialListView;
    private SingleAttendanceDetails[] person;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_list);
        tutorialListView = (ListView) findViewById(R.id.tutorialListId);
        new RESTCall().execute("115816437810331465568");
    }

    private class RESTCall extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;
        protected String doInBackground(String... id) {
            publishProgress("Sleeping...");
            String rawAnswer;
            try{
                rawAnswer = new StudentAttendanceRestService().getAttendanceDetails(id[0]);
            }
            catch (Exception e)
            {
                rawAnswer = e.getMessage();
            }
            return rawAnswer;
        }

        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            Gson gson = new Gson();
            person = gson.fromJson(result, SingleAttendanceDetails[].class);
            //textView.setText(person[0].getStudentId().toString());
            ArrayAdapter<SingleAttendanceDetails> adapter = new ArrayAdapter<SingleAttendanceDetails>(TutorialListActivity.this,android.R.layout.simple_list_item_1,person);
            tutorialListView.setAdapter(adapter);
        }

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(TutorialListActivity.this,
                    "ProgressDialog",
                    "Loading");
        }
    }
}
