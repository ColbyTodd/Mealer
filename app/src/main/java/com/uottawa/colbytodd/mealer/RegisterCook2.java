
package com.uottawa.colbytodd.mealer;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterCook2 extends AppCompatActivity {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference mDocRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_cook2);
    }
    public void openCookWelcome(View v){
        //EditText email = findViewById(R.id.email2);
        EditText description = findViewById(R.id.inputDescription);

        //String semail = email.getText().toString();
        String sdescription = description.getText().toString();

        TextView t = findViewById(R.id.RegistrationFeedback);

        //Input checking (Can be made more clean later)

        if(sdescription.length() < 1){
            t.setText("Profile Description Cannot be Empty");
        }
        else{
            createAccount(sdescription);//, semail);
        }
    }
    public void createAccount(String description){//, String email) {
        // Create a new client
        //mDocRef = db.document("cooks/" + email);
        Map<String, Object> user = new HashMap<String, Object>();


        user.put("description", description);

        //mDocRef.set(user);

        startActivity(new Intent(this, CookWelcome.class));
    }

}