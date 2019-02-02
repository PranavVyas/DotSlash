package com.teamzero.easyedu.database.followingdatabase;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

public abstract class FollowingDatabase extends RoomDatabase {

    public static final String DB_NAME = "Following Database";
    public static final Object LOCK = new Object();
    private static FollowingDatabase sInstance;

    public static FollowingDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext()
                        , FollowingDatabase.class, DB_NAME).fallbackToDestructiveMigration().build();
            }
        }
        return sInstance;
    }

    public abstract FollowingDao followingDao();

}
