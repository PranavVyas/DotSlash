package com.teamzero.easyedu.repository;

import android.content.Context;

import com.teamzero.easyedu.database.followingdatabase.FollowingDao;
import com.teamzero.easyedu.database.followingdatabase.FollowingDatabase;
import com.teamzero.easyedu.database.followingdatabase.FollowingEntry;

import java.util.List;

import androidx.lifecycle.LiveData;

public class SelectFollowingRepository {

    private Context context;
    private FollowingDao followingDao;

    public SelectFollowingRepository(Context context) {
        this.context = context;
        followingDao = FollowingDatabase.getInstance(context).followingDao();
    }

    public LiveData<List<FollowingEntry>> getAllFollowers() {
        return followingDao.getAllFollowers();
    }

}
