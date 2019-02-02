package com.teamzero.easyedu.database.followingdatabase;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface FollowingDao {

    @Query("SELECT * FROM FollowingTable")
    LiveData<List<FollowingEntry>> getAllFollowers();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(FollowingEntry entry);
}
