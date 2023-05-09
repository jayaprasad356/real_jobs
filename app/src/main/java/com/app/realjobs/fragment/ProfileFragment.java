package com.app.realjobs.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.realjobs.R;
import com.app.realjobs.activities.SplashActivity;
import com.app.realjobs.adapter.FakeAdapters;
import com.app.realjobs.databinding.FragmentFakeBinding;
import com.app.realjobs.databinding.FragmentProfileBinding;
import com.app.realjobs.helper.ApiConfig;
import com.app.realjobs.helper.Constant;
import com.app.realjobs.helper.Session;
import com.app.realjobs.activities.LoginActivity;
import com.app.realjobs.activities.EditProfileActivity;

import com.app.realjobs.model.Fake;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;
    Activity activity;
    Session session;


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        activity = getActivity();
        session = new Session(activity);
        binding.fabLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
        binding.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), EditProfileActivity.class);
                startActivity(intent);
            }
        });


        profileList();


        return view;


    }

    public void logout() {
        // Clear all the shared preferences data
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("realJob", Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
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

                        binding.tvName.setText(jsonArray.getJSONObject(0).getString(Constant.NAME));
                        binding.tvMobileNumber.setText(jsonArray.getJSONObject(0).getString(Constant.MOBILE));
                        binding.tvEmail.setText(jsonArray.getJSONObject(0).getString(Constant.EMAIL));
                        binding.tvPlace.setText(jsonArray.getJSONObject(0).getString(Constant.PLACE));
                        binding.tvSkill.setText(jsonArray.getJSONObject(0).getString(Constant.SKILLS));
                        binding.tvWorkingExperience.setText(jsonArray.getJSONObject(0).getString(Constant.WORKING_EXPERIENCE));


                    } else {
                        Toast.makeText(getActivity(), "" + String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
            }
        }, requireActivity(), Constant.USER_DETAILS, params, true);


    }


}