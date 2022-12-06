package com.uottawa.colbytodd.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
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
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        extras = getIntent().getExtras();
        email = extras.getString("email");
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
                    List<String> cookEmail = new ArrayList<>();

                    documents = document.getDocuments();
                    for(DocumentSnapshot s : documents){
                        Map<String, Object> map = s.getData();
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if(entry.getKey().equals("Allergens")){
                                allergens.add(entry.getValue().toString());
                            }
                            else if(entry.getKey().equals("Cuisine Type")) {
                                cuisineType.add(entry.getValue().toString());
                            }
                            else if(entry.getKey().equals("Description")) {
                                description.add(entry.getValue().toString());
                            }
                            else if(entry.getKey().equals("Ingredients")) {
                                ingredients.add(entry.getValue().toString());
                            }
                            else if(entry.getKey().equals("Meal Type")) {
                                mealType.add(entry.getValue().toString());
                            }
                            else if(entry.getKey().equals("Name")){
                                name.add(entry.getValue().toString());
                            }
                            else if(entry.getKey().equals("Price")) {
                                price.add(entry.getValue().toString());
                            }
                            else if(entry.getKey().equals("isOffered")){
                                isOffered.add(entry.getValue().toString());
                            }
                            else if(entry.getKey().equals("CookEmail")){
                                cookEmail.add(entry.getValue().toString());
                            }
                        }
                    }
                    int i=0;
                    //for(int i=0;i<allergens.size();i++){
                    while(i<allergens.size()){
                        if(isOffered.get(i).equals("true")) {
                            Meal meal = new Meal(name.get(i), mealType.get(i), cuisineType.get(i), price.get(i));
                            mealList.add(meal);
                            i++;
                        }
                        else{
                            allergens.remove(i);
                            cuisineType.remove(i);
                            description.remove(i);
                            ingredients.remove(i);
                            mealType.remove(i);
                            name.remove(i);
                            price.remove(i);
                            isOffered.remove(i);
                            cookEmail.remove(i);
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
                            i.putExtra("cookEmail", cookEmail.get(position));
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

    public void onNameSearchClick(View view){
        EditText meal = (EditText) findViewById(R.id.mealSearch);
        ArrayList<Meal> mealList = new ArrayList<>();
        ListView mealView = (ListView) findViewById(R.id.mealList);
        if(meal.getText().length() == 0){
            return;
        }
        String top, bot = meal.getText().toString();
        int len = bot.length();

        //Creates query parameters
        String allButLast = bot.substring(0, len - 1);
        top = allButLast + (char) (bot.charAt(len - 1) + 1);

        db.collectionGroup("menu")
                .whereGreaterThanOrEqualTo("Name", bot)
                .whereLessThan("Name", top)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                            List<String> cookEmail = new ArrayList<>();

                            documents = document.getDocuments();
                            for (DocumentSnapshot s : documents) {
                                Map<String, Object> map = s.getData();
                                for (Map.Entry<String, Object> entry : map.entrySet()) {
                                    if (entry.getKey().equals("Allergens")) {
                                        allergens.add(entry.getValue().toString());
                                    } else if (entry.getKey().equals("Cuisine Type")) {
                                        cuisineType.add(entry.getValue().toString());
                                    } else if (entry.getKey().equals("Description")) {
                                        description.add(entry.getValue().toString());
                                    } else if (entry.getKey().equals("Ingredients")) {
                                        ingredients.add(entry.getValue().toString());
                                    } else if (entry.getKey().equals("Meal Type")) {
                                        mealType.add(entry.getValue().toString());
                                    } else if (entry.getKey().equals("Name")) {
                                        name.add(entry.getValue().toString());
                                    } else if (entry.getKey().equals("Price")) {
                                        price.add(entry.getValue().toString());
                                    } else if (entry.getKey().equals("isOffered")) {
                                        isOffered.add(entry.getValue().toString());
                                    } else if (entry.getKey().equals("CookEmail")) {
                                        cookEmail.add(entry.getValue().toString());
                                    }
                                }
                            }
                            int i = 0;
                            //for(int i=0;i<allergens.size();i++){
                            while (i < allergens.size()) {
                                if (isOffered.get(i).equals("true")) {
                                    Meal meal = new Meal(name.get(i), mealType.get(i), cuisineType.get(i), price.get(i));
                                    mealList.add(meal);
                                    i++;
                                } else {
                                    allergens.remove(i);
                                    cuisineType.remove(i);
                                    description.remove(i);
                                    ingredients.remove(i);
                                    mealType.remove(i);
                                    name.remove(i);
                                    price.remove(i);
                                    isOffered.remove(i);
                                    cookEmail.remove(i);
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
                                    i.putExtra("cookEmail", cookEmail.get(position));
                                    startActivity(i);
                                }
                            });
                        }
                    }
                });
    }

    public void onMealSearchClick(View view){
        EditText meal = (EditText) findViewById(R.id.mealSearch);
        ArrayList<Meal> mealList = new ArrayList<>();
        ListView mealView = (ListView) findViewById(R.id.mealList);
        if(meal.getText().length() == 0){
            return;
        }
        String top, bot = meal.getText().toString();
        int len = bot.length();

        //Creates query parameters
        String allButLast = bot.substring(0, len - 1);
        top = allButLast + (char) (bot.charAt(len - 1) + 1);

        db.collectionGroup("menu")
                .whereGreaterThanOrEqualTo("Meal Type", bot)
                .whereLessThan("Meal Type", top)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                            List<String> cookEmail = new ArrayList<>();

                            documents = document.getDocuments();
                            for (DocumentSnapshot s : documents) {
                                Map<String, Object> map = s.getData();
                                for (Map.Entry<String, Object> entry : map.entrySet()) {
                                    if (entry.getKey().equals("Allergens")) {
                                        allergens.add(entry.getValue().toString());
                                    } else if (entry.getKey().equals("Cuisine Type")) {
                                        cuisineType.add(entry.getValue().toString());
                                    } else if (entry.getKey().equals("Description")) {
                                        description.add(entry.getValue().toString());
                                    } else if (entry.getKey().equals("Ingredients")) {
                                        ingredients.add(entry.getValue().toString());
                                    } else if (entry.getKey().equals("Meal Type")) {
                                        mealType.add(entry.getValue().toString());
                                    } else if (entry.getKey().equals("Name")) {
                                        name.add(entry.getValue().toString());
                                    } else if (entry.getKey().equals("Price")) {
                                        price.add(entry.getValue().toString());
                                    } else if (entry.getKey().equals("isOffered")) {
                                        isOffered.add(entry.getValue().toString());
                                    } else if (entry.getKey().equals("CookEmail")) {
                                        cookEmail.add(entry.getValue().toString());
                                    }
                                }
                            }
                            int i = 0;
                            //for(int i=0;i<allergens.size();i++){
                            while (i < allergens.size()) {
                                if (isOffered.get(i).equals("true")) {
                                    Meal meal = new Meal(name.get(i), mealType.get(i), cuisineType.get(i), price.get(i));
                                    mealList.add(meal);
                                    i++;
                                } else {
                                    allergens.remove(i);
                                    cuisineType.remove(i);
                                    description.remove(i);
                                    ingredients.remove(i);
                                    mealType.remove(i);
                                    name.remove(i);
                                    price.remove(i);
                                    isOffered.remove(i);
                                    cookEmail.remove(i);
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
                                    i.putExtra("cookEmail", cookEmail.get(position));
                                    startActivity(i);
                                }
                            });
                        }
                    }
                });
    }

    public void onCuisineSearchClick(View view){
        EditText meal = (EditText) findViewById(R.id.mealSearch);
        ArrayList<Meal> mealList = new ArrayList<>();
        ListView mealView = (ListView) findViewById(R.id.mealList);
        if(meal.getText().length() == 0){
            return;
        }
        String top, bot = meal.getText().toString();
        int len = bot.length();

        //Creates query parameters
        String allButLast = bot.substring(0, len - 1);
        top = allButLast + (char) (bot.charAt(len - 1) + 1);

        db.collectionGroup("menu")
                .whereGreaterThanOrEqualTo("Cuisine Type", bot)
                .whereLessThan("Cuisine Type", top)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                            List<String> cookEmail = new ArrayList<>();

                            documents = document.getDocuments();
                            for (DocumentSnapshot s : documents) {
                                Map<String, Object> map = s.getData();
                                for (Map.Entry<String, Object> entry : map.entrySet()) {
                                    if (entry.getKey().equals("Allergens")) {
                                        allergens.add(entry.getValue().toString());
                                    } else if (entry.getKey().equals("Cuisine Type")) {
                                        cuisineType.add(entry.getValue().toString());
                                    } else if (entry.getKey().equals("Description")) {
                                        description.add(entry.getValue().toString());
                                    } else if (entry.getKey().equals("Ingredients")) {
                                        ingredients.add(entry.getValue().toString());
                                    } else if (entry.getKey().equals("Meal Type")) {
                                        mealType.add(entry.getValue().toString());
                                    } else if (entry.getKey().equals("Name")) {
                                        name.add(entry.getValue().toString());
                                    } else if (entry.getKey().equals("Price")) {
                                        price.add(entry.getValue().toString());
                                    } else if (entry.getKey().equals("isOffered")) {
                                        isOffered.add(entry.getValue().toString());
                                    } else if (entry.getKey().equals("CookEmail")) {
                                        cookEmail.add(entry.getValue().toString());
                                    }
                                }
                            }
                            int i = 0;
                            //for(int i=0;i<allergens.size();i++){
                            while (i < allergens.size()) {
                                if (isOffered.get(i).equals("true")) {
                                    Meal meal = new Meal(name.get(i), mealType.get(i), cuisineType.get(i), price.get(i));
                                    mealList.add(meal);
                                    i++;
                                } else {
                                    allergens.remove(i);
                                    cuisineType.remove(i);
                                    description.remove(i);
                                    ingredients.remove(i);
                                    mealType.remove(i);
                                    name.remove(i);
                                    price.remove(i);
                                    isOffered.remove(i);
                                    cookEmail.remove(i);
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
                                    i.putExtra("cookEmail", cookEmail.get(position));
                                    startActivity(i);
                                }
                            });
                        }
                    }
                });
    }

}