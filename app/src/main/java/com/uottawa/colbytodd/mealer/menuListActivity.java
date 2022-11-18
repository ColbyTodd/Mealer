package com.uottawa.colbytodd.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class menuListActivity extends AppCompatActivity {
    //List<DocumentSnapshot> myListOfDocuments;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<String> documents = new ArrayList<String>();
    List<Boolean> isChecked = new ArrayList<Boolean>();
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);

        Intent intent = getIntent();
        email = intent.getStringExtra("EMAIL");

        //Add back button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fillMenuList();
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

    private void fillMenuList(){
        ListView menuList = findViewById(R.id.menuList);

        //Querys the database for meals
        db.collection("cooks").document(email).collection("menu")
                //.limit(10)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //adds cooks with complaints to a list
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                documents.add(document.getId());

                                /*
                                db.collection("cooks")
                                        .document(email)
                                        .collection("menu")
                                        .document(document.getId())
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    isChecked.add((Boolean) task.getResult().get("isOffered"));
                                                }
                                            }

                                        });*/
                            }

                            //Shows the list of cooks with complaints on the app
                            ArrayAdapter<String> adapter = new ArrayAdapter(getApplicationContext(), R.layout.menu_list, documents);
                            menuList.setAdapter(adapter);
                        }
                    }
                });
    }
    public void goToAddMeal(View view){
        Intent intent = new Intent(this, AddMeal.class);
        intent.putExtra("EMAIL", email);
        startActivity(intent);
    }
}