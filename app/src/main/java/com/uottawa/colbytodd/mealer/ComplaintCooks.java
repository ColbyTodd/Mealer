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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ComplaintCooks extends AppCompatActivity {
    //List<DocumentSnapshot> myListOfDocuments;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<String> documents = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);
        ListView cooksList = (ListView) findViewById(R.id.cooksList);

        //Querys the database for complaints
        db.collection("complaints")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int i = 0;
                            //adds cooks with complaints to a list
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                documents.add(document.getId());
                                i++;
                            }

                            if(documents.size() == 0){
                                documents.add("There are currently no cooks with complaints.");
                            }

                            //Shows the list of cooks with complaints on the app
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.cook_lists, documents);
                            cooksList.setAdapter(adapter);
                        }
                    }
                });

    }
}