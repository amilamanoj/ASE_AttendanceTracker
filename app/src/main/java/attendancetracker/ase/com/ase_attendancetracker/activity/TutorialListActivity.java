package attendancetracker.ase.com.ase_attendancetracker.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;

import attendancetracker.ase.com.ase_attendancetracker.R;
import attendancetracker.ase.com.ase_attendancetracker.SingleAttendanceDetails;
import attendancetracker.ase.com.ase_attendancetracker.service.StudentAttendanceRestService;

public class TutorialListActivity extends AppCompatActivity {

    public static String TOKEN;
    private ListView tutorialListView;
    private ArrayList<SingleAttendanceDetails> person;
    ImageView myImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_list);
        tutorialListView = (ListView) findViewById(R.id.tutorialListId);
        new RESTCall().execute("115816437810331465568");
        //myImageView = (ImageView) findViewById(R.id.imageView);
        //myImageView.setImageResource(R.drawable.ic_launcher_background);
        tutorialListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent intent = new Intent(TutorialListActivity.this, DisplayQRCodeActivity.class);
                intent.putExtra(TOKEN, person.get(position).getId());
                startActivity(intent);
            }
        });
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
            person = gson.fromJson(result, new TypeToken<ArrayList<SingleAttendanceDetails>>(){}.getType());
            Collections.sort(person);
            //textView.setText(person[0].getStudentId().toString());
            ArrayAdapter<SingleAttendanceDetails> adapter = new ArrayAdapter<SingleAttendanceDetails>(TutorialListActivity.this,android.R.layout.test_list_item,person);
            tutorialListView.setAdapter(adapter);
        }

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(TutorialListActivity.this,
                    "ProgressDialog",
                    "Loading");
        }
    }
}
