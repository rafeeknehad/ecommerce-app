package com.example.onlineshoppingisa.activity2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlineshoppingisa.MainActivity;
import com.example.onlineshoppingisa.R;
import com.example.onlineshoppingisa.dialog.DataPickerFragment;
import com.example.onlineshoppingisa.models.User;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Objects;

public class MainActivity2 extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    //private static final String TAG = "MainActivity2";
    //ui
    private TextInputLayout emailText;
    private TextInputLayout userNameText;
    private TextInputLayout passText;
    private TextInputLayout confirmPassText;
    private TextInputLayout jobText;
    private RadioGroup radioGroup;
    private TextView dataOfBirth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        emailText = findViewById(R.id.main_activity2_email);
        userNameText = findViewById(R.id.main_activity2_username);
        passText = findViewById(R.id.main_activity2_pass);
        confirmPassText = findViewById(R.id.main_activity2_confirm_pass);
        dataOfBirth = findViewById(R.id.main_activity2_data_birth);
        Button loginBtn = findViewById(R.id.main_activity2_sign_up_btn);
        jobText = findViewById(R.id.main_activity2_job);
        radioGroup = findViewById(R.id.main_activity2_radio_grop);

        loginBtn.setOnClickListener(v -> {
            boolean res = confirmInput();
            if (!res) {
                return;
            }
            RadioButton selectedGender = findViewById(radioGroup.getCheckedRadioButtonId());
            User user = new User(Objects.requireNonNull(emailText.getEditText()).getText().toString().trim(),
                    Objects.requireNonNull(userNameText.getEditText()).getText().toString().trim(),
                    Objects.requireNonNull(passText.getEditText()).getText().toString().trim(),
                    Objects.requireNonNull(jobText.getEditText()).getText().toString().trim(),
                    selectedGender.getText().toString(),
                    dataOfBirth.getText().toString());
            addNewUserFun(user);
        });

        dataOfBirth.setOnLongClickListener(v -> {
            DataPickerFragment dataPickerFragment = new DataPickerFragment();
            dataPickerFragment.show(getSupportFragmentManager(), "data picker");
            return true;
        });

    }

    private void addNewUserFun(User user) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference users = firebaseFirestore.collection("Users");
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPass())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        user.setUserAuthId(firebaseAuth.getUid());
                        users.add(user);
                        Toast.makeText(getApplicationContext(),"User Add",Toast.LENGTH_SHORT).show();
                        initialField();
                        Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),"The User is exist",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean confirmInput() {
        return !(!validEmail() | !validUsername() | !validPassword() | !validJob() | !validDataOFBirth());
    }

    private Boolean validEmail() {
        String email = Objects.requireNonNull(emailText.getEditText()).getText().toString().trim();
        if (email.equals("")) {
            emailText.setErrorEnabled(true);
            emailText.setError("Field can't be empty");
            return false;
        }
        emailText.setErrorEnabled(false);
        return true;
    }

    private Boolean validUsername() {
        String userName = Objects.requireNonNull(userNameText.getEditText()).getText().toString().trim();
        if (userName.equals("")) {
            userNameText.setErrorEnabled(true);
            userNameText.setError("Field can't be empty");
            return false;
        }
        userNameText.setErrorEnabled(false);
        return true;
    }

    private Boolean validPassword() {
        String pass = Objects.requireNonNull(passText.getEditText()).getText().toString().trim();
        String confirmPass = Objects.requireNonNull(confirmPassText.getEditText()).getText().toString().trim();
        passText.setErrorEnabled(true);
        confirmPassText.setErrorEnabled(true);
        if (pass.equals("")) {
            passText.setError("Field can't be empty");
        }
        if (confirmPass.equals("")) {
            confirmPassText.setError("Field can't be empty");
        }
        if (!pass.equals(confirmPass) && !pass.equals("") && !confirmPass.equals("")) {
            passText.setError("Password don't match");
            confirmPassText.setError("Password don't match");
            return false;
        }
        if (pass.length() < 6) {
            passText.setError("Password must be at least 6 characters");
            confirmPassText.setError("Password must be at least 6 characters");
            return false;
        }
        passText.setErrorEnabled(false);
        confirmPassText.setErrorEnabled(false);
        return true;
    }

    private Boolean validJob() {
        String job = Objects.requireNonNull(jobText.getEditText()).getText().toString().trim();
        if (job.equals("")) {
            jobText.setErrorEnabled(true);
            jobText.setError("Field can't be empty");
            return false;
        }
        jobText.setErrorEnabled(false);
        return true;
    }

    private Boolean validDataOFBirth() {
        String text = dataOfBirth.getText().toString();
        if (text.equals(getString(R.string.data_of_birth))) {
            dataOfBirth.setError("Field can't be empty");

            return false;
        }
        dataOfBirth.setError("");
        dataOfBirth.setError(null);
        return true;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentData = DateFormat.getDateInstance().format(calendar.getTime());
        dataOfBirth.setText(currentData);
    }

    public void initialField() {
        Objects.requireNonNull(emailText.getEditText()).setText("");
        Objects.requireNonNull(userNameText.getEditText()).setText("");
        Objects.requireNonNull(passText.getEditText()).setText("");
        Objects.requireNonNull(confirmPassText.getEditText()).setText("");
        Objects.requireNonNull(jobText.getEditText()).setText("");
        dataOfBirth.setText(getString(R.string.data_of_birth));
        radioGroup.check(R.id.main_activity2_male);
    }
}