package com.uottawa.colbytodd.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CookWelcome extends AppCompatActivity {
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_welcome);
        Intent intent = getIntent();
        email = intent.getStringExtra("EMAIL");

        TextView welcome = findViewById(R.id.welcome);
        welcome.setText("You are logged in as " + email);
    }
    public void logOff(View v){
        startActivity(new Intent(this, MainActivity.class));
    }

    public void onViewMenuClick(View v){
        Intent intent = new Intent(this, menuListActivity.class);
        intent.putExtra("EMAIL", email);
        startActivity(intent);
    }
}