package com.uottawa.colbytodd.mealer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class menuListAdapter extends ArrayAdapter {
    private LayoutInflater inflater;

    public menuListAdapter (Context context, List<menuList> data){
        super(context, 0, data);
        inflater = LayoutInflater.from(context);
    }

    /*
    @Override
    public long getItemId(int position)
    {
        //It is just an example
        menuList data = (menuList) getItem(position);
        return data.ID;
    }*/

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference mDocRef;

        ViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.menu_list, null);
            // Do some initialization

            //Retrieve the view on the item layout and set the value.
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }

        //Retrieve your object
        menuList data = (menuList) getItem(position);
        viewHolder.docId.setText(data.getDocumentId());
        viewHolder.chk.setChecked(data.getChecked());

        mDocRef = db.document("cooks/" + data.getEmail() + "/menu/" + data.getDocumentId());

        viewHolder.chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CompoundButton) view).isChecked()){
                    mDocRef.update("isOffered",true);
                    Toast.makeText(getContext(), "Meal \"" + data.getDocumentId()+ "\" Has Been Added to the Offered List", Toast.LENGTH_SHORT).show();
                } else {
                    mDocRef.update("isOffered",false);
                    Toast.makeText(getContext(), "Meal \"" + data.getDocumentId()+ "\" Has Been Removed from the Offered List", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;

    }

    private class ViewHolder
    {
        private final TextView docId;
        private final CheckBox chk;

        private ViewHolder(View view)
        {
            docId = (TextView) view.findViewById(R.id.docId);
            chk = (CheckBox) view.findViewById(R.id.chkbx);
        }
    }
}

