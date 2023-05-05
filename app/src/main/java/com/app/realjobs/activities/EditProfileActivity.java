package com.app.realjobs.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.realjobs.databinding.ActivityEditProfileBinding;
import com.app.realjobs.helper.Session;
import com.app.realjobs.helper.ApiConfig;
import com.app.realjobs.helper.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {
    ActivityEditProfileBinding binding;
    Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        session=new Session(this);
        binding.etEmail.setText(session.getData(Constant.EMAIL));
        binding.etName.setText(session.getData(Constant.NAME));
        binding.etMobile.setText(session.getData(Constant.MOBILE));
        binding.etPassword.setText(session.getData(Constant.PASSWORD));
        binding.etPlaces.setText(session.getData(Constant.PLACE));
        binding.etSkills.setText(session.getData(Constant.SKILLS));
        binding.etWorkExperience.setText(session.getData(Constant.WORKING_EXPERIENCE));

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

  }
    private void updateProfile() {


        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID,session.getData(Constant.USER_ID));

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

                        Toast.makeText(this, "" + jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                        session.setData(Constant.MOBILE, binding.etMobile.getText().toString().trim());
                        session.setData(Constant.NAME, binding.etName.getText().toString().trim());
                        session.setData(Constant.EMAIL, binding.etEmail.getText().toString().trim());
                        session.setData(Constant.PLACE, binding.etPlaces.getText().toString().trim());
                        session.setData(Constant.SKILLS, binding.etSkills.getText().toString().trim());
                        session.setData(Constant.WORKING_EXPERIENCE, binding.etWorkExperience.getText().toString().trim());
                        session.setData(Constant.PASSWORD, binding.etPassword.getText().toString().trim());


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
        }, EditProfileActivity.this, Constant.UPDATE_USER_DETAILS, params, true);



    }
}