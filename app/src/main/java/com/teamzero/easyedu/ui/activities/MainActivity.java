package com.teamzero.easyedu.ui.activities;

import android.os.Bundle;

import com.teamzero.easyedu.R;
import com.teamzero.easyedu.ui.fragments.HomeFragment;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        showFragment();
    }

    private void showFragment() {
        HomeFragment home = new HomeFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_main_container, home)
                .commit();
    }
}
