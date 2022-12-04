package com.uottawa.colbytodd.mealer;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SigninScreenClient extends AppCompatActivity {
    private static final String TAG = "SigninScreenClient";

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference mDocRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_screen_client);

        //Add back button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == android.R.id.home){
            //Ends the activity
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("SetTextI18n")
    private void validateUser(String email, DocumentSnapshot doc, String password, TextView message){
            Map<String, Object> signedUser = doc.getData();
            assert signedUser != null;
            for (Map.Entry<String, Object> entry : signedUser.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (Objects.equals(key, "password")) {
                    if (Objects.equals(value, password)) {
                        Intent i = new Intent(this, ClientWelcome.class);
                        i.putExtra("email", email);
                        startActivity(i);
                    } else {
                        message.setText("Password is incorrect");
                    }
                }
            }
    }

    private void loginUser(String email, String password, TextView message){
        mDocRef = db.collection("clients").document(email);
        mDocRef.get().addOnCompleteListener(task -> {
            if (task.getResult().exists()) {
                DocumentSnapshot document = task.getResult();
                validateUser(email, document, password, message);
            } else {
                message.setText("Input Error");
            }
        });
    }
    public void openClientWelcome(View v){
        EditText emailInput = (EditText) findViewById(R.id.ClientEmail);
        EditText passwordInput = (EditText) findViewById(R.id.ClientPassword);
        TextView messageText = findViewById(R.id.ClientMessage);
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