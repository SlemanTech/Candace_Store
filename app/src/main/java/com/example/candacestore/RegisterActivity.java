package com.example.candacestore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText Name , Username,Password,Email,Phone;
    Button Gender , Save,backtohome;
    String selectedGender;
    private static final String SAVEDATA = "data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        show();
    }
    public void show(){
        Name = findViewById(R.id.Name);
        Username = findViewById(R.id.Username);
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        Phone = findViewById(R.id.Phone);
        backtohome=findViewById(R.id.backtohome);
        Gender = findViewById(R.id.Gender);
        Save = findViewById(R.id.SaveRegister);
        Gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogGender();
            }
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveData();
            }
        });
        backtohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backtologin();
            }
        });
    }

    private void backtologin() {
        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }


    private void showDialogGender() {
        final String[] genders = {"Male" , "Female"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Gender").setSingleChoiceItems(genders, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedGender = genders[which];
            }
        });
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Gender.setText(selectedGender);
            }
        });
        builder.setNegativeButton("Cancel",null);
        builder.show();
    }


    private void SaveData() {
        String name = Name.getText().toString().trim();
        String username = Username.getText().toString().trim();
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();
        String phone = Phone.getText().toString().trim();

        if (name.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty()) {
            AlertDialog.Builder emptyFieldsDialog = new AlertDialog.Builder(this);
            emptyFieldsDialog.setTitle("Error");
            emptyFieldsDialog.setMessage("All fields must be filled out");
            emptyFieldsDialog.setPositiveButton("OK", null);
            emptyFieldsDialog.show();
        } else {
            SharedPreferences sharedPreferences = getSharedPreferences(SAVEDATA, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("name", name);
            editor.putString("username", username);
            editor.putString("email", email);
            editor.putString("password", password);
            editor.putString("phone", phone);
            editor.putString("gender",selectedGender);
            editor.apply();

            sureSave();
        }
    }

    private  void sureSave(){
        AlertDialog.Builder Confirmation = new AlertDialog.Builder(this);

        Confirmation.setTitle("Confirmation");
        Confirmation.setMessage("Are you sure you want to save");
        Confirmation.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this,SplashActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Confirmation.setNegativeButton("No",null);
        Confirmation.show();

    }

}