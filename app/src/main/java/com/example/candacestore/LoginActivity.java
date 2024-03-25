package com.example.candacestore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText login,Password;
    Button Blogin,Bregister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Show();
        giveData();
    }
    public void Show()
    {
        login = findViewById(R.id.login);
        Password = findViewById(R.id.Password);
        Blogin = findViewById(R.id.Blogin);
        Bregister = findViewById(R.id.Bregister);
        Bregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoRegister();
            }
        });
        Blogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }
    private void GotoRegister() {
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
        finish();
    }
    private void giveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username","");
        String password = sharedPreferences.getString("password","");

        login.setText(username);
        Password.setText(password);
    }
    private void loginUser() {
        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        String savedUsername  = sharedPreferences.getString("username","");
        String savedPassword  = sharedPreferences.getString("password","");

        String inputUsername = login.getText().toString();
        String inputPassword = Password.getText().toString();

        if (inputUsername.equals(savedUsername) && inputPassword.equals(savedPassword)) {
            startActivity(new Intent(LoginActivity.this, CandyActivity.class));
            finish();
        } else {
            Toast.makeText(LoginActivity.this, "Username or password incorrect", Toast.LENGTH_SHORT).show();
        }
    }

}