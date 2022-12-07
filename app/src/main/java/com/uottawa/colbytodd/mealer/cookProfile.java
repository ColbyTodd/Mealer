package com.uottawa.colbytodd.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class cookProfile extends AppCompatActivity {
    Bundle extras;
    String email;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView fullName = findViewById(R.id.textView14);
        TextView address = findViewById(R.id.textView15);
        TextView emailText = findViewById(R.id.emailTexts);
        TextView rating = (TextView) findViewById(R.id.textView19);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_profile);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        extras = getIntent().getExtras();
        email = extras.getString("EMAIL");

        DocumentReference docRef = db.collection("cooks").document(email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        TextView fullName = findViewById(R.id.textView14);
                        TextView address = findViewById(R.id.textView15);
                        TextView emailText = findViewById(R.id.emailTexts);
                        emailText.setText(email);
                        fullName.setText(document.getData().get("first").toString()+" "+document.getData().get("last").toString());
                        address.setText(document.getData().get("address").toString());
                        //rating.setText(document.getData().get("rating"));  FIX THIS ONCE RATING IS IMPLEMENTED
                        Log.d("PROFILE", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("PROFILE", "No such document");
                    }
                } else {
                    Log.d("PROFILE", "get failed with ", task.getException());
                }
            }
        });

        CollectionReference docRef2 = db.collection("cooks").document(email).collection("ratings");
        docRef2.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    long rating = 0, count = 0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.exists()) {

                            rating = rating + Long.parseLong(document.get("rating").toString());
                            count++;
                            //rating.setText(document.getData().get("rating"));  FIX THIS ONCE RATING IS IMPLEMENTED
                            Log.d("PROFILE", "DocumentSnapshot data: " + document.getData());
                        } else {
                            Log.d("PROFILE", "No such document");
                        }
                    }

                    rating = rating * 100/count;
                    TextView rate = (TextView) findViewById(R.id.textView19);
                    rate.setText( (rating/100.0) + "");

                }else {
                    Log.d("PROFILE", "get failed with ", task.getException());
                }
            }
        });
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
    public void onReturnClick(View v){
        Intent intent = new Intent(this, CookWelcome.class);
        intent.putExtra("EMAIL", email);
        startActivity(intent);
    }
}