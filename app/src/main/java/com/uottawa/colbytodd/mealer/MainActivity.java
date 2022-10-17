package com.uottawa.colbytodd.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void openRegister(View v){
        startActivity(new Intent(this, Register.class));
    }
    public void openSignInSelect(View v){
        startActivity(new Intent(this, signinselect.class));
    }
}