package com.teamzero.easyedu.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.orhanobut.logger.Logger;
import com.teamzero.easyedu.R;
import com.teamzero.easyedu.models.SubjectModel;
import com.teamzero.easyedu.models.UploadDocumentModel;
import com.teamzero.easyedu.utils.FireStoreQueryLiveData;
import com.teamzero.easyedu.viewmodel.MainViewModel;
import com.teamzero.easyedu.viewmodel.UploadDocumentViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UploadDocumentActivity extends AppCompatActivity {

    private static final int RC_SELECT_DOCUMENT = 100;
    @BindView(R.id.et_upload_document_item_name)
    TextInputEditText etName;
    @BindView(R.id.text_input_upload_document_item_name)
    TextInputLayout inputName;
    @BindView(R.id.spinner_upload_document_branch)
    Spinner spinnerBranch;
    @BindView(R.id.spinner_upload_document_sem)
    Spinner spinnerSem;
    @BindView(R.id.spinner_upload_document_subject)
    Spinner spinnerSubject;

    @BindView(R.id.btn_upload_document_upload)
    Button btnUpload;

    private FirebaseFirestore mDb;
    private CollectionReference mCollectionReference;
    private FirebaseStorage mStorage;
    private StorageReference mRef;
    private StorageReference child;
    private List<String> branches = new ArrayList<>(new ArrayList<>(Arrays.asList("Please Select")));
    private List<String> subjects = new ArrayList<>(new ArrayList<>(Arrays.asList("Please Select")));
    private List<String> sems = new ArrayList<>(new ArrayList<>(Arrays.asList("Please Select")));

    private String selectedSem;
    private String selectedBranch;
    private Uri selectedDocument;
    private Uri downloadUri;
    private String userName = "ANYNOMOUS";
    private String name = "";
    private String selectedSubject = "";

    private UploadTask uploadTask;
    private UploadDocumentViewModel viewModel;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_document);
        ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(this).get(UploadDocumentViewModel.class);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        userName = mainViewModel.getCurrUser().getDisplayName();
        uploadTask = viewModel.getUploadTask();
        pupulateUI();
    }

    private void pupulateUI() {
        initDatabase();
        setUpSpinners();
        setUpSubjectSpinner();
        uploadContinueIfAvailable();
        btnUpload.setEnabled(false);
    }

    private void initDatabase() {
        mDb = FirebaseFirestore.getInstance();
        mCollectionReference = mDb.collection("Subjects");
        mStorage = FirebaseStorage.getInstance();
        mRef = mStorage.getReference();
    }

    private void setUpLiveData() {
        Query query = mCollectionReference.whereEqualTo("branch", selectedBranch).whereEqualTo("sem", selectedSem);
        FireStoreQueryLiveData subjectLiveData = new FireStoreQueryLiveData(query);
        subjectLiveData.observe(this, new Observer<QuerySnapshot>() {
            @Override
            public void onChanged(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.isEmpty()) {
                    Toast.makeText(UploadDocumentActivity.this, "Subject is not available", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<SubjectModel> subjectModels = queryDocumentSnapshots.toObjects(SubjectModel.class);
                subjects = new ArrayList<>();
                for (int i = 0; i < subjectModels.size(); i++) {
                    subjects.add(subjectModels.get(i).getSubject());
                }
                setUpSubjectSpinner();
            }
        });
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
        ArrayAdapter<String> adapterSem = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sems);
        adapterSem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSem.setAdapter(adapterSem);

        spinnerSem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSem = sems.get(position);
                Toast.makeText(UploadDocumentActivity.this, "Selcetd Sem :" + sems.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick(R.id.btn_upload_document_fetch_subjects)
    void fetchSubjectClicked() {
        if (selectedBranch == null || selectedSem == null) {
            Toast.makeText(this, "Branch or Sem is invalid", Toast.LENGTH_SHORT).show();
            return;
        }
        setUpLiveData();
    }

    private void setUpSubjectSpinner() {
        ArrayAdapter<String> adapterSubject = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subjects);
        adapterSubject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSubject.setAdapter(adapterSubject);
        spinnerSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSubject = subjects.get(position);
                Toast.makeText(UploadDocumentActivity.this, "Selected Subject : " + subjects.get(position), Toast.LENGTH_SHORT).show();
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
        ArrayAdapter<String> adapterBranch = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, branches);
        adapterBranch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBranch.setAdapter(adapterBranch);
        spinnerBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedBranch = branches.get(position);
                Toast.makeText(UploadDocumentActivity.this, "Selected Branch" + branches.get(position), Toast.LENGTH_SHORT).show();
//                if(!validateSubject()){
//                    refreshSubjectSpinner();
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick(R.id.btn_upload_document_select)
    void uploadDocument() {
        if (!validateName()) {
            Toast.makeText(this, "Name not selected", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!validateSubject()) {
            Toast.makeText(this, "Subject is not Selected", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("*/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK);
        pickIntent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "*/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Documents");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        startActivityForResult(chooserIntent, RC_SELECT_DOCUMENT);
    }

    private boolean validateSubject() {
        if (selectedSubject.length() == 0) {
            return false;
        } else return !selectedSubject.equals("Please Select");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SELECT_DOCUMENT) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                selectedDocument = uri;
                startUploadingFile(selectedDocument);
            }
        }
    }

    private void startUploadingFile(Uri uri) {
        child = mRef.child("images/" + userName + "/items/" + name + System.currentTimeMillis() + uri.getLastPathSegment());
        uploadTask = child.putFile(uri);
        viewModel.setUploadTask(uploadTask);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                getDownloadUrl(taskSnapshot);
                viewModel.setUploadTask(null);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UploadDocumentActivity.this, "Error while uploading image in database", Toast.LENGTH_SHORT).show();
                Logger.d("Failed due to " + e.getMessage());
                viewModel.setUploadTask(null);
                btnUpload.setEnabled(false);
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                long progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                Toast.makeText(UploadDocumentActivity.this, "Progress : " + progress, Toast.LENGTH_SHORT).show();
            }
        });
    }
    //TODO Solve Bug after this

    private void getDownloadUrl(UploadTask.TaskSnapshot taskSnapshot) {
        taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                downloadUri = uri;
                btnUpload.setEnabled(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                downloadUri = null;
                Toast.makeText(UploadDocumentActivity.this, "Download URI not found", Toast.LENGTH_SHORT).show();
                btnUpload.setEnabled(false);
            }
        });
    }

    private void uploadContinueIfAvailable() {
        if (uploadTask != null) {
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    getDownloadUrl(taskSnapshot);
                    viewModel.setUploadTask(null);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UploadDocumentActivity.this, "Error while uploading image in database", Toast.LENGTH_SHORT).show();
                    Logger.d("Failed due to " + e.getMessage());
                    viewModel.setUploadTask(null);
                    btnUpload.setEnabled(false);
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    long progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    Toast.makeText(UploadDocumentActivity.this, "Progress : " + progress, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean validateName() {
        name = etName.getText().toString().trim();
        if (name.length() == 0) {
            inputName.setError("Please Input Name");
            return false;
        } else {
            inputName.setErrorEnabled(false);
            return true;
        }
    }

    @OnClick(R.id.btn_upload_document_upload)
    void uploadFinal() {
        if (downloadUri == null | name.length() == 0 | selectedSubject.length() == 0) {
            Toast.makeText(this, "Please Fill in details", Toast.LENGTH_SHORT).show();
            return;
        } else {
            UploadDocumentModel model = new UploadDocumentModel();
            model.setBranch(selectedBranch);
            model.setSem(selectedSem);
            model.setSubject(selectedSubject);
            model.setTimestamp(System.currentTimeMillis());
            model.setUrl(downloadUri.toString());
            model.setTitle(name);
            model.setUserName(userName);
            startUploadInDatabase(model);
            //TODO handle life cycle changes in spinner and variables
        }
    }

    private void startUploadInDatabase(UploadDocumentModel model) {
        CollectionReference uploads = mDb.collection("Uploads");
        uploads.add(model).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(UploadDocumentActivity.this, "Uploaded Just Now", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UploadDocumentActivity.this, "Error while Uploading Data in the Databse", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void refreshSubjectSpinner() {
        ArrayAdapter<String> adapterSubject = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>(Arrays.asList("Please Select")));
        selectedSubject = "";
        spinnerSubject.setAdapter(adapterSubject);
    }
}
