package com.teamzero.easyedu.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.teamzero.easyedu.R;
import com.teamzero.easyedu.viewmodel.MainViewModel;

public class ProfileFragment extends Fragment {

    @BindView(R.id.profile_picture)
    ImageView profilePicture;
    @BindView(R.id.profile_username)
    TextView profileUsername;
    @BindView(R.id.profile_email)
    TextView profileEmail;
    private MainViewModel mainViewModel;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getActivity() != null)
            mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        Glide.with(this).load(mainViewModel.getCurrUser().getPhotoUrl()).into(profilePicture);
        profileUsername.setText(mainViewModel.getCurrUser().getDisplayName());
        profileEmail.setText(mainViewModel.getCurrUser().getEmail());
    }
}
