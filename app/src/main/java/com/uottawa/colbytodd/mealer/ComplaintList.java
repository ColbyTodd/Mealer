package com.uottawa.colbytodd.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ComplaintList extends AppCompatActivity {
    List<String> documents = new ArrayList<String>();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_list);
        Bundle extras = getIntent().getExtras();
        ListView complaintList = (ListView) findViewById(R.id.complaintList);

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
                        }
                    }
                }
            });

        }
        else {
            startActivity(new Intent(ComplaintList.this, ComplaintCooks.class));
        }
    }
}