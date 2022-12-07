package com.uottawa.colbytodd.mealer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ComplaintRate extends AppCompatActivity {
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
                                switch (which) {
                                    case 1:
                                        break;
                                    case 2:
                                        break;
                                    case 3:
                                        break;
                                    case 4:
                                        break;
                                    case 5:
                                        break;
                                }

                            }
                        });

        // 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
        AlertDialog dialog = builder.create();

        dialog.show();

    }

    public void onComplaintClick(View view){

        // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(ComplaintRate.this);

        // Add the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
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