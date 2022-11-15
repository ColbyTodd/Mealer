package com.uottawa.colbytodd.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class ComplaintList extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    List<String> documents = new ArrayList<String>();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    int highlightedPosition = -1;
    Bundle extras;
    TextView dateText;
    ListView complaintList;
    int hour, minute, year, month, dayOfMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_list);
        extras = getIntent().getExtras();
        complaintList = (ListView) findViewById(R.id.complaintList);
        Button deleteComplaint = (Button) findViewById(R.id.deleteComplaint);
        extras = getIntent().getExtras();
        dateText = findViewById(R.id.suspensionDate);
        complaintList = (ListView) findViewById(R.id.complaintList);
        findViewById(R.id.suspend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        if (extras != null) {
            String email = extras.getString("email");
            db.collection("complaints").document(email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            List<String> complaints = new ArrayList<>();

                            Map<String, Object> map = document.getData();
                            if (map != null) {
                                for (Map.Entry<String, Object> entry : map.entrySet()) {
                                    complaints.add(entry.getValue().toString());
                                }
                            }
                            for (String s : complaints) {
                                documents.add(s);
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.cook_lists, documents);
                            complaintList.setAdapter(adapter);
                            complaintList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                                    highlightedPosition=position;
                                }
                            });
                            deleteComplaint.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (highlightedPosition==-1){
                                        Toast.makeText(getApplicationContext(), "No Complaint Selected.", Toast.LENGTH_LONG).show();;
                                    }
                                    else{
                                        adapter.remove(adapter.getItem(highlightedPosition));
                                    }
                                }
                            });

                        }
                    }
                }
            });

        }
        else {
            startActivity(new Intent(ComplaintList.this, ComplaintCooks.class));
        }

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

    public void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
    public void popTimePicker() {
        // int style = AlertDialog.THEME_HOLO_DARK;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, this, hour, minute, true);
        timePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
        year = selectedYear;
        month = selectedMonth;
        dayOfMonth = selectedDayOfMonth;
        popTimePicker();
    }
    @Override
    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
        hour = selectedHour;
        minute = selectedMinute;
        suspendCook();
    }

    public void suspendCook() {
        Calendar newDate = Calendar.getInstance();
        newDate.set(Calendar.YEAR, year);
        newDate.set(Calendar.MONTH, month);
        newDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        newDate.set(Calendar.HOUR_OF_DAY, hour);
        newDate.set(Calendar.MINUTE, minute);
        newDate.set(Calendar.SECOND, 0);
        Date calDate = newDate.getTime();
        Timestamp dateTimestamp = new Timestamp(calDate);

        String email = extras.getString("email");
        Map<String, Object> suspension = new HashMap<>();
        suspension.put("suspension", dateTimestamp);
        db.collection("cooks").document(email)
                .set(suspension, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dateText.setText(email + " account has been successfully suspended until " + calDate);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dateText.setText("Error suspending cook");
                    }
                });
    }
}

