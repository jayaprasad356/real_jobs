package com.app.realjobs.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.app.realjobs.R;
import com.app.realjobs.helper.ApiConfig;
import com.app.realjobs.helper.Constant;
import com.app.realjobs.helper.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {

    Handler handler;
    Session session;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        activity = SplashActivity.this;
        session = new Session(activity);
        handler = new Handler();
paymentStatus();
        GotoActivity();


    }


    private void GotoActivity() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Session session = new Session(SplashActivity.this);
                if (session.getBoolean("is_logged_in")) {

                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();


                } else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                }


            }
        }, 2000);
    }
    private void paymentStatus() {


        Map<String, String> params = new HashMap<>();

        params.put(Constant.USER_ID, session.getData(Constant.USER_ID));

        ApiConfig.RequestToVolley((result, response) -> {
            Log.d("SIGNUP_RES", response);
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {

                        JSONArray userArray = jsonObject.getJSONArray(Constant.DATA);
                        session.setData(Constant.PAYMENT_STATUS, userArray.getJSONObject(0).getString(Constant.PAYMENT_STATUS));


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
        }, activity, Constant.PAYMENT_STATUS_URL, params, true);


    }

}