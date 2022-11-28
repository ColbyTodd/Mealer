package com.uottawa.colbytodd.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    List<menuList> documents = new ArrayList<menuList>();
    int highlightedMenuItemPosition = -1;
    Button goToRemoveMeal;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);
         goToRemoveMeal = (Button) findViewById(R.id.removeMealButton);

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
            Intent intent = new Intent(this, CookWelcome.class);
            intent.putExtra("EMAIL", email);
            startActivity(intent);
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
                                documents.add(new menuList(document.getId(), (Boolean) document.get("isOffered"), email));

                            }

                            //Shows the list of cooks with complaints on the app
                            //ArrayAdapter<String> adapter = new ArrayAdapter(getApplicationContext(), R.layout.menu_list, documents);
                            ArrayAdapter<menuList> adapter = new menuListAdapter(getApplicationContext(), documents);
                            menuList.setAdapter(adapter);
                            menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                                    highlightedMenuItemPosition=position;
                                }
                            });
                            goToRemoveMeal.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (highlightedMenuItemPosition==-1){
                                        Toast.makeText(getApplicationContext(), "No Meal Selected.", Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        db.collection("cooks").document(email).collection("menu").document(adapter.getItem(highlightedMenuItemPosition).getDocumentId())
                                                .delete()
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(getApplicationContext(), adapter.getItem(highlightedMenuItemPosition).getDocumentId() + " was removed", Toast.LENGTH_LONG).show();
                                                        adapter.remove(adapter.getItem(highlightedMenuItemPosition));
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(getApplicationContext(), "Server Side error", Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                    }
                                }
                            });
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