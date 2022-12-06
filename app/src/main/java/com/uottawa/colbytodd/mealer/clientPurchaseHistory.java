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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class clientPurchaseHistory extends AppCompatActivity {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<PurchaseHistory> documents = new ArrayList<PurchaseHistory>();
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_purchase_history);

        Bundle extras = getIntent().getExtras();
        email = extras.getString("email");

        fillPurchaseHistory();

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

    private void fillPurchaseHistory(){
        ListView purchases = findViewById(R.id.purchaseList);

        //Querys the database for purchases
        db.collection("clients").document(email).collection("purchases")
                //.limit(10)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //adds cooks with complaints to a list
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                documents.add(new PurchaseHistory(document.getId(), (String) document.get("Status"), (String) document.get("Cook")));

                            }

                            //Shows the list of cooks with complaints on the app
                            //ArrayAdapter<String> adapter = new ArrayAdapter(getApplicationContext(), R.layout.menu_list, documents);
                            ArrayAdapter<menuList> adapter = new PurchaseHistoryAdapter(getApplicationContext(), documents);
                            purchases.setAdapter(adapter);
                            purchases.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                                    Intent i = new Intent(clientPurchaseHistory.this, FileComplaint.class);
                                    i.putExtra("Email", email);
                                    i.putExtra("Cook", documents.get(position).getCook());
                                    startActivity(i);
                                }
                            });
                        }
                    }
                });
    }
}
