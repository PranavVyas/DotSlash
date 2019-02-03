package com.teamzero.easyedu.adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.teamzero.easyedu.R;
import com.teamzero.easyedu.models.UploadDocumentModel;
import com.teamzero.easyedu.utils.ConverterUtils;

import java.io.File;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.HomeItemHolder> {

    private File file;
    private List<UploadDocumentModel> dataItems;
    private Context context;

    public HomeRecyclerAdapter(Context context) {
        this.context = context;
        checkPermission();
    }

    private void checkPermission() {
        Dexter.withActivity((Activity) context)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
//                        Toast.makeText(context, "Thank you for giving permission", Toast.LENGTH_SHORT).show();
                        file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(context, "Permission is must for opening local files", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

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

    public void setDataItem(List<UploadDocumentModel> dataItems) {
        this.dataItems = dataItems;
        notifyDataSetChanged();
    }

    class HomeItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_reycler_home_item_name)
        TextView tvItemName;
        @BindView(R.id.tv_reycler_home_name)
        TextView tvName;
        @BindView(R.id.tv_reycler_home_date)
        TextView tvDate;
        @BindView(R.id.tv_reycler_home_subject)
        TextView tvSubject;

        HomeItemHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {/*
            for (int i = 0; i < dataItems.size(); i++)
                Log.e("EEE", i + " " + dataItems.get(i).getTitle());*/
            // Log.e("EEE", getAdapterPosition()+"");
            if (file == null) {
                return;
            }
            File[] files = file.listFiles();
            String title = dataItems.get(getAdapterPosition()).getTitle();
            String date = String.valueOf(dataItems.get(getAdapterPosition()).getTimestamp());
            String filePath = "";
            boolean fileFound = false;
            if (files != null) {
                for (File eachFile : files) {
                    if (eachFile.getName().contains(title + title + title + "169961")) {
                        Log.e("EEE", eachFile.getAbsolutePath());
                        fileFound = true;
                        String path = eachFile.getAbsolutePath();

                        //TODO: Open In App
                        break;
                    }
                }
                if (!fileFound)
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(dataItems.get(getAdapterPosition()).getUrl())));
            }

        }
    }


}
