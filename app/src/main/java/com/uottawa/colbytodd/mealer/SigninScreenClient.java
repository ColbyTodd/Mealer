package com.uottawa.colbytodd.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SigninScreenClient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_screen_client);
    }
    public void openClientWelcome(View v){
        EditText email = findViewById(R.id.Email2);
        EditText password = findViewById(R.id.Password2);

        String semail = email.getText().toString();
        String spassword = password.getText().toString();

        TextView t = findViewById(R.id.signInFeedback);

        if(semail.length() < 1){
            t.setText("Please put a valid email");
        }
        else if(spassword.length() > 25 || spassword.length() < 8){
            t.setText("Password must be 8 to 25 characters");
        }
        else{
            startActivity(new Intent(this, ClientWelcome.class));
        }
    }

}