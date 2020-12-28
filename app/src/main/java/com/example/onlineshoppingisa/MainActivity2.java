package com.example.onlineshoppingisa;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.onlineshoppingisa.models.User;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity2 extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = "MainActivity2";
    //ui
    private TextInputLayout emailText;
    private TextInputLayout userNameText;
    private TextInputLayout passText;
    private TextInputLayout confirmPassText;
    private TextInputLayout jobText;
    private RadioGroup radioGroup;
    private TextView dataOfBirth;
    private Button loginBtn;

    //variable
    private MainActivity2Model mainActivity2Model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        emailText = findViewById(R.id.main_activity2_email);
        userNameText = findViewById(R.id.main_activity2_username);
        passText = findViewById(R.id.main_activity2_pass);
        confirmPassText = findViewById(R.id.main_activity2_confirm_pass);
        dataOfBirth = findViewById(R.id.main_activity2_data_birth);
        loginBtn = findViewById(R.id.main_activity2_signin_btn);
        jobText = findViewById(R.id.main_activity2_job);
        radioGroup = findViewById(R.id.main_activity2_radio_grop);
        mainActivity2Model = ViewModelProviders.of(this).get(MainActivity2Model.class);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean res = confirmInput();
                if (res == false) {
                    return;
                }
                RadioButton selectedGender = findViewById(radioGroup.getCheckedRadioButtonId());
                User user = new User(emailText.getEditText().getText().toString().trim(), userNameText.getEditText().getText().toString().trim(),
                        passText.getEditText().getText().toString().trim(), jobText.getEditText().getText().toString().trim(),
                        selectedGender.getText().toString(), dataOfBirth.getText().toString());
                mainActivity2Model.addUserAuth(user);
                initialField();

                Log.d(TAG, "onClick: aaaa ");
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        dataOfBirth.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DataPickerFragment dataPickerFragment = new DataPickerFragment();
                dataPickerFragment.show(getSupportFragmentManager(), "data picker");
                return true;
            }
        });

    }

    private boolean confirmInput() {
        if (!validEmail() | !validUsername() | !validPassword() | !validJob() | !validDataOFBirth()) {
            return false;
        }
        return true;
    }

    private Boolean validEmail() {
        String email = emailText.getEditText().getText().toString().trim();
        if (email.equals("")) {
            emailText.setErrorEnabled(true);
            emailText.setError("Field can't be empty");
            return false;
        }
        emailText.setErrorEnabled(false);
        return true;
    }

    private Boolean validUsername() {
        String userName = userNameText.getEditText().getText().toString().trim();
        if (userName.equals("")) {
            userNameText.setErrorEnabled(true);
            userNameText.setError("Field can't be empty");
            return false;
        }
        userNameText.setErrorEnabled(false);
        return true;
    }

    private Boolean validPassword() {
        String pass = passText.getEditText().getText().toString().trim();
        String confirmPass = confirmPassText.getEditText().getText().toString().trim();
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
            confirmPassText.setError("Password dont't match");
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
        String job = jobText.getEditText().getText().toString().trim();
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
        emailText.getEditText().setText("");
        userNameText.getEditText().setText("");
        passText.getEditText().setText("");
        confirmPassText.getEditText().setText("");
        jobText.getEditText().setText("");
        dataOfBirth.setText(getString(R.string.data_of_birth));
        radioGroup.check(R.id.main_activity2_male);
    }
}