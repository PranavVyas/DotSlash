package com.teamzero.easyedu.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.teamzero.easyedu.R;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.profile_picture)
    ImageView profilePicture;
    @BindView(R.id.profile_username)
    TextView profileUsername;
    @BindView(R.id.profile_email)
    TextView profileEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            Glide.with(this).load(currentUser.getPhotoUrl()).into(profilePicture);
            profileUsername.setText(currentUser.getDisplayName());
            profileEmail.setText(currentUser.getEmail());
        }
    }
}
