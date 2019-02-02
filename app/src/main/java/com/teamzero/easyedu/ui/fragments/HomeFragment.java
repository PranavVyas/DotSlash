package com.teamzero.easyedu.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.teamzero.easyedu.R;
import com.teamzero.easyedu.adapters.HomeRecyclerAdapter;
import com.teamzero.easyedu.models.UploadDocumentModel;
import com.teamzero.easyedu.ui.activities.UploadDocumentActivity;
import com.teamzero.easyedu.utils.FireStoreQueryLiveData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeFragment extends Fragment {

    @BindView(R.id.recycler_home_frag_main)
    RecyclerView rvMain;

    private List<String> followers = new ArrayList<>(Arrays.asList("CSE", "ME"));
    private FirebaseFirestore mDb;
    private CollectionReference collectionReference;

    private HomeRecyclerAdapter mAdapter;


    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpRecyclerView();
        initDatabase();
    }

    private void setUpRecyclerView() {
        mAdapter = new HomeRecyclerAdapter();
        mAdapter.setHasStableIds(true);
        rvMain.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rvMain.setAdapter(mAdapter);
    }

    private void initDatabase() {
        mDb = FirebaseFirestore.getInstance();
        collectionReference = mDb.collection("Uploads");
        Query query = collectionReference;
        for (int i = 0; i < followers.size(); i++) {
            collectionReference.whereEqualTo("branch", followers.get(i));
        }
        FireStoreQueryLiveData queryLiveData = new FireStoreQueryLiveData(query);
        queryLiveData.observe(getActivity(), new Observer<QuerySnapshot>() {
            @Override
            public void onChanged(QuerySnapshot queryDocumentSnapshots) {
                List<UploadDocumentModel> uploadDocumentModels = queryDocumentSnapshots.toObjects(UploadDocumentModel.class);
                mAdapter.setDataItem(uploadDocumentModels);
            }
        });
    }


    @OnClick(R.id.fab_home_frag_upload)
    void fabClicked() {
        Intent intent = new Intent(getContext(), UploadDocumentActivity.class);
        startActivity(intent);
    }

}
