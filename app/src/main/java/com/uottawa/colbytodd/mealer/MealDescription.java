package com.uottawa.colbytodd.mealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MealDescription extends AppCompatActivity {
    Bundle extras;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    String email, address, card, cvv, expiration, first, last;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_meal_description);

        extras = getIntent().getExtras();


        TextView title = findViewById(R.id.mealTitle);
        TextView type = findViewById(R.id.mealTypeText);
        TextView description = findViewById(R.id.mealDesc);
        TextView ingredients = findViewById(R.id.mealIngredientsText);
        TextView price = findViewById(R.id.mealPriceText);
        TextView cuisineType = findViewById(R.id.mealCuisineTypeText);
        TextView allergens = findViewById(R.id.mealAllergenText);
        TextView cookEmailText = findViewById(R.id.cookEmailText);

        email = extras.getString("email");
        String mealName = extras.getString("Meal");
        String mealType = extras.getString("Type");
        String mealDescription = extras.getString("Description");
        String mealIngredients = extras.getString("Ingredients");
        String mealPrice = extras.getString("Price");
        String mealCuisineType = extras.getString("Cuisine Type");
        String mealAllergens = extras.getString("Allergens");
        String mealCookEmail = extras.getString("cookEmail");
        findViewById(R.id.mealDetailProceed).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MealDescription.this, MealCheckout.class);
                i.putExtra("email", email);
                i.putExtra("address", address);
                i.putExtra("card", card);
                i.putExtra("cvv", cvv);
                i.putExtra("expiration", expiration);
                i.putExtra("first", first);
                i.putExtra("last", last);
                i.putExtra("Meal", mealName);
                i.putExtra("Price", mealPrice);
                i.putExtra("cookEmail", mealCookEmail);
                startActivity(i);
            }
        });

        title.setText(mealName);
        type.setText(mealType);
        description.setText(mealDescription);
        ingredients.setText(mealIngredients);
        price.setText("$" + mealPrice);
        cuisineType.setText(mealCuisineType);
        allergens.setText(mealAllergens);
        cookEmailText.setText(mealCookEmail);

        db.collection("clients").document(email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot document = task.getResult();
                Map<String, Object> map = document.getData();
                for (Map.Entry<String, Object> entry : map.entrySet()){
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if (Objects.equals(key, "address")) address = (String) value;
                    else if (Objects.equals(key, "card")) card = (String) value;
                    else if (Objects.equals(key, "cvv")) cvv = (String) value;
                    else if (Objects.equals(key, "expiration")) expiration = (String) value;
                    else if (Objects.equals(key, "first")) first = (String) value;
                    else if (Objects.equals(key, "last")) last = (String) value;
                }
            }
        });

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
}