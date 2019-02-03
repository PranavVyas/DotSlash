package com.teamzero.easyedu.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.teamzero.easyedu.R;
import com.teamzero.easyedu.adapters.HomeRecyclerAdapter;
import com.teamzero.easyedu.models.UploadDocumentModel;
import com.teamzero.easyedu.utils.FireStoreQueryLiveData;
import com.teamzero.easyedu.utils.GlideApp;
import com.teamzero.easyedu.viewmodel.MainViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileFragment extends Fragment {

    @BindView(R.id.profile_picture)
    ImageView profilePicture;
    @BindView(R.id.profile_username)
    TextView profileUsername;
    @BindView(R.id.profile_email)
    TextView profileEmail;
    @BindView(R.id.recycler_my_profile_main)
    RecyclerView rvMain;
    private MainViewModel mainViewModel;

    private HomeRecyclerAdapter mAdapter;

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
        //TODO Set Place Holder and error imahe
        if(getActivity() != null)
            mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        GlideApp.with(this).load(mainViewModel.getCurrUser().getPhotoUrl()).circleCrop().into(profilePicture);
        profileUsername.setText(mainViewModel.getCurrUser().getDisplayName());
        profileEmail.setText(mainViewModel.getCurrUser().getEmail());
        setUpRecyclerView();
        setUpLiveData();
    }

    private void setUpRecyclerView() {
        mAdapter = new HomeRecyclerAdapter(getContext());
        mAdapter.setHasStableIds(true);
        rvMain.setAdapter(mAdapter);
        rvMain.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }

    private void setUpLiveData() {
        Query query = FirebaseFirestore.getInstance().collection("Uploads").whereEqualTo("userName", mainViewModel.getCurrUser().getDisplayName());
        FireStoreQueryLiveData queryLiveData = new FireStoreQueryLiveData(query);
        queryLiveData.observe(this, new Observer<QuerySnapshot>() {
            @Override
            public void onChanged(QuerySnapshot queryDocumentSnapshots) {
                List<UploadDocumentModel> uploadDocumentModels = queryDocumentSnapshots.toObjects(UploadDocumentModel.class);
                mAdapter.setDataItem(uploadDocumentModels);
            }
        });
    }

}
