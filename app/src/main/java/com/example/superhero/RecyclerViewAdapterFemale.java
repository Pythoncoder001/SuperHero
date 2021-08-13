package com.example.superhero;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapterFemale extends RecyclerView.Adapter<RecyclerViewAdapterFemale.RecyclerViewHolder> implements Filterable {
    private ArrayList<basic> recyclerDataArrayList;
    private ArrayList<basic>  recyclerDataArrayListfull;
    private Context mcontext;
    private OnItemClickListener mListener;




    public  interface OnItemClickListener {
        void onItemClick(int position);


    }
    public  void setOnItemClickListener(OnItemClickListener listener){
           mListener = listener;

    }



    public RecyclerViewAdapterFemale(ArrayList<basic> courseDataArrayList, Context mcontext) {
        this.recyclerDataArrayList = courseDataArrayList;
        recyclerDataArrayListfull = new ArrayList<>(recyclerDataArrayList);
        this.mcontext = mcontext;

    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        // Set the data to textview from our modal class.
        basic modal = recyclerDataArrayList.get(position);
        if(modal.getAppearance().getGender().equals("Female")) {
            holder.mname.setText(modal.getName());
            holder.msname.setText(modal.getSlug());

        }

    }

    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return recyclerDataArrayList.size();
    }

    // View Holder Class to handle Recycler View.
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our views.
        private TextView mname, msname;


        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our views with their ids.
            mname = itemView.findViewById(R.id.name);
            msname = itemView.findViewById(R.id.sname);

             itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     if(mListener != null){
                         int position = getAdapterPosition();
                         if(position != RecyclerView.NO_POSITION){
                             mListener.onItemClick(position);

                         }

                     }
                 }
             });
        }

    }
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<basic> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(recyclerDataArrayListfull);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(basic item : recyclerDataArrayListfull){
                    if(item.getName().toLowerCase().contains(filterPattern) || item.getId().contains(filterPattern)){
                        filteredList.add(item);

                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return  results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            recyclerDataArrayList.clear();
            recyclerDataArrayList.addAll((ArrayList)results.values);
            notifyDataSetChanged();

        }
    };
}




