package com.uottawa.colbytodd.mealer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class ComplaintRate extends AppCompatActivity {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    String email, cook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_rate);

        Bundle extras = getIntent().getExtras();
        email = extras.getString("Email");
        cook = extras.getString("Cook");

        TextView title = (TextView) findViewById(R.id.ComplaintRateTitleText);
        title.setText("Complain or rate about " + cook);

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

    public void onRateClick(View view){

        // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(ComplaintRate.this);

        // Add the buttons
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setTitle("Rating " + cook)
                .setItems(new String[]{"1", "2", "3", "4", "5"}, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item;
                                DocumentReference mDocRef = db.document("cooks/" + cook + "/ratings/" + email);
                                Map<String, Object> rating = new HashMap<String, Object>();
                                rating.put("rating", which + 1);
                                mDocRef.set(rating);
                            }
                        });
        // 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
        AlertDialog dialog = builder.create();

        dialog.show();

    }

    public void onComplaintClick(View view){

        // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(ComplaintRate.this);

        // Get the layout inflater
        LayoutInflater inflater = ComplaintRate.this.getLayoutInflater();
        View promptUserView = inflater.inflate(R.layout.complaint_dialog, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(promptUserView);

        final EditText description = (EditText) promptUserView.findViewById(R.id.complaintText);

        // Add the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button

                DocumentReference mDocRef = db.document("complaints/" + cook);
                Map<String, Object> complaint = new HashMap<String, Object>();
                complaint.put(email, description.getText().toString());
                mDocRef.set(complaint);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setTitle("Complaining about " + cook);

        // 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
        AlertDialog dialog = builder.create();

        dialog.show();

    }
}