package com.app.realjobs.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.realjobs.R;
import com.app.realjobs.activities.RealJobDetailActivity;
import com.app.realjobs.model.Real;
import com.google.gson.Gson;

import java.util.ArrayList;

public class RealAdapters extends RecyclerView.Adapter<RealAdapters.ViewHolder> {

    private final Context mContext;
    private final ArrayList<Real> realArrayList;


    public RealAdapters(Context mContext, ArrayList<Real> realArrayList) {
        this.mContext = mContext;
        this.realArrayList = realArrayList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.real_lyt, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Real real = realArrayList.get(i);
        viewHolder.tvDescription.setText(real.getDescription());
        viewHolder.tvName.setText(real.getCompany_name());
        viewHolder.tvAmount.setText("Monthly   income  ₹ "+real.getIncome());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, RealJobDetailActivity.class);
                intent.putExtra("title",real.getTitle());
                intent.putExtra("description",real.getDescription());
                intent.putExtra("company_name",real.getCompany_name());
                intent.putExtra("income",real.getIncome());
                // Convert the list to a JSON string
                Gson gson = new Gson();
                String json = gson.toJson(real.getReal_jobs_variant());
                intent.putExtra("list", json);
                mContext.startActivity(intent);
            }
        });

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {


        private final TextView tvDescription;
        private final TextView tvAmount,tvName;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvName = itemView.findViewById(R.id.tvName);

        }
    }

    @Override
    public int getItemCount() {
        return realArrayList.size();
    }
}
