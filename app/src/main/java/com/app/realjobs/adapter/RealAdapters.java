package com.app.realjobs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.realjobs.R;
import com.app.realjobs.model.Real;

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
        viewHolder.tvAmount.setText("Monthly   income  ₹ "+real.getAmount());

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {


        private final TextView tvDescription;
        private final TextView tvAmount;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvAmount = itemView.findViewById(R.id.tvAmount);

        }
    }

    @Override
    public int getItemCount() {
        return realArrayList.size();
    }
}
