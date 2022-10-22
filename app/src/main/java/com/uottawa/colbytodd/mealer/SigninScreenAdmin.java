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

public class SigninScreenAdmin extends AppCompatActivity {
    private static final String TAG = "SigninScreenAdmin";

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_screen_admin);
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
                    startActivity(new Intent(this, AdminWelcome.class));
                } else {
                    message.setText("Password is incorrect");
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void loginUser(String email, String password, TextView message){
        DocumentReference mDocRef = db.collection("Administrator").document(email);
        mDocRef.get().addOnCompleteListener(task -> {
            if (task.getResult().exists()) {
                DocumentSnapshot document = task.getResult();
                validateUser(document, password, message);
            } else {
                message.setText("Input Error");
            }
        });
    }

    public void openAdminWelcome(View v){
        EditText emailInput = (EditText) findViewById(R.id.AdminEmail);
        EditText passwordInput = (EditText) findViewById(R.id.AdminPassword);
        TextView messageText = findViewById(R.id.AdminMessage);
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