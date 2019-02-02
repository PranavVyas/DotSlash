package com.teamzero.easyedu.viewmodel;

import android.app.Application;

import com.google.firebase.storage.UploadTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class UploadDocumentViewModel extends AndroidViewModel {

    private UploadTask uploadTask;

    public UploadDocumentViewModel(@NonNull Application application) {
        super(application);
    }

    public UploadTask getUploadTask() {
        return uploadTask;
    }

    public void setUploadTask(UploadTask uploadTask) {
        this.uploadTask = uploadTask;
    }
}
