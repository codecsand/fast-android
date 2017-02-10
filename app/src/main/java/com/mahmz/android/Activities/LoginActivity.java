package com.mahmz.android.Activities;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.mahmz.android.Classes.HttpPost;
import com.mahmz.android.Classes.Validation;
import com.mahmz.android.MainActivity;
import com.mahmz.android.R;
import com.mahmz.android.Settings.Config;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private EditText userOrEmail;
    private EditText password;
    private Button loginbt;
    private ProgressWheel wheel;
    private Button disableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //load progress
        wheel = (ProgressWheel) findViewById(R.id.progress_wheel);
        //load disable view
        disableView = (Button) findViewById(R.id.disableView);
        //get fields
        userOrEmail = (EditText) findViewById(R.id.usernameOrEmail);
        password = (EditText) findViewById(R.id.password);
        loginbt = (Button) findViewById(R.id.loginbt);
        //register event
        loginbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation()) {
                    JSONObject values = new JSONObject();
                    try {
                        values.put("UserNameEmail", userOrEmail.getText());
                        values.put("Password", password.getText());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (Config.testDemo) {
                        //login for test  //remove this
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        //uncomment //submit login
                        new loginTask(values).execute(Config.loginUrl);
                    }
                }
            }
        });
    }

    private boolean checkValidation() {
        boolean ret = true;
        if (!Validation.hasText(userOrEmail)) ret = false;
        if (!Validation.hasText(password)) ret = false;
        return ret;
    }

    // submit register as task
    class loginTask extends AsyncTask<String, Void, String> {
        private JSONObject values;
        private JSONObject res;

        public loginTask(JSONObject valuesp) {
            values = valuesp;
        }

        @Override
        protected void onPreExecute() {
            wheel.setVisibility(View.VISIBLE);
            disableView.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            res = HttpPost.getJsonByPost(params[0], values);
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                wheel.setVisibility(View.GONE);
                disableView.setVisibility(View.GONE);
                if (res == null || res.length() == 0) {
                /*new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText(getResources().getString(R.string.information))
                        .setContentText(getResources().getString(R.string.invalidUserOrPass))
                        .show();
                return;*/
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                    return;
                }
                if (res.getInt("MessageType") == 1) {
                    userOrEmail.setText("");
                    password.setText("");
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    /*new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(getResources().getString(R.string.information))
                            .setContentText(getResources().getString(R.string.invalidUserOrPass))
                            .show();*/
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }
    }
}
