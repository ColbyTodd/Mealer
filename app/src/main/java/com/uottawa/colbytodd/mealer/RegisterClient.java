package com.uottawa.colbytodd.mealer;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RegisterClient extends AppCompatActivity {
    private static final String TAG = "RegisterClient";

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference mDocRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_client);

    }

    private void createAccount(String first, String last, String email, String password, String address, String card, String expiration, String cvv) {

        //if(clientParse(v, first, last, email, password, address, card, expiration, cvv)){
        // Create a new client
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

            startActivity(new Intent(this, ClientWelcome.class));
        //}


    }

    public void openClientWelcome(View v){
        EditText first = (EditText) findViewById(R.id.firstName);
        EditText last = (EditText) findViewById(R.id.lastName);
        EditText email = (EditText) findViewById(R.id.email);
        EditText password = (EditText) findViewById(R.id.password);
        EditText address = (EditText) findViewById(R.id.address);
        EditText card = (EditText) findViewById(R.id.creditCardNumber);
        EditText expiration = (EditText) findViewById(R.id.expiration);
        EditText cvv = findViewById(R.id.cvv);

        String sfirst = first.getText().toString();
        String slast =  last.getText().toString();
        String semail = email.getText().toString();
        String spassword = password.getText().toString();
        String saddress = address.getText().toString();
        String scard = card.getText().toString();
        String sexpiration = expiration.getText().toString();
        String scvv = cvv.getText().toString();

        TextView t = findViewById(R.id.clientRegistrationFeedback);

        boolean cardflag = false;
        boolean expirationflag = false;
        boolean scvvflag = false;

        //Input checking (Can be made more clean later)
        //remove whitespace
        semail.replaceAll(" ", "");
        scard.replaceAll(" ", "");

        //Check credit card is all integers
        for(char i:scard.toCharArray()){
            if(i < '0' || i > '9'){
                cardflag = true;
            }
        }

        //Check valid expiration date
        for(char i:sexpiration.toCharArray()){
            Date d = new Date();
            int currentYear = (d.getYear() + 1900) % 100;
            int year = 0, month = 0;
            boolean swap = false;
            if(i < '0' || i > '9' || i != '/'){
                expirationflag = true;
            }
            else if(i == '/'){
                swap = true;
            }
            else if(!swap){
                month = month*10 + i - '0';
            }
            else if(swap){
                year = year*10 + i - '0';
            }

            if(year < currentYear){
                expirationflag = true;
            }
            else if(year == currentYear && month < d.getMonth() + 1){
                expirationflag = true;
            }
        }

        //Check valid cvv
        for(char i:scvv.toCharArray()){
            if(i < '0' || i > '9'){
                scvvflag = true;
            }
        }

        if(sfirst == null || sfirst.length() > 25){
            t.setText("First name must be from 1 to 25 characters");
        }
        else if(slast == null || slast.length() > 25){
            t.setText("Last name must be from 1 to 25 characters");
        }
        //For now assume valid email address is given and any password within 8 - 25 characters is valid
        else if(semail == null){
            t.setText("Please put a valid email");
        }
        else if(spassword == null || spassword.length() > 25 || spassword.length() < 8){
            t.setText("Password must be 8 to 25 characters");
        }
        //Assume address is valid if field is not blank
        else if(saddress == null){
            t.setText("Please put a valid address");
        }
        else if(scard == null || cardflag || scard.length() != 16){
            t.setText("Please put a valid credit card");
        }
        else if(sexpiration == null || expirationflag || sexpiration.length() != 5){
            t.setText("Please put a valid expiration date");
        }
        else if(scvv == null || scvv.length() != 3 || scvvflag){
            t.setText("Please enter a valid cvv");
        }
        else{
            createAccount(sfirst, slast, semail, spassword, saddress, scard, sexpiration, scvv);
        }

    }

}

