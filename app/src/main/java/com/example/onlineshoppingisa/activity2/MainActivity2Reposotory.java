package com.example.onlineshoppingisa.activity2;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshoppingisa.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2Reposotory {

    private static final String TAG = "MainActivity2Reposotory";
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference users = firebaseFirestore.collection("Users");
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private MutableLiveData<List<User>> usersLiveData;
    private MutableLiveData<Boolean> loginLiveData;

    public MainActivity2Reposotory() {
        usersLiveData = new MutableLiveData<>();
        loginLiveData = new MutableLiveData<>();
    }

    //MainActivity2
    public void addUsers(User user) {
        users.add(user);
    }

    public void addUserAuth(User user) {
        new AddUserAuthAsync(user).execute();
    }

    public LiveData<List<User>> getAllUsers() {
        new GetAllUsersAsync().execute();
        return usersLiveData;
    }

    public LiveData<Boolean> loginUser(String email, String pass) {
        firebaseAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        loginLiveData.setValue(task.isComplete());
                    }
                });
        return loginLiveData;
    }

    public class AddUserAuthAsync extends AsyncTask<Void, Void, Void> {

        private User user;

        AddUserAuthAsync(User user) {
            this.user = user;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPass())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            user.setUserAuthId(firebaseAuth.getUid());
                            addUsers(user);

                        } else {
                            Log.d(TAG, "onComplete: addUserAuth Error");
                        }
                    });
            return null;
        }
    }

    public class  GetAllUsersAsync extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            users.get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        List<User> data = new ArrayList<>();
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            User user = documentSnapshot.toObject(User.class);
                            user.setIdKey(documentSnapshot.getId());
                            data.add(user);
                        }
                        usersLiveData.setValue(data);
                    })
                    .addOnFailureListener(e -> {
                    });
            return null;
        }
    }
}
