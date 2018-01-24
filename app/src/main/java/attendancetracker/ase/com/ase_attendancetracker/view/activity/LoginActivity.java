package attendancetracker.ase.com.ase_attendancetracker.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import attendancetracker.ase.com.ase_attendancetracker.R;
import attendancetracker.ase.com.ase_attendancetracker.service.LoginService;


public class LoginActivity extends AppCompatActivity {

    public static String EXTRA_MESSAGE;
    SharedPreferences sharedPref;
    private EditText userName,password;
    private Intent intent;
    private Boolean isValid;
    private TextView textViewToChange;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPref = getApplicationContext().getSharedPreferences("ASE", Context.MODE_PRIVATE);
        intent = getIntent();
        userName = (EditText) findViewById(R.id.email_address);
        password = (EditText) findViewById(R.id.password);
        button = (Button) findViewById(R.id.login);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                new UserAuthenticationTask().execute(userName.getText().toString(), password.getText().toString());

            }
        });
    }
    private class UserAuthenticationTask extends AsyncTask<String, String, Boolean> {

        ProgressDialog progressDialog;
        String userId;
        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                publishProgress("Sleeping...");
                LoginService loginService= new LoginService(getApplicationContext());
                String rawValues = loginService.authenticateUser(strings[0],strings[1]);
                JSONObject jsonObject = new JSONObject(rawValues);
                userId = jsonObject.getString("user_id");
                return true;

            }
            catch(IOException exc) {
                Log.e("LoginException IOException",exc.toString());
                return true;
            }
            catch (JSONException exc){
                Log.e("LoginException JSONException",exc.toString());
                return true;
            }

        }

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(LoginActivity.this,
                    "ProgressDialog",
                    "Authenticating...");
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            isValid = aBoolean;

            SharedPreferences.Editor editor = sharedPref.edit();

            if(isValid)
            {
                editor.putString("userId",userId);
                intent = new Intent(LoginActivity.this,HomeActivity.class);
                startActivity(intent);
            }
            else
            {
                textViewToChange = (TextView) findViewById(R.id.invalid_login_text);
                textViewToChange.setText("Invalid Email ID or Password.");
            }
            editor.putBoolean("isLogin", isValid);
            editor.commit();
            progressDialog.dismiss();
        }

    }
    @Override
    protected void onStart() {
        super.onStart();
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        updateUI(account);
    }

    public void sendMessage(View view) {
            Intent intent = new Intent(this, AttendanceListActivity.class);
           // EditText editText = (EditText) findViewById(R.id.editText);
          //  String message = editText.getText().toString();
            intent.putExtra(EXTRA_MESSAGE, "aaa");
            startActivity(intent);
    }


}
