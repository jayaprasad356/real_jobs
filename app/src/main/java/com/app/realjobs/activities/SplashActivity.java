package com.app.realjobs.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.app.realjobs.R;
import com.app.realjobs.helper.ApiConfig;
import com.app.realjobs.helper.Constant;
import com.app.realjobs.helper.Session;
import com.google.gson.Gson;

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
        if (session.getBoolean("is_logged_in")){
            profileList();
        }

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

    private void profileList() {

        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID, session.getData(Constant.USER_ID));
        ApiConfig.RequestToVolley((result, response) -> {
            Log.d("CAT_RES", response);

            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {

                        Log.d("CAT_RES", response);
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.DATA);
                        Gson g = new Gson();
                        session.setData(Constant.PAYMENT_STATUS,jsonArray.getJSONObject(0).getString(Constant.PAYMENT_STATUS));

                    } else {
                        Toast.makeText(this, "" + String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
            }
        }, this, Constant.USER_DETAILS, params, true);


    }
    // Check if the current version is outdated
    private void showUpdateDialog() {

        // Create an AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Set the title and message
        builder.setTitle("Update Available");
        builder.setMessage("A new version of the app is available. Please update to continue using the app.");

        // Set the dialog to be cancelable false
        builder.setCancelable(false);

        // Add the positive button and its click listener
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Redirect the user to the Google Play Store or your server's download page
                // to download the latest version of the app
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.example.app"));
                startActivity(intent);
            }
        });

        // Create and show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}