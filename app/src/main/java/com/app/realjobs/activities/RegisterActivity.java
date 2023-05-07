package com.app.realjobs.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.app.realjobs.R;
import com.app.realjobs.databinding.ActivityRealJobDetailBinding;
import com.app.realjobs.databinding.ActivityRegisterBinding;
import com.app.realjobs.helper.ApiConfig;
import com.app.realjobs.helper.Constant;
import com.app.realjobs.helper.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;

    Activity activity;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        activity = this;
        session = new Session(activity);

        binding.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call validateFields() method to validate all fields
                if (validateFields()) {
                   register();
                    // All fields are valid, continue with data processing
                }
            }
        });
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void register() {


        Map<String, String> params = new HashMap<>();
        params.put(Constant.NAME, binding.etName.getText().toString().trim());
        params.put(Constant.MOBILE, binding.etMobile.getText().toString().trim());
        params.put(Constant.EMAIL, binding.etEmail.getText().toString().trim());
        params.put(Constant.PLACE, binding.etPlaces.getText().toString().trim());
        params.put(Constant.SKILLS, binding.etSkills.getText().toString().trim());
        params.put(Constant.WORKING_EXPERIENCE, binding.etSkills.getText().toString().trim());
        params.put(Constant.PASSWORD, binding.etPassword.getText().toString().trim());
        ApiConfig.RequestToVolley((result, response) -> {
            Log.d("SIGNUP_RES", response);
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {

                        session.setBoolean("is_logged_in", true);

                        JSONArray userArray = jsonObject.getJSONArray(Constant.DATA);



                        // Toast.makeText(activity, ""+ userArray.getJSONObject(0).getString(Constant.ID), Toast.LENGTH_SHORT).show();


                        session.setData(Constant.USER_ID, userArray.getJSONObject(0).getString(Constant.ID));
                        session.setData(Constant.MOBILE, binding.etMobile.getText().toString().trim());
                        session.setData(Constant.NAME, binding.etName.getText().toString().trim());
                        session.setData(Constant.EMAIL, binding.etEmail.getText().toString().trim());
                        session.setData(Constant.PLACE, binding.etPlaces.getText().toString().trim());
                        session.setData(Constant.SKILLS, binding.etSkills.getText().toString().trim());
                        session.setData(Constant.WORKING_EXPERIENCE, binding.etWorkExperience.getText().toString().trim());
                        session.setData(Constant.PASSWORD, binding.etPassword.getText().toString().trim());
                        showSuccessDialog();



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
        }, RegisterActivity.this, Constant.REGISTER_URL, params, true);



    }

    private boolean validateFields() {
        boolean isValid = true;
        if (binding.etName.getText().toString().isEmpty()) {
            binding.etName.setError("Name is required");
            isValid = false;
        } else {
            binding.etName.setError(null);
        }
        if (binding.etMobile.getText().toString().length() < 10) {
            binding.etMobile.setError("Mobile number should be minimum 10 digits");
            isValid = false;
        } else {
            binding.etMobile.setError(null);
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.getText().toString()).matches()) {
            binding.etEmail.setError("Invalid email address");
            isValid = false;
        } else {
            binding.etEmail.setError(null);
        }
        if (binding.etPassword.getText().toString().isEmpty()) {
            binding.etPassword.setError("Password is required");
            isValid = false;
        } else {
            binding.etPassword.setError(null);
        }
        if (binding.etPlaces.getText().toString().isEmpty()) {
            binding.etPlaces.setError("Place is required");
            isValid = false;
        } else {
            binding.etPlaces.setError(null);
        }
        if (binding.etSkills.getText().toString().isEmpty()) {
            binding.etSkills.setError("Skills are required");
            isValid = false;
        } else {
            binding.etSkills.setError(null);
        }
        if (binding.etWorkExperience.getText().toString().isEmpty()) {
            binding.etWorkExperience.setError("Working experience is required");
            isValid = false;
        } else {
            binding.etWorkExperience.setError(null);
        }
        return isValid;
    }
    private void showSuccessDialog(){
        // show registration success dialog
        final Dialog dialog = new Dialog(RegisterActivity.this);
        dialog.setContentView(R.layout.registration_success_layout);
        dialog.setCancelable(false);
        dialog.show();

// delay the transition to HomeActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        }, 2000); // delay in milliseconds (2 seconds)

    }

}