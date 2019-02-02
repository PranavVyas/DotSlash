package com.teamzero.easyedu.database.followingdatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = FollowingEntry.class, version = 1, exportSchema = false)
public abstract class FollowingDatabase extends RoomDatabase {

    private static final String DB_NAME = "Following Database";
    private static final Object LOCK = new Object();
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
