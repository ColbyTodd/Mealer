package com.uottawa.colbytodd.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class ComplaintCooks extends AppCompatActivity {
    List<DocumentSnapshot> myListOfDocuments;
    String[] documents = new String[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);

        FirebaseFirestore.getInstance()
                .collection("complaints")
                .limit(10)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            myListOfDocuments = task.getResult().getDocuments();
                            int i = 0;
                            for(DocumentSnapshot document: myListOfDocuments){
                                documents[i] = document.get("Document ID").toString();
                            }
                        }
                    }
                });

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_complaints, documents);
        ListView cooksList = (ListView) findViewById(R.id.cooksList);
        cooksList.setAdapter(adapter);
    }
}