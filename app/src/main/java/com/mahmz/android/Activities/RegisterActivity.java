package com.mahmz.android.Activities;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.mahmz.android.Classes.HttpPost;
import com.mahmz.android.Classes.Validation;
import com.mahmz.android.R;
import com.mahmz.android.Settings.Config;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class RegisterActivity extends AppCompatActivity {
    private EditText firstname;
    private EditText lastname;
    private EditText email;
    private EditText username;
    private EditText password;
    private Button registerbt;
    private ProgressWheel wheel;
    private Button disableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //load progress
        wheel = (ProgressWheel) findViewById(R.id.progress_wheel);
        //load disable view
        disableView = (Button) findViewById(R.id.disableView);
        //get fields
        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        registerbt = (Button) findViewById(R.id.registerbt);
        //register event
        registerbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation()) {
                    JSONObject values = new JSONObject();
                    try {
                        values.put("UserName", username.getText());
                        values.put("Password", password.getText());
                        values.put("FirstName", firstname.getText());
                        values.put("LastName", lastname.getText());
                        values.put("Email", email.getText());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (Config.testDemo) {
                        new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText(getResources().getString(R.string.successRegistration))
                                .setContentText(getResources().getString(R.string.emailActivate))
                                .show();
                    } else {
                        //uncomment //submit register
                        new registerTask(values).execute(Config.registerUrl);
                    }
                } else {
                    //Toast.makeText(RegisterActivity.this, "Form contains error", Toast.LENGTH_LONG).show();
                }
            }
        });
        //initialize validation
        firstname.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(firstname);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        username.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(username);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        lastname.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(lastname);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    Validation.isEmailAddress(email, true);
                    // code to execute when EditText loses focus
                }
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(password);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    private boolean checkValidation() {
        boolean ret = true;
        if (!Validation.hasText(firstname)) ret = false;
        if (!Validation.hasText(lastname)) ret = false;
        if (!Validation.hasText(username)) ret = false;
        if (!Validation.isEmailAddress(email, true)) ret = false;
        if (!Validation.isCorrectLength(password, true)) ret = false;
        return ret;
    }

    // submit register as task
    class registerTask extends AsyncTask<String, Void, String> {
        private JSONObject values;
        private JSONObject res;

        public registerTask(JSONObject valuesp) {
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
            wheel.setVisibility(View.GONE);
            disableView.setVisibility(View.GONE);
            if (res == null || res.length() == 0) {
                new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText(getResources().getString(R.string.information))
                        .setContentText(getResources().getString(R.string.emailExist))
                        .show();
                return;
            }
            try {
                if (res.getInt("MessageType") == 1) {
                    JSONArray jsarr = res.getJSONArray("User");
                    JSONObject js = jsarr.getJSONObject(0);
                    new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText(getResources().getString(R.string.successRegistration))
                            .setContentText(getResources().getString(R.string.emailActivate))
                            .show();
                    firstname.setText("");
                    lastname.setText("");
                    email.setText("");
                    username.setText("");
                    password.setText("");
                } else {
                    new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(getResources().getString(R.string.information))
                            .setContentText(getResources().getString(R.string.emailExist))
                            .show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }
    }
}
