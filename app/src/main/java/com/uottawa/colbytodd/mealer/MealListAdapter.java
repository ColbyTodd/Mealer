package com.uottawa.colbytodd.mealer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MealListAdapter extends ArrayAdapter<Meal> {
    private static final String TAG = "MealListAdapter";
    private Context mContext;
    int mResource;

    public MealListAdapter(Context context, int resource, ArrayList<Meal> objects){
        super(context,resource,objects);
        mContext=context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getMealName();
        String mealType = getItem(position).getMealType();
        String cuisineType = getItem(position).getCuisineType();
        String price = getItem(position).getPrice();

        Meal meal = new Meal(name,mealType,cuisineType, price);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView mealName = (TextView) convertView.findViewById(R.id.textView1);
        TextView mType = (TextView) convertView.findViewById(R.id.textView3);
        TextView cType = (TextView) convertView.findViewById(R.id.textView2);
        TextView mealPrice = (TextView) convertView.findViewById(R.id.textView9);

        mealName.setText(name);
        mType.setText(mealType);
        cType.setText(cuisineType);
        mealPrice.setText("$"+price);

        return convertView;
    }
}
