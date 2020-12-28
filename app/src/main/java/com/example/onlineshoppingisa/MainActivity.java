package com.example.onlineshoppingisa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.onlineshoppingisa.models.User;
import com.example.onlineshoppingisa.roomdatabase.ProductDataBase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //final
    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE = 1;

    //ui
    private TextInputLayout emailTxt;
    private TextInputLayout passTxt;
    private Button loginBtn;
    private Button signinBtn;
    private TextView forgetPass;
    private CheckBox rememberMe;

    //variable
    private MainActivity2Model mainActivity2Model;
    private List<User> allDataOfUsers;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: aaaaa ");
        emailTxt = findViewById(R.id.input_email);
        passTxt = findViewById(R.id.input_password);
        loginBtn = findViewById(R.id.btn_login);
        signinBtn = findViewById(R.id.btn_create_account);
        forgetPass = findViewById(R.id.txt_forget_pass);
        rememberMe = findViewById(R.id.remember_me);
        ProductDataBase productDataBase = ProductDataBase.grtInstance(MainActivity.this);
        mainActivity2Model = ViewModelProviders.of(this).get(MainActivity2Model.class);
        sharedPreferences = getSharedPreferences("rememberMe", MODE_PRIVATE);


        Boolean rememberMeBoolean = sharedPreferences.getBoolean("checked", false);
        if (rememberMeBoolean == true) {
            String userName = sharedPreferences.getString("userName", null);
            String password = sharedPreferences.getString("password", null);
            mainActivity2Model.loginUser(userName, password).observe(MainActivity.this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {
                    Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                    startActivityForResult(intent, REQUEST_CODE);
                }
            });
        } else if (rememberMeBoolean == false) {
            Toast.makeText(this, "Please Login IN", Toast.LENGTH_SHORT).show();
        }

        mainActivity2Model.getAllUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                allDataOfUsers = users;
            }
        });

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailTxt.getEditText().getText().toString().trim();
                String pass = passTxt.getEditText().getText().toString().trim();
                mainActivity2Model.loginUser(email, pass).observe(MainActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                        startActivityForResult(intent, REQUEST_CODE);
                    }
                });
            }
        });

        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.sendPasswordResetEmail(emailTxt.getEditText().getText().toString().trim())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isComplete()) {
                                    Toast.makeText(MainActivity.this, "Send Emai", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        rememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("checked", true);
                    editor.putString("userName", emailTxt.getEditText().getText().toString().trim());
                    editor.putString("password", passTxt.getEditText().getText().toString().trim());
                    editor.apply();
                    Toast.makeText(MainActivity.this, "Checked", Toast.LENGTH_SHORT).show();
                } else if (!isChecked) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("checked", false);
                    editor.putString("userName", "");
                    editor.putString("password", "");
                    editor.apply();
                    Toast.makeText(MainActivity.this, "Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}