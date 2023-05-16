package com.app.realjobs.activities;

import static com.app.realjobs.chat.constants.IConstants.CATEGORY;
import static com.app.realjobs.chat.constants.IConstants.EXTRA_USER_ID;
import static com.app.realjobs.chat.constants.IConstants.NAME;
import static com.app.realjobs.chat.constants.IConstants.TICKET_ID;
import static com.app.realjobs.chat.constants.IConstants.TYPE;
import static com.app.realjobs.helper.Constant.DESCRIPTION;
import static com.app.realjobs.helper.Constant.REFERRED_BY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.realjobs.adapter.FakeHistoryAdapters;
import com.app.realjobs.adapter.ReferBonusAdapters;
import com.app.realjobs.chat.MessageActivity;
import com.app.realjobs.chat.models.Ticket;
import com.app.realjobs.databinding.ActivityRealJobDetailBinding;
import com.app.realjobs.helper.ApiConfig;
import com.app.realjobs.helper.Constant;
import com.app.realjobs.helper.Session;
import com.app.realjobs.model.RealJobVariant;
import com.app.realjobs.model.ReferBonus;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    Session session;
    String RandomId;
    DatabaseReference reference;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRealJobDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        activity = RealJobDetailActivity.this;
        session = new Session(this);


        Title = getIntent().getStringExtra("title");
        Description = getIntent().getStringExtra("description");
        income = getIntent().getStringExtra("income");
        company_name = getIntent().getStringExtra("company_name");
        String Image = getIntent().getStringExtra("image");
        String json = getIntent().getStringExtra("list");
        Gson gson = new Gson();
        Type type = new TypeToken<List<RealJobVariant>>() {
        }.getType();
        variants = gson.fromJson(json, type);
        Glide.with(this).load(Image).into(binding.ivlogo);
        binding.tvName.setText(company_name);
        binding.btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkJoining();

            }
        });
        binding.tvDescription.setText(Title);
        binding.tvMonthlyIncome.setText("Monthly   income  ₹ " + income);
        binding.webview.setVerticalScrollBarEnabled(true);
        binding.webview.loadDataWithBaseURL("",getIntent().getStringExtra("description") , "text/html", "UTF-8", "");

        // webView backgroud color
        binding.webview.setBackgroundColor(Color.parseColor("#F4F4F4"));




        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }










    private void checkJoining() {
        //DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constant.JOINING_TICKET).child(session.getData(Constant.MOBILE));
        FirebaseDatabase.getInstance()
                .getReference(Constant.JOINING_TICKET).child(session.getData(Constant.MOBILE)).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Ticket user = dataSnapshot.getValue(Ticket.class);
                            sendChat(user.getId(), user.getName(), user.getCategory(), user.getType(), user.getDescription());

                        } else {
                            joinTicket();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }


    private void joinTicket() {
        Long tsLong = System.currentTimeMillis() / 1000;
        RandomId = session.getData(Constant.USER_ID) + "_" + tsLong.toString();
        reference = FirebaseDatabase.getInstance().getReference(Constant.JOINING_TICKET).child(session.getData(Constant.MOBILE));
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(Constant.ID, RandomId);
        hashMap.put(Constant.CATEGORY, "Joining");
        hashMap.put(DESCRIPTION, "Enquiry For Joining");
        hashMap.put(Constant.USER_ID, session.getData(Constant.USER_ID));
        hashMap.put(Constant.NAME, session.getData(Constant.NAME));
        hashMap.put(Constant.MOBILE, session.getData(Constant.MOBILE));
        hashMap.put(Constant.TYPE, Constant.JOINING_TICKET);
        hashMap.put(Constant.SUPPORT, "Admin");
        hashMap.put(REFERRED_BY, session.getData(REFERRED_BY));
        hashMap.put(Constant.TIMESTAMP, tsLong.toString());
        reference.setValue(hashMap).addOnCompleteListener(task1 -> {

            sendChat(RandomId, session.getData(Constant.NAME), "Joining", Constant.JOINING_TICKET, "Enquiry For Joining");

        });
    }

    private void sendChat(String id, String name, String category, String type, String description) {

        //Log.d("CHAT_DETAILS","USER_ID - "+id + "\nName - "+name+"\nCategory - "+category+"\nType - "+type +"Description - "+description);
        final Intent intent = new Intent(activity, MessageActivity.class);
        intent.putExtra(EXTRA_USER_ID, id);
        intent.putExtra(TICKET_ID, id);
        intent.putExtra(NAME, name);
        intent.putExtra(TYPE, type);
        intent.putExtra(DESCRIPTION, description);
        intent.putExtra(CATEGORY, category);
        startActivity(intent);
    }




}