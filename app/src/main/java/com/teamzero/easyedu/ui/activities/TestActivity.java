package com.teamzero.easyedu.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.teamzero.easyedu.R;
import com.teamzero.easyedu.models.QuestionModel;
import com.teamzero.easyedu.utils.FireStoreQueryLiveData;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import butterknife.ButterKnife;

public class TestActivity extends AppCompatActivity {

    private static final String EXTRA_NO_OF_QUESTIONS = "NoOfQuestions";
    private static final String EXTRA_SUBJECT_NAME = "NameOfSubject";
    private int sizeOfQuestions;
    private String subjectName = "";

    private FirebaseFirestore mDb;
    private CollectionReference collectionReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            sizeOfQuestions = extras.getInt(EXTRA_NO_OF_QUESTIONS);
            subjectName = extras.getString(EXTRA_SUBJECT_NAME);
        }
        initDatabase();
    }

    private void initDatabase() {
        mDb = FirebaseFirestore.getInstance();
        collectionReference = mDb.collection("Questions");
    }


    private void getFireStoreLiveData(Query query) {
        FireStoreQueryLiveData liveData = new FireStoreQueryLiveData(query);
        liveData.observe(this, new Observer<QuerySnapshot>() {
            @Override
            public void onChanged(QuerySnapshot queryDocumentSnapshots) {
                List<QuestionModel> questionModels = queryDocumentSnapshots.toObjects(QuestionModel.class);
                //TODO shkjjhsjsdds
            }
        });
    }

    private boolean validateSubject() {
        if (subjectName.length() == 0) {
            Toast.makeText(this, "No Subject Selected", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateNoOfQuestions() {
        if (sizeOfQuestions < 1) {
            Toast.makeText(this, "No Of Questions are not correct", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void onRadioButtonClicked(View view) {


    }
}
