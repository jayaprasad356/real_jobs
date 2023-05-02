package com.app.realjobs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.realjobs.R;
import com.app.realjobs.model.Fake;
import com.app.realjobs.model.ReferBonus;

import java.util.ArrayList;

public class ReferBonusAdapters extends RecyclerView.Adapter<ReferBonusAdapters.ViewHolder> {

    private final Context mContext;
    private final ArrayList<ReferBonus> fakeList;


    public ReferBonusAdapters(Context mContext, ArrayList<ReferBonus> fakeArrayList) {
        this.mContext = mContext;
        this.fakeList = fakeArrayList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.refer_bonus_lyt, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final ReferBonus f = fakeList.get(i);
        viewHolder.tvReferBonus.setText(f.getBonus());

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {


        private final TextView tvReferBonus;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            tvReferBonus = itemView.findViewById(R.id.tvReferBonus);

        }
    }

    @Override
    public int getItemCount() {
        return fakeList.size();
    }
}
