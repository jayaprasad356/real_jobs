package com.app.realjobs.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.realjobs.R;
import com.app.realjobs.adapter.FakeAdapters;
import com.app.realjobs.adapter.FakeHistoryAdapters;
import com.app.realjobs.databinding.ActivityCheckFakeJobBinding;
import com.app.realjobs.databinding.ActivityFakeJobHistoryBinding;
import com.app.realjobs.helper.ApiConfig;
import com.app.realjobs.helper.Constant;
import com.app.realjobs.model.Fake;
import com.app.realjobs.model.FakeHistory;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FakeJobHistoryActivity extends AppCompatActivity {
private ActivityFakeJobHistoryBinding binding;
private FakeHistoryAdapters fakeHistoryAdapters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFakeJobHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
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
                        ArrayList<FakeHistory> fakeHistoryArrayList = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            if (jsonObject1 != null) {
                                FakeHistory group = g.fromJson(jsonObject1.toString(), FakeHistory.class);
                                fakeHistoryArrayList.add(group);
                            } else {
                                break;
                            }
                        }

                        //important
                        fakeHistoryAdapters = new FakeHistoryAdapters(this, fakeHistoryArrayList);
                        binding.recyclerView.setAdapter(fakeHistoryAdapters);




                    }
                    else {
                        Toast.makeText(this, ""+String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
            }else {

                ArrayList<FakeHistory> fakeArrayList = new ArrayList<>();
                FakeHistory group = new FakeHistory("Fiewin","Description","success");
                fakeArrayList.add(group);
                fakeHistoryAdapters = new FakeHistoryAdapters(this, fakeArrayList);
                binding.recyclerView.setAdapter(fakeHistoryAdapters);

            }
        }, this, "Constant.CATEGORY_LIST", params, true);




    }
}