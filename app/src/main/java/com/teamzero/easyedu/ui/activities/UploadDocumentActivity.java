package com.teamzero.easyedu.ui.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.teamzero.easyedu.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UploadDocumentActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_document);
        ButterKnife.bind(this);
        pupulateUI();
    }

    private void pupulateUI() {
        setUpSpinners();
    }

    private void setUpSpinners() {
        List<String> branches = new ArrayList<>(Arrays.asList(
                "Computer",
                "Chemical"
        ));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, branches);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        List<String> Sems = new ArrayList<>(Arrays.asList(
                "1",
                "2"
        ));
        ArrayAdapter<String> adapterSem = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Sems);
        adapterSem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        List<String> subject = new ArrayList<>(Arrays.asList(
                "Computer",
                "Chemical"
        ));
        ArrayAdapter<String> adapterSubject = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subject);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }
}
