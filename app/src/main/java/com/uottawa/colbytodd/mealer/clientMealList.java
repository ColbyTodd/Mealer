package com.uottawa.colbytodd.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class clientMealList extends AppCompatActivity {

    Bundle extras;
    private static final String TAG = "clientMealList";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<DocumentSnapshot> documents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        extras = getIntent().getExtras();
        String email = extras.getString("email");
        setContentView(R.layout.activity_client_meal_list);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView mealView = (ListView) findViewById(R.id.mealList);
        ArrayList<Meal> mealList = new ArrayList<>();

        db.collectionGroup("menu").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot document = task.getResult();

                    List<String> allergens = new ArrayList<>();
                    List<String> cuisineType = new ArrayList<>();
                    List<String> description = new ArrayList<>();
                    List<String> ingredients = new ArrayList<>();
                    List<String> mealType = new ArrayList<>();
                    List<String> name = new ArrayList<>();
                    List<String> price = new ArrayList<>();
                    List<String> isOffered = new ArrayList<>();

                    documents = document.getDocuments();
                    for(DocumentSnapshot s : documents){
                        Map<String, Object> map = s.getData();
                        int i = 0;
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if(entry.getKey().equals("Allergens")){
                                allergens.add(entry.getValue().toString());
                                Log.d("TEST","1");
                            }
                            else if(entry.getKey().equals("Cuisine Type")) {
                                cuisineType.add(entry.getValue().toString());
                                Log.d("TEST","2");
                            }
                            else if(entry.getKey().equals("Description")) {
                                description.add(entry.getValue().toString());
                                Log.d("TEST","3");
                            }
                            else if(entry.getKey().equals("Ingredients")) {
                                ingredients.add(entry.getValue().toString());
                                Log.d("TEST","4");
                            }
                            else if(entry.getKey().equals("Meal Type")) {
                                mealType.add(entry.getValue().toString());
                                Log.d("TEST","5");
                            }
                            else if(entry.getKey().equals("Name")){
                                name.add(entry.getValue().toString());
                                Log.d("TEST","6");
                            }
                            else if(entry.getKey().equals("Price")) {
                                price.add(entry.getValue().toString());
                                Log.d("TEST","7");
                            }
                            else if(entry.getKey().equals("isOffered")){
                                isOffered.add(entry.getValue().toString());
                                Log.d("TEST","8");
                            }
                            i++;
                        }
                    }
                    for(int i=0;i<allergens.size();i++){
                        if(isOffered.get(i)=="true") {
                            Meal meal = new Meal(name.get(i), mealType.get(i), cuisineType.get(i), price.get(i));
                            mealList.add(meal);
                        }
                    }
                    MealListAdapter adapter = new MealListAdapter(getApplicationContext(), R.layout.client_meal_adapter_layout, mealList);
                    mealView.setAdapter(adapter);
                    mealView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            Intent i = new Intent(clientMealList.this, MealDescription.class);
                            i.putExtra("Meal", name.get(position));
                            i.putExtra("Type", mealType.get(position));
                            i.putExtra("Description", description.get(position));
                            i.putExtra("Ingredients", ingredients.get(position));
                            i.putExtra("Price", price.get(position));
                            i.putExtra("Cuisine Type", cuisineType.get(position));
                            i.putExtra("Allergens", allergens.get(position));
                            i.putExtra("email", email);
                            startActivity(i);
                        }
                    });
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