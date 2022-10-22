package com.uottawa.colbytodd.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
        assert signedUser != null;
        for (Map.Entry<String, Object> entry : signedUser.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (Objects.equals(key, "password")) {
                if (Objects.equals(value, password)) {
                    startActivity(new Intent(this, CookWelcome.class));
                } else {
                    message.setText("Password is incorrect");
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void loginUser(String email, String password, TextView message){
        DocumentReference mDocRef = db.collection("cooks").document(email);
        mDocRef.get().addOnCompleteListener(task -> {
            if (task.getResult().exists()) {
                DocumentSnapshot document = task.getResult();
                validateUser(document, password, message);
            } else {
                message.setText("Input Error");
            }
        });
    }

    public void openCookWelcome(View v){
        EditText emailInput = (EditText) findViewById(R.id.CookEmail);
        EditText passwordInput = (EditText) findViewById(R.id.CookPassword);
        TextView messageText = findViewById(R.id.CookMessage);
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        loginUser(email, password, messageText);
    }
}