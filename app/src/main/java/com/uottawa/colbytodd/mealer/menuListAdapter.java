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
    String[] documents;
    Boolean[] isChecked;
    private Context context;

    public menuListAdapter(Context context, List<String> documents, List<Boolean> checked) {
        super(context, R.layout.menu_list, documents);

        this.documents = new String[documents.size()];
        this.documents = documents.toArray(this.documents);
        this.isChecked = new Boolean[checked.size()];
        this.isChecked = checked.toArray(this.isChecked);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater siteInflater = LayoutInflater.from(getContext());
        View customView = siteInflater.inflate(R.layout.menu_list, parent, false);

        TextView observationTV = (TextView) customView.findViewById(R.id.obs);
        CheckBox checkCB = (CheckBox) customView.findViewById(R.id.chkbx);

        checkCB.setTag(Integer.valueOf(position));

        observationTV.setText(documents[position]);
        checkCB.setChecked(isChecked[position]);

        return customView;
    }
}
