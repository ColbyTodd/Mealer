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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class menuListActivity extends AppCompatActivity {
    //List<DocumentSnapshot> myListOfDocuments;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<String> documents = new ArrayList<String>();
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);

        ListView menuList = findViewById(R.id.menuList);

        Intent intent = getIntent();
        email = intent.getStringExtra("EMAIL");

    //Querys the database for meals
        // Something from this point to the end of the onComplete is stopping this page from being loaded twice -Geoff
        db.collection("cooks").document(email).collection("menu")
                .limit(10)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
                //adds cooks with complaints to a list
                for (QueryDocumentSnapshot document : task.getResult()) {
                    documents.add(document.getId());
                }
                /*
                if(documents.size() == 0){
                    documents.add("There are currently no meals added");
                }*/
                //Shows the list of cooks with complaints on the app
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.menu_list, documents);
                menuList.setAdapter(adapter);
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
    public void goToAddMeal(View view){
        Intent intent = new Intent(this, AddMeal.class);
        intent.putExtra("EMAIL", email);
        startActivity(intent);
    }
}