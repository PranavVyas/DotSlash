package com.teamzero.easyedu.utils;

import android.app.Activity;
import android.view.View;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.teamzero.easyedu.R;

import androidx.appcompat.widget.Toolbar;

public class NavigationUtils {

    public static final int ID_HOME = 1;
    public static final int ID_SETTINGS = 2;

    public Drawer getDrawer(Activity activity, Toolbar toolbar) {
        OnDrawerItemSelectedListener mCallback = (OnDrawerItemSelectedListener) activity;
        PrimaryDrawerItem home = new PrimaryDrawerItem()
                .withName("Home")
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        mCallback.onDrawerItemSelected(ID_HOME);
                        return false;
                    }
                });

        PrimaryDrawerItem settings = new PrimaryDrawerItem()
                .withName("Settings")
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        mCallback.onDrawerItemSelected(ID_SETTINGS);
                        return false;
                    }
                });

        AccountHeader accountHeader = new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.drawable.ic_launcher_background)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName("Team[0]")
                                .withEmail("team[0]@gmail.com")
                                .withIcon(activity.getResources().getDrawable(R.drawable.ic_launcher_foreground))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        Drawer drawer = new DrawerBuilder()
                .withActivity(activity)
                .withToolbar(toolbar)
                .withAccountHeader(
                        accountHeader
                )
                .addDrawerItems(
                        home,
                        settings
                )
                .build();

        return drawer;
    }


    public interface OnDrawerItemSelectedListener {
        void onDrawerItemSelected(int identifier);
    }
}
