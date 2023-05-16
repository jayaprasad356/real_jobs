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
import com.app.realjobs.model.FakeHistory;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FakeHistoryAdapters extends RecyclerView.Adapter<FakeHistoryAdapters.ViewHolder> {

    private final Context mContext;
    private final ArrayList<FakeHistory> fakeHistories;


    public FakeHistoryAdapters(Context mContext, ArrayList<FakeHistory> fakeHistories) {
        this.mContext = mContext;
        this.fakeHistories = fakeHistories;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fake_history_lyt, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final FakeHistory fakeHistory = fakeHistories.get(i);

        if (fakeHistory.getStatus().equals("0")){
            viewHolder.tvPending.setVisibility(View.VISIBLE);
            viewHolder.tvFake.setVisibility(View.GONE);
            viewHolder.tvReal.setVisibility(View.GONE);
        }else if (fakeHistory.getStatus().equals("1")){
            viewHolder.tvPending.setVisibility(View.GONE);
            viewHolder.tvFake.setVisibility(View.VISIBLE);
            viewHolder.tvReal.setVisibility(View.GONE);
        }
        else if (fakeHistory.getStatus().equals("2")){
            viewHolder.tvPending.setVisibility(View.GONE);
            viewHolder.tvFake.setVisibility(View.GONE);
            viewHolder.tvReal.setVisibility(View.VISIBLE);
        }

        viewHolder.tvDescription.setText(fakeHistory.getDescription());
        viewHolder.tvTitle.setText(fakeHistory.getTitle());
        Glide.with(mContext).load(fakeHistory.getScreenshot()).into(viewHolder.ivScreenshot);


    }


    public static class ViewHolder extends RecyclerView.ViewHolder {


        private final TextView tvDescription,tvTitle,tvReal,tvFake,tvPending;
        ImageView ivScreenshot;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            ivScreenshot=itemView.findViewById(R.id.ivScreenshot);
            tvReal=itemView.findViewById(R.id.tvReal);
            tvFake=itemView.findViewById(R.id.tvFake);
            tvPending=itemView.findViewById(R.id.tvPending);


        }
    }

    @Override
    public int getItemCount() {
        return fakeHistories.size();
    }
}
