package com.example.quranapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quranapp.R;
import com.example.quranapp.model.Review;
import com.example.quranapp.model.Student;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> implements Filterable {
    private List<Review> listItems;
    private List<Review> listItemsFiltered;

    public ReviewAdapter(List<Review> listItems, Context context) {
        this.listItems = listItems;
        listItemsFiltered = new ArrayList<>(listItems);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_review, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review listItem = listItems.get(position);
        holder.textReviewDecName.setText(listItem.getReviewDec());
        holder.textNumberofPart.setText(listItem.getNumberOfParts());
//        holder.textStudentPhoneNumber.setText(listItem.getPhone());
//        holder.timestamp.setText(listItem.getDate());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textReviewDecName;
        public TextView textNumberofPart;
//        public TextView textStudentPhoneNumber;
//        public TextView timestamp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textReviewDecName = itemView.findViewById(R.id.textReviewDecName);
            textNumberofPart = itemView.findViewById(R.id.textNumberofPart);
//            textStudentPhoneNumber = itemView.findViewById(R.id.textTaskDetails);
//            timestamp = itemView.findViewById(R.id.timestamp);

        }
    }

    @Override
    public Filter getFilter() {
        return contactFilter;
    }

    private Filter contactFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Review> filteredList =new ArrayList<>();

            if (constraint==null|| constraint.length()==0){
                filteredList.addAll(listItemsFiltered);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Review item : listItemsFiltered){
                    if (item.getReviewDec().toLowerCase().contains(filterPattern) || item.getNumberOfParts().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results =new FilterResults();
            results.values=filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listItems.clear();
            listItems.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };
    public interface ReviewAdapterListener {
        void onReviewSelected(Review review);
    }
}
