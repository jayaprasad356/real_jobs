package com.app.realjobs.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.realjobs.activities.CheckFakeJobActivity;
import com.app.realjobs.activities.RiseTicketActivity;
import com.app.realjobs.adapter.FakeAdapters;
import com.app.realjobs.databinding.ActivityCheckFakeJobBinding;
import com.app.realjobs.databinding.FragmentFakeBinding;
import com.app.realjobs.helper.ApiConfig;
import com.app.realjobs.helper.Constant;
import com.app.realjobs.helper.Session;
import com.app.realjobs.model.Fake;
import com.app.realjobs.model.Real;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.app.realjobs.adapter.RealAdapters;


public class FakeFragment extends Fragment {
    FakeAdapters fakeAdapters;
    Session session;
    FragmentFakeBinding binding;
    Activity activity;
    public FakeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFakeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        activity=getActivity();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        fakeList();
        binding.llCheckFake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CheckFakeJobActivity.class);
                startActivity(intent);            }
        });

        return view;
    }
    private void fakeList() {

        Map<String, String> params = new HashMap<>();
        ApiConfig.RequestToVolley((result, response) -> {
            Log.d("CAT_RES",response);

            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {

                        Log.d("CAT_RES",response);
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.DATA);
                        Gson g = new Gson();
                        ArrayList<Fake> fakeArrayList = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            if (jsonObject1 != null) {
                                Fake group = g.fromJson(jsonObject1.toString(), Fake.class);
                                fakeArrayList.add(group);
                            } else {
                                break;
                            }
                        }

                        //important
                        fakeAdapters = new FakeAdapters(getActivity(), fakeArrayList);
                        binding.recyclerView.setAdapter(fakeAdapters);




                    }
                    else {
                       // Toast.makeText(getActivity(), ""+String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
            }
        }, requireActivity(), Constant.FAKEJOBSLIST, params, true);




    }
}