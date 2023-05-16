package com.app.realjobs.fragment;

import static com.app.realjobs.chat.constants.IConstants.CATEGORY;
import static com.app.realjobs.chat.constants.IConstants.EXTRA_USER_ID;
import static com.app.realjobs.chat.constants.IConstants.NAME;
import static com.app.realjobs.chat.constants.IConstants.TICKET_ID;
import static com.app.realjobs.chat.constants.IConstants.TYPE;
import static com.app.realjobs.helper.Constant.DESCRIPTION;
import static com.app.realjobs.helper.Constant.REFERRED_BY;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.realjobs.activities.RiseTicketActivity;
import com.app.realjobs.activities.TicketActivity;
import com.app.realjobs.chat.MessageActivity;
import com.app.realjobs.chat.models.Ticket;
import com.app.realjobs.databinding.FragmentRealBinding;
import com.app.realjobs.helper.ApiConfig;
import com.app.realjobs.helper.Constant;
import com.app.realjobs.helper.Session;
import com.app.realjobs.model.Real;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    String RandomId;
    DatabaseReference reference;


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
        session= new Session(requireContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        realList();
        binding.fabChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), TicketActivity.class);
//                startActivity(intent);
                checkJoining();
            }
        });
        return view;
    }
    private void realList() {

        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID, session.getData(Constant.USER_ID));
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
            }
//            else {
//
//                ArrayList<Real> realArrayList = new ArrayList<>();
//
//                Real group = new Real("an employee is working from their house, apartment, or place of residence, rather than working from the office.","45645");
//                realArrayList.add(group);
//                realAdapters = new RealAdapters(requireContext(), realArrayList);
//                binding.recyclerView.setAdapter(realAdapters);
//            }
        }, requireActivity(), Constant.REALJOBSLIST, params, true);




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
        final Intent intent = new Intent(getActivity(), MessageActivity.class);
        intent.putExtra(EXTRA_USER_ID, id);
        intent.putExtra(TICKET_ID, id);
        intent.putExtra(NAME, name);
        intent.putExtra(TYPE, type);
        intent.putExtra(DESCRIPTION, description);
        intent.putExtra(CATEGORY, category);
        startActivity(intent);
    }

}