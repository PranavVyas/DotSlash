package com.teamzero.easyedu.ui.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.orhanobut.logger.Logger;
import com.teamzero.easyedu.R;
import com.teamzero.easyedu.models.SubjectModel;
import com.teamzero.easyedu.utils.FireStoreQueryLiveData;
import com.teamzero.easyedu.utils.SharedPrefsUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectFollowingActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_select_following)
    Toolbar toolbar;
    @BindView(R.id.linear_select_following_container_subject)
    LinearLayout linearContainer;
    @BindView(R.id.spinner_select_following_branch)
    Spinner spinnerBranch;
    @BindView(R.id.spinner_select_following_sem)
    Spinner spinnerSem;

    List<String> branches;
    List<String> sems;

    String selectedBranch = "";
    String selectedSem = "";

    private FirebaseFirestore mDb = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference;
    private SharedPrefsUtils sharedPreferencesUtils;

    //    private SelectFollwingViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_following);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
//        viewModel = ViewModelProviders.of(this).get(SelectFollwingViewModel.class);
        branches = new ArrayList<>(Arrays.asList(this.getResources().getStringArray(R.array.branches)));
        sems = new ArrayList<>(Arrays.asList(this.getResources().getStringArray(R.array.sems)));
        sharedPreferencesUtils = new SharedPrefsUtils(this);
        initDatabase();
        setUpBranchSpinner();
        setUpSemSpinner();
    }

    private void initDatabase() {
        collectionReference = mDb.collection("Subjects");
    }

    private void setUpBranchSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, branches);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBranch.setAdapter(adapter);
        spinnerBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedBranch = branches.get(position);
                Toast.makeText(SelectFollowingActivity.this, "Selected Branch" + branches.get(position), Toast.LENGTH_SHORT).show();
//                if(!validateSubject()){
//                    refreshSubjectSpinner();
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setUpSemSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSem.setAdapter(adapter);
        spinnerSem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSem = sems.get(position);
                Toast.makeText(SelectFollowingActivity.this, "Selected Sem" + sems.get(position), Toast.LENGTH_SHORT).show();
//                if(!validateSubject()){
//                    refreshSubjectSpinner();
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private boolean validateSem() {
        return selectedSem.length() != 0;
    }

    private boolean validateBranch() {
        return selectedBranch.length() != 0;
    }

    @OnClick(R.id.btn_select_following_fetch_subjects)
    void fetchSubjectClicked() {
        if (!validateBranch() || !validateSem()) {
            Toast.makeText(this, "Please Select accordingly", Toast.LENGTH_SHORT).show();
            return;
        }
        Query query = collectionReference.whereEqualTo("sem", selectedSem).whereEqualTo("branch", selectedBranch);
        FireStoreQueryLiveData fireStoreQueryLiveData = new FireStoreQueryLiveData(query);
        fireStoreQueryLiveData.observe(this, new Observer<QuerySnapshot>() {
            @Override
            public void onChanged(QuerySnapshot queryDocumentSnapshots) {
                List<SubjectModel> subjectModels = queryDocumentSnapshots.toObjects(SubjectModel.class);
                //setUpLinearLayout(subjectModels);
                showAlertDialog(subjectModels);
            }
        });

    }

    private void showAlertDialog(List<SubjectModel> subjectModels) {

//        LiveData<List<FollowingEntry>> followers = viewModel.getFollowers();
//        followers.observe(this, new Observer<List<FollowingEntry>>() {
//            @Override
//            public void onChanged(List<FollowingEntry> followingEntries) {
//
//            }
//        });

        List<String> subjectsFollowing = new ArrayList<>();
        subjectsFollowing = sharedPreferencesUtils.getFollowList("SUBJECTS");
        boolean[] selectedAlready = new boolean[subjectModels.size()];
        String[] subjectArray = new String[subjectModels.size()];
        for (int i = 0; i < subjectModels.size(); i++) {
            selectedAlready[i] = subjectsFollowing.contains(subjectModels.get(i).getSubject());
            subjectArray[i] = subjectModels.get(i).getSubject();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Select Subjects to follow")
                .setCancelable(false)
                .setMultiChoiceItems(subjectArray, selectedAlready, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        selectedAlready[which] = isChecked;
                    }
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        setFollowing(selectedAlready, subjectArray);
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();

    }

    private void setFollowing(boolean selected[], String subjects[]) {
        ArrayList<String> followingSubjects = new ArrayList<>();
        ArrayList<String> notFollowingAnyMore = new ArrayList<>();
        for (int i = 0; i < selected.length; i++) {
            if (selected[i]) {
                followingSubjects.add(subjects[i]);
            } else {
                notFollowingAnyMore.add(subjects[i]);
            }
        }
        List<String> subjects1 = sharedPreferencesUtils.getFollowList("SUBJECTS");
        for (String x : subjects1) {
            if (!followingSubjects.contains(x))
                followingSubjects.add(x);
        }
        followingSubjects.removeAll(notFollowingAnyMore);
        sharedPreferencesUtils.setFollowList("SUBJECTS", followingSubjects);
        Logger.d(followingSubjects);
    }

}
