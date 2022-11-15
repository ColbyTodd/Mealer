package com.uottawa.colbytodd.mealer;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterCook extends AppCompatActivity {

    //EditText firstName, lastName, email, password, address, focus;
    //String firstNameText="",lastNameText="",emailText="",passwordText="",addressText="";

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference mDocRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_cook);

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
    public void createAccount(String first, String last, String email, String password, String address) {
        // Create a new client
        mDocRef = db.document("cooks/" + email);
        Map<String, Object> user = new HashMap<String, Object>();


        user.put("first", first);
        user.put("last", last);
        user.put("email", email);
        user.put("password", password);
        user.put("address", address);

        mDocRef.set(user);

        Intent intent = new Intent(this, RegisterCook2.class);
        intent.putExtra("EMAIL", email);
        startActivity(intent);
    }
    public void openCookRegistration2(View v){
        EditText first = findViewById(R.id.firstName2);
        EditText last = findViewById(R.id.lastName2);
        EditText email = findViewById(R.id.email2);
        EditText password = findViewById(R.id.password2);
        EditText address = findViewById(R.id.address2);

        String sfirst = first.getText().toString();
        String slast =  last.getText().toString();
        String semail = email.getText().toString();
        String spassword = password.getText().toString();
        String saddress = address.getText().toString();

        TextView t = findViewById(R.id.RegistrationFeedback);

        //Input checking (Can be made more clean later)

        if(sfirst.length() < 1 || sfirst.length() > 25){
            t.setText("First name must be from 1 to 25 characters");
        }
        else if(slast.length() < 1 || slast.length() > 25){
            t.setText("Last name must be from 1 to 25 characters");
        }
        //For now assume valid email address is given and any password within 8 - 25 characters is valid
        else if(semail.length() < 1){
            t.setText("Please put a valid email");
        }
        else if(spassword.length() > 25 || spassword.length() < 8){
            t.setText("Password must be 8 to 25 characters");
        }
        //Assume address is valid if field is not blank
        else if(saddress.length() < 1){
            t.setText("Please put a valid address");
        }
        else{
            createAccount(sfirst, slast, semail, spassword, saddress);
        }
    }
/*
        focus = findViewById(R.id.focus2);
        firstName = findViewById(R.id.firstName2);
        lastName = findViewById(R.id.lastName2);
        email = findViewById(R.id.email2);
        password = findViewById(R.id.password2);
        address = findViewById(R.id.address2);

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
    }
    public void openCookRegistration2(View v){
        focus.requestFocus();
        if((firstNameText.length()*lastNameText.length()*emailText.length()*passwordText.length()*addressText.length())==0) { //if there is a text box with a length of zero, the button will not function
            Toast.makeText(getApplicationContext(), "Missing Field(s)", Toast.LENGTH_LONG).show();
        }
        else{
            mDocRef = db.document("cooks/" + emailText);
            Map<String, Object> user = new HashMap<String, Object>();

            user.put("first", firstNameText);
            user.put("last", lastNameText);
            user.put("email", emailText);
            user.put("password", passwordText);
            user.put("address", addressText);

            mDocRef.set(user);

            startActivity(new Intent(this, RegisterCook2.class));
        }
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
    private boolean isAllLetters(String s){
        for(int i=0;i<s.length();i++){
            if(!isLetter(s.charAt(i))){
                return false;
            }
        }
        return true;
    }*/
}