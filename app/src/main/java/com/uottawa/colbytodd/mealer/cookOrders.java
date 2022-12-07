package com.uottawa.colbytodd.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class cookOrders extends AppCompatActivity {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    Bundle extras;
    int highlightedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_orders);
        ListView orderListView = (ListView) findViewById(R.id.ordersList);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button rejectOrder = (Button) findViewById(R.id.rejectOrder);
        Button confirmOrder = (Button) findViewById(R.id.confirmOrder);

        extras = getIntent().getExtras();
        String email = extras.getString("EMAIL");
        db.collection("cooks").document(email).collection("orders").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> clientEmail = new ArrayList<>();
                    List<String> meal = new ArrayList<>();
                    ArrayList<Order> orderList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        meal.add(document.getData().get("Meal").toString());
                        clientEmail.add(document.getData().get("Client Email").toString());
                    }
                    for (int i = 0; i < clientEmail.size(); i++) {
                        Order newOrder = new Order(meal.get(i), clientEmail.get(i));
                        orderList.add(newOrder);
                    }
                    OrderListAdapter adapter = new OrderListAdapter(getApplicationContext(), R.layout.cook_order_adapter_layout, orderList);
                    orderListView.setAdapter(adapter);

                    orderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                            highlightedPosition = position;
                        }
                    });
                    rejectOrder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (highlightedPosition == -1) {
                                Toast.makeText(getApplicationContext(), "No Order Selected", Toast.LENGTH_LONG).show();
                            } else {
                                adapter.remove(adapter.getItem(highlightedPosition));
                                db.collection("clients").document(clientEmail.get(highlightedPosition)).
                                        collection("purchases").document(meal.get(highlightedPosition)).update("Status", "Rejected");
                                db.collection("cooks").document(email).
                                        collection("orders").document(meal.get(highlightedPosition)).delete();
                                clientEmail.remove(highlightedPosition);
                                meal.remove(highlightedPosition);
                                //highlightedPosition = -1;
                                Toast.makeText(getApplicationContext(), "Order Rejected", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    confirmOrder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (highlightedPosition == -1) {
                                Toast.makeText(getApplicationContext(), "No Order Selected", Toast.LENGTH_LONG).show();
                            } else {
                                adapter.remove(adapter.getItem(highlightedPosition));
                                db.collection("clients").document(clientEmail.get(highlightedPosition)).
                                        collection("purchases").document(meal.get(highlightedPosition)).update("Status", "Confirmed");
                                db.collection("cooks").document(email).
                                        collection("orders").document(meal.get(highlightedPosition)).delete();
                                clientEmail.remove(highlightedPosition);
                                meal.remove(highlightedPosition);
                                //highlightedPosition = -1;
                                Toast.makeText(getApplicationContext(), "Order Confirmed!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                } else {
                    Log.d("ORDERS", "Error getting documents: ", task.getException());
                }
            }
        });
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
}