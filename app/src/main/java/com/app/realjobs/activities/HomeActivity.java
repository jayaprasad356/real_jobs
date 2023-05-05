package com.app.realjobs.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

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

            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, profileFragment).commit();
                return true;

        }
        return false;
    }

}