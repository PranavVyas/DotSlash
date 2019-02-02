package com.teamzero.easyedu.repository;

import android.content.Context;

import com.google.firebase.firestore.Query;
import com.teamzero.easyedu.database.uploaddatabase.UploadDao;
import com.teamzero.easyedu.database.uploaddatabase.UploadDatabase;
import com.teamzero.easyedu.models.UploadDocumentModel;
import com.teamzero.easyedu.utils.AppExecutors;
import com.teamzero.easyedu.utils.FireStoreQueryLiveData;

import java.util.List;

import androidx.lifecycle.LiveData;

public class HomeRepository {

    private Context context;
    private UploadDao uploadDao;

    public HomeRepository(Context context) {
        this.context = context;
        uploadDao = UploadDatabase.getInstance(context).uploadDao();
    }

    public FireStoreQueryLiveData getData(Query query) {
        FireStoreQueryLiveData data = new FireStoreQueryLiveData(query);
        return data;
    }

    public void cacheDataInSql(List<UploadDocumentModel> uploadDocumentModels) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                uploadDao.insertAll(uploadDocumentModels);
            }
        });
    }

    public LiveData<List<UploadDocumentModel>> getCachedData() {
        return uploadDao.getAllUploads();
    }
}
