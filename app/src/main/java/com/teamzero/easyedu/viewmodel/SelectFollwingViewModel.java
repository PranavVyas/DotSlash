package com.teamzero.easyedu.viewmodel;

import android.app.Application;

import com.teamzero.easyedu.database.followingdatabase.FollowingEntry;
import com.teamzero.easyedu.repository.SelectFollowingRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class SelectFollwingViewModel extends AndroidViewModel {
    private LiveData<List<FollowingEntry>> followers;
    private SelectFollowingRepository repository;

    public SelectFollwingViewModel(@NonNull Application application) {
        super(application);
        repository = new SelectFollowingRepository(application);
        followers = repository.getAllFollowers();
    }

    public LiveData<List<FollowingEntry>> getFollowers() {
        return followers;
    }
}
