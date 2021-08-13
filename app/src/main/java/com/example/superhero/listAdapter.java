package com.example.superhero;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.List;

public class listAdapter extends ArrayAdapter<Data> {


    public listAdapter(@NonNull Context context, @NonNull List<Data> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        Data current = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.adapter_view_layout, parent, false);
        }
            ((TextView) view.findViewById(R.id.Data)).setText(current.getQueryValue());
            ((TextView) view.findViewById(R.id.Query)).setText(current.getQueryName());
            return view;





    }
}
