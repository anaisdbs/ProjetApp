package com.example.app.presentation.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.presentation.model.Ingredients;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> implements Filterable  {
    private List<Ingredients> listing;
    private List<Ingredients> listFull; //
    private OnItemClickListener listener;


    public interface OnItemClickListener {
        void onItemClick(Ingredients item);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader;
  //      public TextView txtFooter;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
     //       txtFooter = (TextView) v.findViewById(R.id.secondLine);
        }
    }

    public void add(int position, Ingredients item) {
        listing.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        listing.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListAdapter(List<Ingredients> myDataset, OnItemClickListener listener) {
        this.listing = myDataset;
        this.listener = listener;
        listFull = new ArrayList<>(myDataset); //
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Ingredients currentIngredient = listing.get(position);
        holder.txtHeader.setText(currentIngredient.getText());
    //    holder.txtFooter.setText(currentIngredient.getText());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(currentIngredient);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listing.size();
    }


    @Override
    public Filter getFilter() { //
        return ingredientFilter;
    }

    private Filter ingredientFilter = new Filter() { //
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Ingredients> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(listFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Ingredients item : listFull){
                    if (item.getText().toLowerCase().contains(filterPattern)){
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
            listing.clear();
            listing.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };



}
