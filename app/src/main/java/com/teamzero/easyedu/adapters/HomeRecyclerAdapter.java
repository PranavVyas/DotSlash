package com.teamzero.easyedu.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamzero.easyedu.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.HomeItemHolder> {

    @NonNull
    @Override
    public HomeItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeItemHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class HomeItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_reycler_home_item_name)
        TextView tvItemName;
        @BindView(R.id.tv_reycler_home_name)
        TextView tvName;
        @BindView(R.id.tv_reycler_home_date)
        TextView tvDate;

        public HomeItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
