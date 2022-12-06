package com.uottawa.colbytodd.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ClientWelcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_welcome);
    }
    public void logOff(View v){
        startActivity(new Intent(this, MainActivity.class));
    }

    public void goToSeachMeals(View v){
        Bundle extras = getIntent().getExtras();
        String email = extras.getString("email");
        Intent i = new Intent(this, clientMealList.class);
        i.putExtra("email", email);
        startActivity(i);
    }

}