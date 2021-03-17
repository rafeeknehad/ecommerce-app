package com.example.onlineshoppingisa;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlineshoppingisa.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfile extends AppCompatActivity {

    private static final int PICK_RESULT = 1;

    private CircleImageView mCircleImageView;
    private FloatingActionButton mAddPhoto;
    private TextView mUserName;
    private TextView mEmail;
    private TextView mJob;
    private TextView mGender;
    private TextView mDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        initComponentFun();
        setUserDataFun();
        mAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGalleryFun();
            }
        });
    }

    private void openGalleryFun() {

    }

    private void setUserDataFun() {
        User user = MainActivity.currentUser;
        mUserName.setText(String.format("%s: %s", mUserName.getText(), user.getUserName()));
        mEmail.setText(String.format("%s: %s", mEmail.getText(), user.getEmail()));
        mJob.setText(String.format("%s: %s", mJob.getText(), user.getJob()));
        mGender.setText(String.format("%s: %s", mGender.getText(), user.getGender()));
        mDate.setText(String.format("%s: %s", mDate.getText(), user.getBirthData()));
    }

    private void initComponentFun() {
        mCircleImageView = findViewById(R.id.activity_user_profile_imageView);
        mAddPhoto = findViewById(R.id.activity_user_profile_add_photo);
        mUserName = findViewById(R.id.activity_user_profile_userName);
        mEmail = findViewById(R.id.activity_user_profile_email);
        mJob = findViewById(R.id.activity_user_profile_job);
        mGender = findViewById(R.id.activity_user_profile_gender);
        mDate = findViewById(R.id.activity_user_profile_date);
    }
}