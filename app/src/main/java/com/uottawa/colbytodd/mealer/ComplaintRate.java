package com.uottawa.colbytodd.mealer;

import androidx.appcompat.app.AppCompatActivity;

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

        /*Intent i = new Intent(ComplaintRate.this, RateCook.class);
        i.putExtra("Email", email);
        i.putExtra("Cook", cook);
        startActivity(i);
        */
    }

    public void onComplaintClick(View view){

        Intent i = new Intent(ComplaintRate.this, FileComplaint.class);
        i.putExtra("Email", email);
        i.putExtra("Cook", cook);
        startActivity(i);

    }
}