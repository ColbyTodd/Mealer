package com.uottawa.colbytodd.mealer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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
        ViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.menu_list_test, null);
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

