package com.app.realjobs.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.realjobs.R;
import com.app.realjobs.databinding.ActivityLoginBinding;
import com.app.realjobs.databinding.ActivityRegisterBinding;
import com.app.realjobs.helper.ApiConfig;
import com.app.realjobs.helper.Constant;
import com.app.realjobs.helper.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    TextView tvCreateAccount;
    ActivityLoginBinding binding;
    Activity activity;
    Session session;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        session = new Session(activity);


        btnLogin = findViewById(R.id.btnLogin);
        tvCreateAccount = findViewById(R.id.tvCreateAccount);

        btnLogin.setOnClickListener(v -> {
            if (validateFields()) {
                Login();
            }
        });
        tvCreateAccount.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);

        });


    }

    private void Login() {


        Map<String, String> params = new HashMap<>();

        params.put(Constant.MOBILE, binding.etMobile.getText().toString().trim());
        params.put(Constant.PASSWORD, binding.etPassword.getText().toString().trim());

        ApiConfig.RequestToVolley((result, response) -> {
            Log.d("SIGNUP_RES", response);
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {



                        JSONArray userArray = jsonObject.getJSONArray(Constant.DATA);

                        Toast.makeText(this, "" + jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();


                       // Toast.makeText(activity, ""+ userArray.getJSONObject(0).getString(Constant.ID), Toast.LENGTH_SHORT).show();


                        session.setData(Constant.USER_ID, userArray.getJSONObject(0).getString(Constant.ID));
                        session.setData(Constant.NAME, userArray.getJSONObject(0).getString(Constant.NAME));
                        session.setData(Constant.EMAIL, userArray.getJSONObject(0).getString(Constant.EMAIL));
                        session.setData(Constant.MOBILE, userArray.getJSONObject(0).getString(Constant.MOBILE));
                        session.setData(Constant.PLACE, userArray.getJSONObject(0).getString(Constant.PLACE));
                        session.setData(Constant.SKILLS, userArray.getJSONObject(0).getString(Constant.SKILLS));
                        session.setData(Constant.WORKING_EXPERIENCE, userArray.getJSONObject(0).getString(Constant.WORKING_EXPERIENCE));


                        session.setBoolean("is_logged_in", true);

                        Intent intent = new Intent(activity,HomeActivity.class);
                        startActivity(intent);
                        finish();


                        // showAlertdialog();

                    } else {
                        Toast.makeText(this, "" + jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, String.valueOf(response) + String.valueOf(result), Toast.LENGTH_SHORT).show();

            }
            //pass url
        }, activity, Constant.LOGIN_URL, params, true);


    }

    private boolean validateFields() {
        boolean isValid = true;
        if (binding.etMobile.getText().toString().length() < 10) {
            binding.etMobile.setError("Mobile number should be minimum 10 digits");
            isValid = false;
        } else {
            binding.etMobile.setError(null);
        }
        if (binding.etPassword.getText().toString().isEmpty()) {
            // binding.etPassword.setError("Password is required");
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else {
            binding.etPassword.setError(null);
        }

        return isValid;
    }
}
