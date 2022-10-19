package com.uottawa.colbytodd.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
    public void openClientRegistration(View v){
        startActivity(new Intent(this, RegisterClient.class));
    }
    public void openCookRegistration(View v){
        startActivity(new Intent(this, RegisterCook.class));
    }
}