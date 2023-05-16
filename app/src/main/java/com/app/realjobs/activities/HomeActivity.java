package com.app.realjobs.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.app.realjobs.fragment.CheckFakejobsFragment;
import com.app.realjobs.helper.ApiConfig;
import com.app.realjobs.helper.Constant;

import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.app.realjobs.R;
import com.app.realjobs.fragment.FakeFragment;
import com.app.realjobs.fragment.PaymentFragment;
import com.app.realjobs.fragment.ProfileFragment;
import com.app.realjobs.fragment.RealFragment;
import com.app.realjobs.helper.Session;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    FrameLayout fragment_container;
    BottomNavigationView bottomNavigationView;
    RealFragment realFragment = new RealFragment();
    FakeFragment fakeFragment = new FakeFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    PaymentFragment paymentFragment = new PaymentFragment();
    CheckFakejobsFragment checkFakejobsFragment = new CheckFakejobsFragment();


    Activity activity;
    Session session;
    public static FragmentManager fm = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Activity activity = HomeActivity.this;
        session = new Session(activity);
        fm = getSupportFragmentManager();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(this);

        fm.beginTransaction().replace(R.id.fragment_container, new FakeFragment()).commit();
        bottomNavigationView.setSelectedItemId(R.id.nav_fake);
profileList();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_fake:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fakeFragment).commit();
                return true;

            case R.id.nav_real:
                if (session.getData(Constant.PAYMENT_STATUS).equals("1"))
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, realFragment).commit();
                else
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, paymentFragment).commit();
                return true;

            case R.id.nav_checkFake:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,checkFakejobsFragment ).commit();
                return true;

            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, profileFragment).commit();
                return true;

        }
        return false;
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
}