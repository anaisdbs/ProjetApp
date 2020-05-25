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
    private List<Ingredients> listFull;
    private OnItemClickListener listener;
    private String myIngredient;

    public interface OnItemClickListener {
        void onItemClick(Ingredients item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtHeader;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = v.findViewById(R.id.firstLine);
        }
    }

    public ListAdapter(List<Ingredients> myDataset, OnItemClickListener listener) {
        this.listing = myDataset;
        this.listener = listener;
        listFull = new ArrayList<>(myDataset);
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Ingredients currentIngredient = listing.get(position);

        myIngredient = currentIngredient.getText().replaceAll("_", "");
        holder.txtHeader.setText(myIngredient.substring(0,1).toUpperCase()  + myIngredient.substring(1).toLowerCase());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(currentIngredient);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listing.size();
    }

    @Override
    public Filter getFilter() {
        return ingredientFilter;
    }

    private Filter ingredientFilter = new Filter() {
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
