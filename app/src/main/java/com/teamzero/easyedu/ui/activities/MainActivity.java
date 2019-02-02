package com.teamzero.easyedu.ui.activities;

import android.os.Bundle;

import com.teamzero.easyedu.R;
import com.teamzero.easyedu.ui.fragments.HomeFragment;
import com.teamzero.easyedu.utils.NavigationUtils;
import com.teamzero.easyedu.viewmodel.MainViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements NavigationUtils.OnDrawerItemSelectedListener {

    private MainViewModel mainViewModel;
    private int currentPositionInViewPager = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        showFragment();
    }

    private void showFragment() {
        HomeFragment home = new HomeFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_main_container, home)
                .commit();
    }

    @Override
    public void onDrawerItemSelected(int identifier) {
        switch (identifier) {

        }
    }



}
