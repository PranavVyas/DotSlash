package com.teamzero.easyedu.ui.activities;

import android.os.Bundle;

import com.teamzero.easyedu.R;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}
