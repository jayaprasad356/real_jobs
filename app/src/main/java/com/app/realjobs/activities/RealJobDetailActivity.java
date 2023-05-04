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
import com.app.realjobs.model.FakeHistory;
import com.app.realjobs.model.ReferBonus;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RealJobDetailActivity extends AppCompatActivity {
    private ActivityRealJobDetailBinding binding;
    private ReferBonusAdapters referBonusAdapters;
    String Title,Description,income,company_name;
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

        binding.tvName.setText(Title);
        binding.tvDescription.setText(Description);
        binding.tvMonthlyIncome.setText("Monthly   income  ₹ "+income);




        fakeHistoryList();
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    private void fakeHistoryList() {

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
                        ArrayList<ReferBonus> referBonusArrayList = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            if (jsonObject1 != null) {
                                ReferBonus group = g.fromJson(jsonObject1.toString(), ReferBonus.class);
                                referBonusArrayList.add(group);
                            } else {
                                break;
                            }
                        }

                        //important
                        referBonusAdapters = new ReferBonusAdapters(this, referBonusArrayList);
                        binding.recyclerView.setAdapter(referBonusAdapters);




                    }
                    else {
                        Toast.makeText(this, ""+String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
            }else {

                ArrayList<ReferBonus> referBonusArrayList = new ArrayList<>();
                ReferBonus group = new ReferBonus("Reffer Bonus ₹1500 ");
                ReferBonus groups = new ReferBonus("Reffer Bonus ₹150 ");
                ReferBonus groupsf = new ReferBonus("Reffer Bonus ₹150 ");
                referBonusArrayList.add(groupsf);
                referBonusArrayList.add(group);
                referBonusArrayList.add(groups);

                referBonusAdapters = new ReferBonusAdapters(this, referBonusArrayList);
                binding.recyclerView.setAdapter(referBonusAdapters);

            }
        }, this, "Constant.CATEGORY_LIST", params, true);




    }
}