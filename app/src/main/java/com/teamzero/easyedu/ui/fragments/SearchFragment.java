package com.teamzero.easyedu.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.teamzero.easyedu.R;
import com.teamzero.easyedu.adapters.HomeRecyclerAdapter;
import com.teamzero.easyedu.models.SubjectModel;
import com.teamzero.easyedu.models.UploadDocumentModel;
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

public class SearchFragment extends Fragment {

    @BindView(R.id.text_input_search_frag_search)
    TextInputLayout inputSearch;
    @BindView(R.id.et_search_frag_search)
    TextInputEditText etSearch;
    @BindView(R.id.spinner_search_frag_branch)
    Spinner spinnerBranch;
    @BindView(R.id.spinner_search_frag_sem)
    Spinner spinnerSem;
    @BindView(R.id.spinner_search_frag_subjects)
    Spinner spinnerSubject;
    @BindView(R.id.recycler_search_frag_main)
    RecyclerView rvMain;

    private List<String> branches = new ArrayList<>(new ArrayList<>(Arrays.asList("Please Select")));
    private List<String> subjects = new ArrayList<>(new ArrayList<>(Arrays.asList("Please Select")));
    private List<String> sems = new ArrayList<>(new ArrayList<>(Arrays.asList("Please Select")));
    private String selectedSem = "";
    private String selectedBranch = "";
    private String selectedSubject = "";
    private String searchName = "";

    private FirebaseFirestore mDb;
    private CollectionReference mCollectionReference;
    private FirebaseStorage mStorage;
    private StorageReference mRef;
    private StorageReference child;

    private HomeRecyclerAdapter mAdapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDatabase();
        populateUI();
    }

    private void populateUI() {
        setUpSpinners();
        setUpSubjectSpinner();
        setUpRecyclerView();
    }

    private void initDatabase() {
        mDb = FirebaseFirestore.getInstance();
        mCollectionReference = mDb.collection("Subjects");
        mStorage = FirebaseStorage.getInstance();
        mRef = mStorage.getReference();
    }

    private void setUpSpinners() {
        branches = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.branches)));
        sems = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.sems)));
        selectedSem = sems.get(0);
        selectedBranch = branches.get(0);
        setUpBranchSpinner();
        setUpSemAdapter();
    }

    private void setUpSemAdapter() {
        ArrayAdapter<String> adapterSem = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, sems);
        adapterSem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSem.setAdapter(adapterSem);

        spinnerSem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSem = sems.get(position);
                Toast.makeText(getContext(), "Selcetd Sem :" + sems.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setUpSubjectSpinner() {
        ArrayAdapter<String> adapterSubject = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, subjects);
        adapterSubject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSubject.setAdapter(adapterSubject);
        spinnerSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSubject = subjects.get(position);
                Toast.makeText(getContext(), "Selected Subject : " + subjects.get(position), Toast.LENGTH_SHORT).show();
//                if(!validateSubject()){
//                    refreshSubjectSpinner();
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setUpBranchSpinner() {
        ArrayAdapter<String> adapterBranch = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, branches);
        adapterBranch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBranch.setAdapter(adapterBranch);
        spinnerBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedBranch = branches.get(position);
                Toast.makeText(getContext(), "Selected Branch" + branches.get(position), Toast.LENGTH_SHORT).show();
//                if(!validateSubject()){
//                    refreshSubjectSpinner();
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private boolean validateSubject() {
        if (selectedSubject.length() == 0) {
            return false;
        } else return !selectedSubject.equals("Please Select");

    }

    private void setUpLiveData() {
        Query query = mCollectionReference.whereEqualTo("branch", selectedBranch).whereEqualTo("sem", selectedSem);
        FireStoreQueryLiveData subjectLiveData = new FireStoreQueryLiveData(query);
        subjectLiveData.observe(this, queryDocumentSnapshots -> {
            subjectLiveData.removeObservers(this);
            if (queryDocumentSnapshots.isEmpty()) {
                Toast.makeText(getContext(), "Subject is not available", Toast.LENGTH_SHORT).show();
                return;
            }
            List<SubjectModel> subjectModels = queryDocumentSnapshots.toObjects(SubjectModel.class);
            subjects = new ArrayList<>();
            for (int i = 0; i < subjectModels.size(); i++) {
                subjects.add(subjectModels.get(i).getSubject());
            }
            setUpSubjectSpinner();
        });
    }

    @OnClick(R.id.btn_search_frag_fetch_subjects)
    void fetchSubjectClicked() {
        if (selectedBranch == null || selectedSem == null) {
            Toast.makeText(getContext(), "Branch or Sem is invalid", Toast.LENGTH_SHORT).show();
            return;
        }
        setUpLiveData();
    }

    @OnClick(R.id.btn_search_frag_search)
    void searchClicked() {
        if (!validateSubject()) {
            Toast.makeText(getContext(), "Subject is not selected", Toast.LENGTH_SHORT).show();
            return;
        }
        Query query = mDb.collection("Uploads").whereEqualTo("subject", selectedSubject);
        if (!(searchName.length() == 0)) {
            query = query.whereEqualTo("title", searchName);
        }
        FireStoreQueryLiveData queryLiveData = new FireStoreQueryLiveData(query);
        queryLiveData.observe(this, new Observer<QuerySnapshot>() {
            @Override
            public void onChanged(QuerySnapshot queryDocumentSnapshots) {
                List<UploadDocumentModel> uploadDocumentModels = queryDocumentSnapshots.toObjects(UploadDocumentModel.class);
                mAdapter.setDataItem(uploadDocumentModels);
            }
        });
    }

    private void setUpRecyclerView() {
        rvMain.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        mAdapter = new HomeRecyclerAdapter(getContext());
        mAdapter.setHasStableIds(true);
        rvMain.setAdapter(mAdapter);
    }
}
