package com.example.onlineshoppingisa.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.onlineshoppingisa.MainActivity;
import com.example.onlineshoppingisa.R;
import com.example.onlineshoppingisa.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class UserProfileFragment extends Fragment {


    private static final int PICK_RESULT = 1;
    private static final String TAG = "UserProfileFragment";

    private CircleImageView mCircleImageView;
    private EditText mUserName;
    private EditText mEmail;
    private EditText mJob;
    private EditText mGender;
    private EditText mDate;

    private StorageReference mStorageRef;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        FloatingActionButton mAddPhoto = view.findViewById(R.id.activity_user_profile_add_photo);
        Button submitBtn = view.findViewById(R.id.fragment_user_profile_submit);

        mStorageRef = FirebaseStorage.getInstance().getReference("User Image");

        mCircleImageView = view.findViewById(R.id.activity_user_profile_imageView);
        mUserName = view.findViewById(R.id.activity_user_profile_userName);
        mEmail = view.findViewById(R.id.activity_user_profile_email);
        mJob = view.findViewById(R.id.activity_user_profile_job);
        mGender = view.findViewById(R.id.activity_user_profile_gender);
        mDate = view.findViewById(R.id.activity_user_profile_date);
        setUserDataFun();
        mAddPhoto.setOnClickListener(v -> openGalleryFun());
        submitBtn.setOnClickListener(v -> updateUserProfileFun());
        return view;
    }

    private void setUserDataFun() {
        User user = MainActivity.currentUser;
        if (user.getUserImage() == null) {
            Picasso.with(getActivity()).load(R.drawable.profile_image).into(mCircleImageView);
        } else {
            Picasso.with(getActivity()).load(user.getUserImage()).into(mCircleImageView);
        }
        mUserName.setText(String.format("%s: %s", mUserName.getText(), user.getUserName()));
        mEmail.setText(String.format("%s: %s", mEmail.getText(), user.getEmail()));
        mJob.setText(String.format("%s: %s", mJob.getText(), user.getJob()));
        mGender.setText(String.format("%s: %s", mGender.getText(), user.getGender()));
        mDate.setText(String.format("%s: %s", mDate.getText(), user.getBirthData()));
    }

    private void openGalleryFun() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_RESULT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_RESULT && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri vImageUri = data.getData();
            StorageReference vFirebaseStorage = mStorageRef.child(MainActivity.currentUser.getIdKey());
            vFirebaseStorage.putFile(vImageUri)
                    .addOnSuccessListener(taskSnapshot -> vFirebaseStorage.getDownloadUrl()
                            .addOnSuccessListener(this::updateUserProfileImageFun));
            Picasso.with(getActivity()).load(vImageUri).into(mCircleImageView);
            MainActivity.currentUser.setUserImage(String.valueOf(vImageUri));
        }
    }

    private void updateUserProfileImageFun(Uri uri) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        WriteBatch batch = firebaseFirestore.batch();
        DocumentReference documentReference = firebaseFirestore.collection("Users")
                .document(MainActivity.currentUser.getIdKey());

        HashMap<String, Object> newValue = new HashMap<>();
        newValue.put("userImage", String.valueOf(uri));

        batch.update(documentReference, newValue);
        batch.commit()
                .addOnSuccessListener(aVoid -> Log.d(TAG, "onSuccess: 0000 success"));
    }

    private void updateUserProfileFun() {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        WriteBatch batch = firebaseFirestore.batch();
        DocumentReference documentReference = firebaseFirestore.collection("Users")
                .document(MainActivity.currentUser.getIdKey());

        HashMap<String, Object> newValue = new HashMap<>();
        newValue.put("userName", mUserName.getText().toString());
        newValue.put("job", mJob.getText().toString());

        batch.update(documentReference, newValue);
        batch.commit()
                .addOnSuccessListener(aVoid -> Log.d(TAG, "onSuccess: 0000 success"));
    }
}