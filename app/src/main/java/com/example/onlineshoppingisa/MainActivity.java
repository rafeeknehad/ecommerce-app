package com.example.onlineshoppingisa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.onlineshoppingisa.activity2.MainActivity2;
import com.example.onlineshoppingisa.activity2.MainActivity2Model;
import com.example.onlineshoppingisa.activity3.MainActivity3;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    //final
    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE = 1;

    //ui
    private TextInputLayout emailTxt;
    private TextInputLayout passTxt;

    //variable
    private MainActivity2Model mainActivity2Model;
    //private List<User> allDataOfUsers;
    private SharedPreferences sharedPreferences;

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
        mainActivity2Model = ViewModelProviders.of(this).get(MainActivity2Model.class);
        sharedPreferences = getSharedPreferences("rememberMe", MODE_PRIVATE);


        boolean rememberMeBoolean = sharedPreferences.getBoolean("checked", false);
        if (rememberMeBoolean) {
            String userName = sharedPreferences.getString("userName", null);
            String password = sharedPreferences.getString("password", null);
            mainActivity2Model.loginUser(userName, password).observe(MainActivity.this, aBoolean -> {
                Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                startActivityForResult(intent, REQUEST_CODE);
            });
        } else {
            Toast.makeText(this, "Please Login IN", Toast.LENGTH_SHORT).show();
        }

        mainActivity2Model.getAllUser().observe(this, users -> {
            Log.d(TAG, "onChanged: **** " + users.size());
            //allDataOfUsers = users;
        });

        signBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
            startActivityForResult(intent, REQUEST_CODE);
        });

        loginBtn.setOnClickListener(v -> {
            String email = Objects.requireNonNull(emailTxt.getEditText()).getText().toString().trim();
            String pass = Objects.requireNonNull(passTxt.getEditText()).getText().toString().trim();
            mainActivity2Model.loginUser(email, pass).observe(MainActivity.this, aBoolean -> {
                if (aBoolean) {
                    Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                    startActivityForResult(intent, REQUEST_CODE);
                } else {
                    Toast.makeText(MainActivity.this, "Please Sign Up", Toast.LENGTH_SHORT).show();
                }
            });
        });

        forgetPass.setOnClickListener(v -> {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.sendPasswordResetEmail(Objects.requireNonNull(emailTxt.getEditText()).getText().toString().trim())
                    .addOnCompleteListener(task -> {
                        if (task.isComplete()) {
                            Toast.makeText(MainActivity.this, "Send Email", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        rememberMe.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("checked", true);
                editor.putString("userName", Objects.requireNonNull(emailTxt.getEditText()).getText().toString().trim());
                editor.putString("password", Objects.requireNonNull(passTxt.getEditText()).getText().toString().trim());
                editor.apply();
            } else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("checked", false);
                editor.putString("userName", "");
                editor.putString("password", "");
                editor.apply();
            }
        });
    }
}