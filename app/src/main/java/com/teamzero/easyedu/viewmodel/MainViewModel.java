package com.teamzero.easyedu.viewmodel;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class MainViewModel extends AndroidViewModel {

    private FirebaseUser currUser;
    private int identifier;

    public MainViewModel(@NonNull Application application) {
        super(application);
        currUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public FirebaseUser getCurrUser() {
        return currUser;
    }

    public void setCurrUser(FirebaseUser currUser) {
        this.currUser = currUser;
    }

    public void setCurrentFragmentId(int identifier) {
        this.identifier = identifier;
    }

    public int getCuurentFragmentId() {
        return identifier;
    }

}
