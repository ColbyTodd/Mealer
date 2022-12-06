package com.uottawa.colbytodd.mealer;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class PurchaseHistoryAdapter extends ArrayAdapter {

    private LayoutInflater inflater;
    private Context mContext;
    int mResource;

    public PurchaseHistoryAdapter(Context context, List<PurchaseHistory> data){
        super(context,0,data);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference mDocRef;

        PurchaseHistoryAdapter.ViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.purchase_history_list, null);
            // Do some initialization

            //Retrieve the view on the item layout and set the value.
            viewHolder = new PurchaseHistoryAdapter.ViewHolder(view);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (PurchaseHistoryAdapter.ViewHolder) view.getTag();
        }

        //Retrieve your object
        PurchaseHistory data = (PurchaseHistory) getItem(position);
        viewHolder.meal.setText(data.getDocumentID());
        viewHolder.status.setText(data.getStatus());

        return view;

    }

    private class ViewHolder
    {
        private final TextView meal;
        private final TextView status;

        private ViewHolder(View view)
        {
            meal = (TextView) view.findViewById(R.id.MealName);
            status = (TextView) view.findViewById(R.id.Status);
        }
    }
}
