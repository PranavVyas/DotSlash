package com.teamzero.easyedu.repository;

import android.content.Context;

import com.google.firebase.firestore.Query;
import com.teamzero.easyedu.utils.FireStoreQueryLiveData;

public class HomeRepository {

    private Context context;

    public HomeRepository(Context context) {
        this.context = context;
    }

    public FireStoreQueryLiveData getData(Query query) {
        FireStoreQueryLiveData data = new FireStoreQueryLiveData(query);
        return data;
    }
}
