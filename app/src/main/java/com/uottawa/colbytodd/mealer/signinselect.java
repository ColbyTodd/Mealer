package com.uottawa.colbytodd.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class signinselect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signinselect);
    }
    public void signInClient(View v){
        startActivity(new Intent(this, SigninScreenClient.class));
    }
    public void signInCook(View v){
        startActivity(new Intent(this, SigninScreenCook.class));
    }
    public void signInAdmin(View v){
        startActivity(new Intent(this, SigninScreenAdmin.class));
    }
}