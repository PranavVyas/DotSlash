package com.teamzero.easyedu.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamzero.easyedu.R;
import com.teamzero.easyedu.models.UploadDocumentModel;
import com.teamzero.easyedu.utils.ConverterUtils;

import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.HomeItemHolder> {

    private List<UploadDocumentModel> dataItems;

    @NonNull
    @Override
    public HomeItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_holder_recycler_home, parent, false);
        return new HomeItemHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeItemHolder holder, int position) {
        holder.tvItemName.setText(dataItems.get(position).getTitle());
        holder.tvName.setText(dataItems.get(position).getUserName());
        holder.tvSubject.setText(dataItems.get(position).getSubject());
        holder.tvDate.setText(ConverterUtils.convertDateToString(new Date(dataItems.get(position).getTimestamp())));
    }

    @Override
    public int getItemCount() {
        return (dataItems == null) ? 0 : dataItems.size();
    }

    class HomeItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_reycler_home_item_name)
        TextView tvItemName;
        @BindView(R.id.tv_reycler_home_name)
        TextView tvName;
        @BindView(R.id.tv_reycler_home_date)
        TextView tvDate;
        @BindView(R.id.tv_reycler_home_subject)
        TextView tvSubject;

        public HomeItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setDataItem(List<UploadDocumentModel> dataItems) {
        this.dataItems = dataItems;
        notifyDataSetChanged();
    }


}
