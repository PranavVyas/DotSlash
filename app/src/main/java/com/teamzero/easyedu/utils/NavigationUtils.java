package com.teamzero.easyedu.utils;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;

import com.google.firebase.auth.FirebaseUser;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.teamzero.easyedu.R;

import androidx.appcompat.widget.Toolbar;

public class NavigationUtils {

    public static final int ID_HOME = 1;
    public static final int ID_SETTINGS = 3;
    public static final int ID_MY_PROFILE = 4;
    public static final int ID_SIGN_OUT = 5;
    public static final int ID_SEARCH = 2;


    public static Drawer getDrawer(Activity activity, Toolbar toolbar, FirebaseUser currUser) {
        Uri photoUri = currUser.getPhotoUrl();
        OnDrawerItemSelectedListener mCallback = (OnDrawerItemSelectedListener) activity;
        //TODO Implement Place holder and error image
        PrimaryDrawerItem home = new PrimaryDrawerItem()
                .withName("Home")
                .withIcon(R.drawable.ic_home)
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    mCallback.onDrawerItemSelected(ID_HOME);
                    return false;
                });

        PrimaryDrawerItem myProfile = new PrimaryDrawerItem()
                .withName("My Profile")
                .withIcon(R.drawable.ic_profile)
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    mCallback.onDrawerItemSelected(ID_MY_PROFILE);
                    return false;
                });

        PrimaryDrawerItem search = new PrimaryDrawerItem()
                .withName("Search Items")
                .withIcon(R.drawable.ic_search)
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    mCallback.onDrawerItemSelected(ID_SEARCH);
                    return false;
                });

        PrimaryDrawerItem settings = new PrimaryDrawerItem()
                .withName("Settings")
                .withIcon(R.drawable.ic_settings)
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    mCallback.onDrawerItemSelected(ID_SETTINGS);
                    return false;
                });

        PrimaryDrawerItem signOut = new PrimaryDrawerItem()
                .withName("Sign Out")
                .withIcon(R.drawable.ic_log_in_out)
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    mCallback.onDrawerItemSelected(ID_SIGN_OUT);
                    return false;
                });


        AccountHeader accountHeader = new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.color.primary)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName(currUser.getDisplayName())
                                .withEmail(currUser.getEmail())
                                .withIcon(photoUri)
                                .withTextColor(Color.parseColor("#2196F3"))
                )
                .withOnAccountHeaderListener((view, profile, currentProfile) -> false)
                .build();

        return new DrawerBuilder()
                .withActivity(activity)
                .withToolbar(toolbar)
                .withAccountHeader(
                        accountHeader
                )
                .addDrawerItems(
                        home,
                        search,
                        myProfile,
                        settings,
                        signOut
                )
                .build();
    }


    public interface OnDrawerItemSelectedListener {
        void onDrawerItemSelected(int identifier);
    }
}
