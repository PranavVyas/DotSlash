package com.teamzero.easyedu.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.mikepenz.materialdrawer.Drawer;
import com.teamzero.easyedu.R;
import com.teamzero.easyedu.ui.fragments.HomeFragment;
import com.teamzero.easyedu.ui.fragments.ProfileFragment;
import com.teamzero.easyedu.ui.fragments.SearchFragment;
import com.teamzero.easyedu.ui.fragments.SettingsFragment;
import com.teamzero.easyedu.utils.NavigationUtils;
import com.teamzero.easyedu.viewmodel.MainViewModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.teamzero.easyedu.utils.NavigationUtils.ID_HOME;


public class MainActivity extends AppCompatActivity implements NavigationUtils.OnDrawerItemSelectedListener {

    @BindView(R.id.toolbar_main)
    Toolbar toolbar;
    private MainViewModel mainViewModel;
    private int currentPositionInViewPager = 0;
    private Drawer drawer;
    private int currentFragmentId;

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        drawer = NavigationUtils.getDrawer(this, toolbar, mainViewModel.getCurrUser());
        currentFragmentId = mainViewModel.getCuurentFragmentId();
        drawer.setSelection(currentFragmentId);
        executeAction(currentFragmentId);
    }

    private void executeAction(int identifier) {
        switch (identifier) {
            case ID_HOME:
                HomeFragment home = new HomeFragment();
                swapFragment(home, identifier);
                mainViewModel.setCurrentFragmentId(identifier);
                actionBar.setTitle("Home");
                break;

            case NavigationUtils.ID_SEARCH:
                SearchFragment searchFragment = new SearchFragment();
                swapFragment(searchFragment, identifier);
                mainViewModel.setCurrentFragmentId(identifier);
                actionBar.setTitle("Search");
                break;

            case NavigationUtils.ID_SETTINGS:
                SettingsFragment settingsFragment = new SettingsFragment();
                swapFragment(settingsFragment, identifier);
                mainViewModel.setCurrentFragmentId(identifier);
                actionBar.setTitle("Settings");
                break;

            case NavigationUtils.ID_MY_PROFILE:
                ProfileFragment profileFragment = new ProfileFragment();
                swapFragment(profileFragment, identifier);
                mainViewModel.setCurrentFragmentId(identifier);
                actionBar.setTitle("My Profile");
                break;

            case NavigationUtils.ID_SIGN_OUT:
                signOutUser();
                break;
        }
    }

    @Override
    public void onDrawerItemSelected(int identifier) {
        executeAction(identifier);
    }

    private void signOutUser() {
        AuthUI.getInstance().signOut(getApplicationContext()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                mainViewModel.setCurrUser(null);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Failed to sign Out", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void swapFragment(Fragment newFragment, int identifier) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_main_container, newFragment)
                .commit();
        currentFragmentId = identifier;
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen()) {
            drawer.closeDrawer();
            return;
        } else if (currentFragmentId != ID_HOME) {
            HomeFragment homeFragment = new HomeFragment();
            swapFragment(homeFragment, ID_HOME);
            mainViewModel.setCurrentFragmentId(ID_HOME);
            drawer.setSelection(ID_HOME);
            return;
        }
        super.onBackPressed();
    }
}
