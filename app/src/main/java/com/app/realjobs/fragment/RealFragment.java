package com.app.realjobs.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.realjobs.activities.RiseTicketActivity;
import com.app.realjobs.databinding.FragmentRealBinding;
import com.app.realjobs.helper.ApiConfig;
import com.app.realjobs.helper.Constant;
import com.app.realjobs.helper.Session;
import com.app.realjobs.model.Real;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.app.realjobs.adapter.RealAdapters;


public class RealFragment extends Fragment {

RealAdapters realAdapters;
Session session;
FragmentRealBinding binding;

    public RealFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRealBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        realList();
        binding.fabChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RiseTicketActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
    private void realList() {

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
                        ArrayList<Real> realArrayList = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            if (jsonObject1 != null) {
                                Real group = g.fromJson(jsonObject1.toString(), Real.class);
                                realArrayList.add(group);
                            } else {
                                break;
                            }
                        }

                        //important
                        realAdapters = new RealAdapters(requireContext(), realArrayList);
                        binding.recyclerView.setAdapter(realAdapters);




                    }
                    else {
                        Toast.makeText(getActivity(), ""+String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
            }else {
                ArrayList<Real> realArrayList = new ArrayList<>();

                Real group = new Real("an employee is working from their house, apartment, or place of residence, rather than working from the office.","45645");
                realArrayList.add(group);
                realAdapters = new RealAdapters(requireContext(), realArrayList);
                binding.recyclerView.setAdapter(realAdapters);
            }
        }, requireActivity(), "Constant.CATEGORY_LIST", params, true);




    }
}