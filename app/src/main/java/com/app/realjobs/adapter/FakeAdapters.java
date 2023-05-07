package com.app.realjobs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.realjobs.R;
import com.app.realjobs.model.Fake;
import com.app.realjobs.model.Real;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FakeAdapters extends RecyclerView.Adapter<FakeAdapters.ViewHolder> {

    private final Context mContext;
    private final ArrayList<Fake> fakeList;


    public FakeAdapters(Context mContext, ArrayList<Fake> fakeArrayList) {
        this.mContext = mContext;
        this.fakeList = fakeArrayList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fake_lyt, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Fake f = fakeList.get(i);
        viewHolder.tvDescription.setText(f.getTitle());
        Glide.with(mContext).load(f.getImage()).placeholder(R.drawable.images_loder).into(viewHolder.ivAd);


    }


    public static class ViewHolder extends RecyclerView.ViewHolder {


        private final TextView tvDescription;
        private final ImageView ivAd;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivAd = itemView.findViewById(R.id.ivAd);

        }
    }

    @Override
    public int getItemCount() {
        return fakeList.size();
    }
}
