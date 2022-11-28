package com.uottawa.colbytodd.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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

    private static final String TAG = "clientMealList";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<DocumentSnapshot> documents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                        Log.d("TEST",map.values().toString());
                        int i = 0;
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if(i==1){
                                allergens.add(entry.getValue().toString());
                            }
                            else if(i==5) {
                                cuisineType.add(entry.getValue().toString());
                            }
                            else if(i==2) {
                                description.add(entry.getValue().toString());
                            }
                            else if(i==4) {
                                ingredients.add(entry.getValue().toString());
                            }
                            else if(i==6) {
                                mealType.add(entry.getValue().toString());
                            }
                            else if(i==7) {
                                name.add(entry.getValue().toString());
                            }
                            else if(i==3) {
                                price.add(entry.getValue().toString());
                            }
                            else if(i==0){
                                isOffered.add(entry.getValue().toString());
                            }
                            i++;
                        }
                    }
                    for(int i=0;i<allergens.size();i++){
                        Meal meal = new Meal(name.get(i),mealType.get(i),cuisineType.get(i),price.get(i));
                        mealList.add(meal);
                    }
                    MealListAdapter adapter = new MealListAdapter(getApplicationContext(), R.layout.client_meal_adapter_layout, mealList);
                    mealView.setAdapter(adapter);
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