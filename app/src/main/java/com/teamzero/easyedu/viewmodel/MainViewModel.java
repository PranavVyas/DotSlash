package com.teamzero.easyedu.viewmodel;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.teamzero.easyedu.utils.NavigationUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class MainViewModel extends AndroidViewModel {

    private FirebaseUser currUser;
    private int identifier;

    public MainViewModel(@NonNull Application application) {
        super(application);
        currUser = FirebaseAuth.getInstance().getCurrentUser();
        identifier = NavigationUtils.ID_HOME;
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
