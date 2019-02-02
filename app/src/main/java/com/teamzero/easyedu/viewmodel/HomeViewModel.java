package com.teamzero.easyedu.viewmodel;

import android.app.Application;

import com.teamzero.easyedu.repository.HomeRepository;
import com.teamzero.easyedu.utils.FireStoreQueryLiveData;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class HomeViewModel extends AndroidViewModel {

    private FireStoreQueryLiveData data;
    private HomeRepository homeRepository;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        homeRepository = new HomeRepository(application);
    }
}
