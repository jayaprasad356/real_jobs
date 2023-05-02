package com.app.realjobs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.realjobs.R;
import com.app.realjobs.model.FakeHistory;

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
        viewHolder.tvDescription.setText(fakeHistory.getDescription());
        viewHolder.tvStatus.setText(fakeHistory.getStatus());
        viewHolder.tvTitle.setText(fakeHistory.getTitle());

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {


        private final TextView tvDescription,tvTitle,tvStatus;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvStatus = itemView.findViewById(R.id.tvStatus);

        }
    }

    @Override
    public int getItemCount() {
        return fakeHistories.size();
    }
}
