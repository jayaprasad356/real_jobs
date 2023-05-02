package com.app.realjobs.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.realjobs.R;
import com.app.realjobs.databinding.ActivityCheckFakeJobBinding;

public class CheckFakeJobActivity extends AppCompatActivity {
private ActivityCheckFakeJobBinding binding;
Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckFakeJobBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
activity=this;
        binding.tvHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity,FakeJobHistoryActivity.class);
                startActivity(intent);

            }
        });
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}