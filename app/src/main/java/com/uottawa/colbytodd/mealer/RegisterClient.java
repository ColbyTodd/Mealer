package com.uottawa.colbytodd.mealer;
import androidx.appcompat.app.AppCompatActivity;
import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;
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
    EditText firstName, lastName, email, password, address, creditCardNumber, expiration, CVV, focus;
    String firstNameText="",lastNameText="",emailText="",passwordText="",addressText="",creditCardText="",expirationText="",cvvText="";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference mDocRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_client);
        focus = findViewById(R.id.focus);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        address = findViewById(R.id.address);
        creditCardNumber = findViewById(R.id.creditCardNumber);
        expiration = findViewById(R.id.expiration);
        CVV = findViewById(R.id.cvv);
        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth

        firstName.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(!hasFocus) {
                    firstNameText=firstName.getText().toString();

                    if(!isAllLetters(firstNameText)){
                        Toast.makeText(getApplicationContext(), "Error: Please enter a Valid First Name", Toast.LENGTH_LONG).show();
                        firstName.getText().clear();
                    }
                }
            }
        });

        lastName.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(!hasFocus) {
                    lastNameText=lastName.getText().toString();

                    if(!isAllLetters(lastNameText)){
                        Toast.makeText(getApplicationContext(), "Error: Please enter a Valid Last Name", Toast.LENGTH_LONG).show();
                        lastName.getText().clear();
                    }
                }
            }
        });

        email.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(!hasFocus) {
                    emailText=email.getText().toString();

                    if(!(includesAt(emailText) && includesDot(emailText))){
                        Toast.makeText(getApplicationContext(), "Error: Please enter a Valid Email", Toast.LENGTH_LONG).show();
                        email.getText().clear();
                    }
                }
            }
        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener(){      //This kind of function is not really pertinent to a password
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(!hasFocus) {
                    passwordText=password.getText().toString();

                }
            }
        });

        address.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(!hasFocus) {
                    addressText=address.getText().toString();

                    if(!(containsLetters(addressText) && containsNumbers(addressText))){
                        Toast.makeText(getApplicationContext(), "Error: Please enter a Valid Address", Toast.LENGTH_LONG).show();
                        address.getText().clear();
                    }
                }
            }
        });

        creditCardNumber.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(!hasFocus) {
                    creditCardText=creditCardNumber.getText().toString();

                    if(!isAllNumbers(creditCardText) || creditCardText.length()!=16){
                        Toast.makeText(getApplicationContext(), "Error: Please enter a Valid 16 Digit Credit Card Number", Toast.LENGTH_LONG).show();
                        creditCardNumber.getText().clear();
                    }
                }
            }
        });

        expiration.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(!hasFocus) {
                    expirationText=expiration.getText().toString();

                    if(expirationText.length()!=5) {
                        Toast.makeText(getApplicationContext(), "Error: Please enter the Expiration Date in the MM/YY Format", Toast.LENGTH_LONG).show();
                        expiration.getText().clear();
                        return;
                    }
                    if(!(isDigit(expirationText.charAt(0)) && isDigit(expirationText.charAt(1)) && isDigit(expirationText.charAt(3)) && isDigit(expirationText.charAt(4)) && expirationText.charAt(2)=='/')){
                        Toast.makeText(getApplicationContext(), "Error: Please enter the Expiration Date in the MM/YY Format", Toast.LENGTH_LONG).show();
                        expiration.getText().clear();
                    }
                }
            }
        });

        CVV.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(!hasFocus) {
                    cvvText=CVV.getText().toString();

                    if(!isAllNumbers(cvvText) || cvvText.length()!=3){
                        Toast.makeText(getApplicationContext(), "Error: Please enter a Valid CVV", Toast.LENGTH_LONG).show();
                        CVV.getText().clear();
                    }
                }
            }
        });
    }


    public void openClientWelcome(View v){
        focus.requestFocus();
        createAccount(firstName.getText().toString(),
                lastName.getText().toString(),
                email.getText().toString(),
                password.getText().toString(),
                address.getText().toString(),
                creditCardNumber.getText().toString(),
                expiration.getText().toString(),
                CVV.getText().toString());
        if((firstNameText.length()*lastNameText.length()*emailText.length()*passwordText.length()*addressText.length()*creditCardText.length()*expirationText.length()*cvvText.length())==0) { //if there is a text box with a length of zero, the button will not function
            Toast.makeText(getApplicationContext(), "Missing Field(s)", Toast.LENGTH_LONG).show();
        }
        else{
            startActivity(new Intent(this, ClientWelcome.class));
        }
    }

    private boolean isAllLetters(String s){
        for(int i=0;i<s.length();i++){
            if(!isLetter(s.charAt(i))){
                return false;
            }
        }
        return true;
    }
    private boolean isAllNumbers(String s){
        for(int i=0;i<s.length();i++){
            if(!isDigit(s.charAt(i))){
                return false;
            }
        }
        return true;
    }
    private boolean containsLetters(String s){
        for(int i=0;i<s.length();i++){
            if(isLetter(s.charAt(i))){
                return true;
            }
        }
        return false;
    }
    private boolean containsNumbers(String s){
        for(int i=0;i<s.length();i++){
            if(isDigit(s.charAt(i))){
                return true;
            }
        }
        return false;
    }
    private boolean includesAt(String s){
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)=='@'){
                return true;
            }
        }
        return false;
    }
    private boolean includesDot(String s){
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)=='.'){
                return true;
            }
        }
        return false;
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

    /*public void openClientWelcome(View v){ //
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
    }*/

    private void reload() { }

    private void updateUI(FirebaseUser user) {

    }


}