package com.teamzero.easyedu.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamzero.easyedu.R;
import com.teamzero.easyedu.ui.activities.UploadDocumentActivity;

import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeFragment extends Fragment {


    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.fab_home_frag_upload)
    void fabClicked() {
        Intent intent = new Intent(getContext(), UploadDocumentActivity.class);
        startActivity(intent);
    }

}
