package com.uottawa.colbytodd.mealer;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterClient extends AppCompatActivity {
    private static final String TAG = "RegisterClient";
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference mDocRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_client);

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }
    // [END on_start_check_user]

    private void createAccount(String first, String last, String email, String password, String address, String card, String expiration, String cvv) {
        // Create a new user with a first and last name
        mDocRef = db.document("clients/" + email);
        Map<String, Object> user = new HashMap<String, Object>();
        user.put("first", first);
        user.put("last", last);
        user.put("email", email);
        user.put("password", password);
        user.put("address", address);
        user.put("card", card);
        user.put("expiration", expiration);
        user.put("cvv", cvv);

        mDocRef.set(user);
    }

    public void openClientWelcome(View v){
        EditText first = (EditText) findViewById(R.id.firstName);
        EditText last = (EditText) findViewById(R.id.lastName);
        EditText email = (EditText) findViewById(R.id.email);
        EditText password = (EditText) findViewById(R.id.password);
        EditText address = (EditText) findViewById(R.id.address);
        EditText card = (EditText) findViewById(R.id.creditCardNumber);
        EditText expiration = (EditText) findViewById(R.id.expiration);
        EditText cvv = (EditText) findViewById(R.id.cvv);

        createAccount(first.getText().toString(),
                last.getText().toString(),
                email.getText().toString(),
                password.getText().toString(),
                address.getText().toString(),
                card.getText().toString(),
                expiration.getText().toString(),
                cvv.getText().toString());

        startActivity(new Intent(this, ClientWelcome.class));
    }

    private void reload() { }

    private void updateUI(FirebaseUser user) {

    }
}

