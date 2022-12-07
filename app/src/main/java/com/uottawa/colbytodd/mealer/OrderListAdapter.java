package com.uottawa.colbytodd.mealer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.uottawa.colbytodd.mealer.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderListAdapter extends ArrayAdapter<Order> {
    private static final String TAG = "OrderListAdapter";
    private Context mContext;
    private int mResource;

    public OrderListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Order> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String meal = getItem(position).getMeal();
        String clientEmail = getItem(position).getClientEmail();

        Order order = new Order(meal,clientEmail);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView mealNameText = (TextView) convertView.findViewById(R.id.mealNameText);
        TextView clientEmailText = (TextView) convertView.findViewById(R.id.clientEmailText);

        mealNameText.setText(meal);
        clientEmailText.setText(clientEmail);

        return convertView;
    }
}
