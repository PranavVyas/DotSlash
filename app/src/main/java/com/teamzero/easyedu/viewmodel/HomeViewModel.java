package com.teamzero.easyedu.viewmodel;

import android.app.Application;

import com.teamzero.easyedu.models.UploadDocumentModel;
import com.teamzero.easyedu.repository.HomeRepository;
import com.teamzero.easyedu.utils.FireStoreQueryLiveData;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class HomeViewModel extends AndroidViewModel {

    private FireStoreQueryLiveData data;
    private HomeRepository homeRepository;
    private LiveData<List<UploadDocumentModel>> uploads;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        homeRepository = new HomeRepository(application);
        uploads = homeRepository.getCachedData();
    }

    public void cacheDataInSql(List<UploadDocumentModel> uploadDocumentModels) {
        homeRepository.cacheDataInSql(uploadDocumentModels);
    }

    public LiveData<List<UploadDocumentModel>> getCachedData() {
        return uploads;
    }
}
