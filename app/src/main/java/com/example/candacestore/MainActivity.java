package com.example.candacestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView  textView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));
        show();
    }

    private void show() {
       textView1 = findViewById(R.id.textView1);
       SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);

        String name = sharedPreferences.getString("name", "");

        textView1.setText("Hello " + name);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_cart){
            Intent intent = new Intent(MainActivity.this,ShoppingCartActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if(id == R.id.menu_profile){
            Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if(id == R.id.menu_about){
            Intent intent = new Intent(MainActivity.this,AboutActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if(id== R.id.menu_logout){
            SharedPreferences sharedPreferences = getSharedPreferences("Candy", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if(id == R.id.menu_candy){
            Intent intent = new Intent(MainActivity.this , CandyActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}