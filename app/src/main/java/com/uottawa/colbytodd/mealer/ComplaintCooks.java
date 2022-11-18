package com.uottawa.colbytodd.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
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
        ListView cooksList = findViewById(R.id.cooksList);

        //Querys the database for complaints
        db.collection("complaints")
                //.limit(10)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //adds cooks with complaints to a list
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                documents.add(document.getId());
                            }

                            if(documents.size() == 0){
                                documents.add("There are currently no cooks with complaints.");
                            }

                            //Shows the list of cooks with complaints on the app
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.cook_lists, documents);
                            cooksList.setAdapter(adapter);
                            cooksList.setOnItemClickListener(new OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                                    Intent i = new Intent(ComplaintCooks.this, ComplaintList.class);
                                    i.putExtra("email",documents.get(position));
                                    startActivity(i);
                                }
                            });
                        }
                    }
                });

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

    public void onSearchClick(View view){
        EditText cook = (EditText) findViewById(R.id.search);
        ListView cooksList = findViewById(R.id.cooksList);
        String top, bot = cook.getText().toString();
        int len = bot.length();
        //Creates query parameters
        String allButLast = bot.substring(0, len - 1);
        top = allButLast + (char) (bot.charAt(len - 1) + 1);


        db.collection("complaints")
                .whereGreaterThanOrEqualTo(FieldPath.documentId(), bot)
                .whereLessThan(FieldPath.documentId(), top)
                //.whereEqualTo(FieldPath.documentId(), "A@gmail.com")
                .limit(10)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            documents = new ArrayList<>();
                            //adds cooks with complaints to a list
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                documents.add(document.getId());
                            }

                            if (documents.size() == 0) {
                                documents.add("No results.");
                            }

                            //Shows the list of cooks with complaints on the app
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.cook_lists, documents);
                            cooksList.setAdapter(adapter);
                        }
                    }
                });
    }



}