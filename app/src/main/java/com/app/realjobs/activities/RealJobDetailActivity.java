package com.app.realjobs.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.realjobs.adapter.FakeHistoryAdapters;
import com.app.realjobs.adapter.ReferBonusAdapters;
import com.app.realjobs.databinding.ActivityRealJobDetailBinding;
import com.app.realjobs.helper.ApiConfig;
import com.app.realjobs.helper.Constant;
import com.app.realjobs.model.RealJobVariant;
import com.app.realjobs.model.ReferBonus;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RealJobDetailActivity extends AppCompatActivity {
    private ActivityRealJobDetailBinding binding;
    private ReferBonusAdapters referBonusAdapters;
    String Title, Description, income, company_name;
    List<RealJobVariant> variants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRealJobDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(linearLayoutManager);

        Title = getIntent().getStringExtra("title");
        Description = getIntent().getStringExtra("description");
        income = getIntent().getStringExtra("income");
        company_name = getIntent().getStringExtra("company_name");
        String json = getIntent().getStringExtra("list");
        Gson gson = new Gson();
        Type type = new TypeToken<List<RealJobVariant>>() {
        }.getType();
        variants = gson.fromJson(json, type);
        binding.tvName.setText(Title);
        binding.tvDescription.setText(Description);
        binding.tvMonthlyIncome.setText("Monthly   income  ₹ " + income);


        fakeHistoryList();
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void fakeHistoryList() {

        referBonusAdapters = new ReferBonusAdapters(this, variants);
        binding.recyclerView.setAdapter(referBonusAdapters);
    }
}