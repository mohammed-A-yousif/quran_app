package com.example.quranapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Filter;
import android.widget.Filterable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements Filterable {
    private List<Teacher> listItems;
    private List<Teacher> listItemsFiltered;

    public MyAdapter(List<Teacher> listItems, Context context) {
        this.listItems = listItems;
        listItemsFiltered = new ArrayList<>(listItems);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row_teach, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Teacher listItem = listItems.get(position);
        holder.textViewName.setText(listItem.getName());
        holder.textViewPhone.setText(listItem.getPhone());
        holder.textViewDate.setText(listItem.getDate());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewPhone;
        public TextView textViewDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.teacher_name_textView);
            textViewPhone = itemView.findViewById(R.id.phone_num_textView);
            textViewDate = itemView.findViewById(R.id.date_textView);
        }
    }

    @Override
    public Filter getFilter() {
        return contactFilter;
    }

    private Filter contactFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Teacher> filteredList =new ArrayList<>();

            if (constraint==null|| constraint.length()==0){
                filteredList.addAll(listItemsFiltered);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Teacher item : listItemsFiltered){
                    if (item.getName().toLowerCase().contains(filterPattern) || item.getPhone().contains(filterPattern)){
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
    public interface MyAdapterListener {
        void onContactSelected(Teacher teacher);
    }
}
