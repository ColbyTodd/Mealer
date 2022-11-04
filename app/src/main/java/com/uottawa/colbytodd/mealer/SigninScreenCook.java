package com.uottawa.colbytodd.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class SigninScreenCook extends AppCompatActivity {
    private static final String TAG = "SigninScreenCook";

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_screen_cook);
    }

    @SuppressLint("SetTextI18n")
    private void validateUser(DocumentSnapshot doc, String password, TextView message){
        Map<String, Object> signedUser = doc.getData();
        Object passDB = null;
        Timestamp suspension = null;
        Date date = new Date();
        Timestamp timestampNow = new Timestamp(date);
        assert signedUser != null;
        for (Map.Entry<String, Object> entry : signedUser.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (Objects.equals(key, "password")) passDB = entry.getValue();
            else if(Objects.equals(key, "suspension")) suspension = (Timestamp) value;
        }
        if(suspension != null && suspension.compareTo(timestampNow) > 0) {
            message.setText("Your account is currently suspended, you will regain access to your account on: " + suspension.toDate());
        }
        else if(!Objects.equals(passDB, password)) message.setText("Password is incorrect");
        else startActivity(new Intent(this, CookWelcome.class));
    }

    @SuppressLint("SetTextI18n")
    private void loginUser(String email, String password, TextView message){
        DocumentReference mDocRef = db.collection("cooks").document(email);
        mDocRef.get().addOnCompleteListener(task -> {
            if (task.getResult().exists()) {
                DocumentSnapshot document = task.getResult();
                validateUser(document, password, message);
            } else {
                message.setText("Email does not exist");
            }
        });
    }

    public void openCookWelcome(View v){
        EditText emailInput = (EditText) findViewById(R.id.CookEmail);
        EditText passwordInput = (EditText) findViewById(R.id.CookPassword);
        TextView messageText = findViewById(R.id.CookMessage);
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        TextView t = findViewById(R.id.signInFeedback);
        if(email.length()!=0 && password.length()!=0){
            loginUser(email, password, messageText);
        }
        else{
            t.setText("Email/Password Cannot be Empty");
        }
    }
}