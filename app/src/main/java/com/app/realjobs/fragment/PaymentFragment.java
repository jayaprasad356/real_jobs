package com.app.realjobs.fragment;

import static com.app.realjobs.chat.constants.IConstants.CATEGORY;
import static com.app.realjobs.chat.constants.IConstants.EXTRA_USER_ID;
import static com.app.realjobs.chat.constants.IConstants.NAME;
import static com.app.realjobs.chat.constants.IConstants.TICKET_ID;
import static com.app.realjobs.chat.constants.IConstants.TYPE;
import static com.app.realjobs.helper.Constant.DESCRIPTION;
import static com.app.realjobs.helper.Constant.REFERRED_BY;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.realjobs.R;
import com.app.realjobs.activities.PaymentActivity;
import com.app.realjobs.chat.MessageActivity;
import com.app.realjobs.chat.models.Ticket;
import com.app.realjobs.helper.ApiConfig;
import com.app.realjobs.helper.Constant;
import com.app.realjobs.helper.Session;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class PaymentFragment extends Fragment {

    Button btnUpload;

    Session session;
    String RandomId;
    DatabaseReference reference;
    Button tvUpi;
    TextView tvUpiid,tvAmount;

    public PaymentFragment() {
        // Required empty public constructor
    }



    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_payment, container, false);
        session= new Session(requireContext());
        btnUpload = root.findViewById(R.id.btnUpload);
        tvUpi = root.findViewById(R.id.btnCopy);
        tvUpiid = root.findViewById(R.id.tvUpiid);
        tvAmount = root.findViewById(R.id.tvAmount);


        apicalldetails();


        tvUpi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // copy to clipboard
                ClipboardManager clipboard = (ClipboardManager) requireContext().getSystemService(requireContext().CLIPBOARD_SERVICE);
                clipboard.setText(tvUpiid.getText().toString().trim());
                Toast.makeText(requireContext(), "Copied to clipboard ", Toast.LENGTH_SHORT).show();

            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkJoining();

//                Intent intent = new Intent(getActivity(), PaymentActivity.class);
//                startActivity(intent);

            }
        });

        return root;
    }

    private void apicalldetails() {

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



                        tvUpiid.setText(jsonArray.getJSONObject(0).getString("upi_id"));
                        String amount = (jsonArray.getJSONObject(0).getString("price"));

                        tvAmount.setText("₹ "+amount);



                    } else {
                        Toast.makeText(getActivity(), "" + String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
            }
        }, requireActivity(), Constant.SETTINGS, params, true);




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