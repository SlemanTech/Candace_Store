package com.example.candacestore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity {

    EditText NameEditText, usernameEditText, passwordEditText, emailEditText;
    TextView birthDateTextView, genderTextView;
    ImageView datePickerIcon;
    Button saveButton,backtohome;

    String[] genders = {"Male", "Female"};
    String selectedGender;

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        show();
    }

    public void show() {
        NameEditText = findViewById(R.id.NameEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        emailEditText = findViewById(R.id.emailEditText);
        birthDateTextView = findViewById(R.id.birthDateTextView);
        genderTextView = findViewById(R.id.genderTextView);
        datePickerIcon = findViewById(R.id.datePickerIcon);
        saveButton = findViewById(R.id.saveButton);
        backtohome  = findViewById(R.id.backtohome);

        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        loadProfileData();

        datePickerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        genderTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGenderDialog();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSaveConfirmationDialog();
            }
        });
        backtohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gottomain();
            }
        });
    }

    private void gottomain() {
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void loadProfileData() {
        NameEditText.setText(sharedPreferences.getString("name", ""));
        usernameEditText.setText(sharedPreferences.getString("username", ""));
        passwordEditText.setText(sharedPreferences.getString("password", ""));
        emailEditText.setText(sharedPreferences.getString("email", ""));
        birthDateTextView.setText(sharedPreferences.getString("birthDate", ""));
        genderTextView.setText(sharedPreferences.getString("gender", ""));
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String birthDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                birthDateTextView.setText(birthDate);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void showGenderDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Gender");
        builder.setSingleChoiceItems(genders, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedGender = genders[which];
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                genderTextView.setText(selectedGender);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void showSaveConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Save Profile");
        builder.setMessage("Are you sure you want to save your profile?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveProfileData();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }

    private void saveProfileData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("firstName", NameEditText.getText().toString());
        editor.putString("username", usernameEditText.getText().toString());
        editor.putString("password", passwordEditText.getText().toString());
        editor.putString("email", emailEditText.getText().toString());
        editor.putString("birthDate", birthDateTextView.getText().toString());
        editor.putString("gender", genderTextView.getText().toString());
        editor.apply();
        Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
        Toast.makeText(this, "Profile saved successfully!", Toast.LENGTH_SHORT).show();
    }
}