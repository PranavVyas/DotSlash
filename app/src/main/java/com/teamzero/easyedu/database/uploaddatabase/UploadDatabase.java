package com.teamzero.easyedu.database.uploaddatabase;

import android.content.Context;

import com.teamzero.easyedu.models.UploadDocumentModel;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = UploadDocumentModel.class, version = 1, exportSchema = false)
public abstract class UploadDatabase extends RoomDatabase {

    private static final String DB_NAME = "Upload Database";
    private static final Object LOCK = new Object();
    private static UploadDatabase sInstance;

    public static UploadDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        UploadDatabase.class,
                        DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
                return sInstance;
            }
        } else {
            return sInstance;
        }
    }

    public abstract UploadDao uploadDao();
}
