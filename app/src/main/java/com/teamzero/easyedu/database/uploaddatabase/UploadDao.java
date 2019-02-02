package com.teamzero.easyedu.database.uploaddatabase;

import com.teamzero.easyedu.models.UploadDocumentModel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UploadDao {

    @Query("SELECT * FROM Uploads")
    LiveData<List<UploadDocumentModel>> getAllUploads();

    @Query("SELECT * FROM Uploads where subject = :subject")
    LiveData<List<UploadDocumentModel>> getUploadsBySubject(String subject);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UploadDocumentModel uploadDocumentModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<UploadDocumentModel> uploadDocumentModelList);

    @Query("DELETE FROM Uploads")
    void deleteAll();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(UploadDocumentModel uploadDocumentModel);
}
