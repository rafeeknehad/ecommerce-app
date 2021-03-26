package com.example.onlineshoppingisa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlineshoppingisa.activity3.MainActivity3;
import com.example.onlineshoppingisa.models.User;
import com.example.onlineshoppingisa.sign_up.MainActivity2;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;


public class MainActivity extends AppCompatActivity {

    //final
    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE = 1;
    public static User currentUser = null;
    //ui
    private TextInputLayout emailTxt;
    private TextInputLayout passTxt;

    //variable
    private SharedPreferences sharedPreferences;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emailTxt = findViewById(R.id.input_email);
        passTxt = findViewById(R.id.input_password);
        Button loginBtn = findViewById(R.id.btn_login);
        Button signBtn = findViewById(R.id.btn_create_account);
        TextView forgetPass = findViewById(R.id.txt_forget_pass);
        CheckBox rememberMe = findViewById(R.id.remember_me);
        sharedPreferences = getSharedPreferences("rememberMe", MODE_PRIVATE);
        boolean rememberMeBoolean = sharedPreferences.getBoolean("checked", false);
        if (rememberMeBoolean) {
            String userName = sharedPreferences.getString("userName", null);
            String password = sharedPreferences.getString("password", null);
            if (userName != null && password != null)
                firebaseAuth.signInWithEmailAndPassword(userName, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                getUserDataFun();
                            }
                        });
        } else {
            Toast.makeText(this, "Please Login In", Toast.LENGTH_SHORT).show();
        }
        signBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
            startActivityForResult(intent, REQUEST_CODE);
        });
        loginBtn.setOnClickListener(v -> {
            EditText editText1 = emailTxt.getEditText();
            EditText editText2 = passTxt.getEditText();
            if (editText1 != null && editText2 != null) {
                String email = editText1.getText().toString().trim();
                String pass = editText2.getText().toString().trim();
                if (!email.equals("") && !pass.equals("")) {
                    firebaseAuth.signInWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    getUserDataFun();
                                }
                            });
                }
            }
        });
        forgetPass.setOnClickListener(v -> {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            if (emailTxt.getEditText() != null) {
                firebaseAuth.sendPasswordResetEmail(emailTxt.getEditText().getText().toString().trim())
                        .addOnCompleteListener(task -> {
                            if (task.isComplete()) {
                                Toast.makeText(MainActivity.this, "Send Email", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        rememberMe.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked && emailTxt.getEditText() != null && passTxt.getEditText() != null) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("checked", true);
                editor.putString("userName", emailTxt.getEditText().getText().toString().trim());
                editor.putString("password", passTxt.getEditText().getText().toString().trim());
                editor.apply();
            } else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                /*SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("checked", false);
                editor.putString("userName", "");
                editor.putString("password", "");
                editor.apply();*/
            }
        });
    }

    private void getUserDataFun() {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference reference = firebaseFirestore.collection("Users");
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Query query = reference.whereEqualTo("userAuthId", FirebaseAuth.getInstance().getCurrentUser().getUid());
            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult() != null) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        currentUser = documentSnapshot.toObject(User.class);
                        currentUser.setIdKey(documentSnapshot.getId());
                        Log.d(TAG, "onComplete: success " + currentUser.getIdKey());
                        finish();
                        Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                        startActivityForResult(intent, REQUEST_CODE);
                    }
                } else {
                    Log.d(TAG, "onComplete: error");
                }
            });
        }
    }
}