package com.example.quranapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quranapp.R;
import com.example.quranapp.model.Teacher;

import java.util.ArrayList;
import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.ViewHolder> implements Filterable {
    private List<Teacher> listItems;
    private List<Teacher> listItemsFiltered;
    private TeacherAdapterListener mListener;

    public TeacherAdapter(List<Teacher> listItems, Context context, TeacherAdapterListener mListener) {
        this.listItems = listItems;
        this.mListener = mListener;
        listItemsFiltered = new ArrayList<>(listItems);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_teacher, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Teacher listItem = listItems.get(position);
        holder.textTeacherName.setText("اسم الشيخ : " + listItem.getName());
        holder.textTeacherAddress.setText("عنوان الشيخ : " + listItem.getAddress());
        holder.textTeacherPhoneNumber.setText("هاتف الشيخ : " + listItem.getPhone());
        holder.timestamp.setText(listItem.getDate());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textTeacherName;
        public TextView textTeacherAddress;
        public TextView textTeacherPhoneNumber;
        public TextView timestamp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textTeacherName = itemView.findViewById(R.id.textTeacherName);
            textTeacherAddress = itemView.findViewById(R.id.textTeacherAddress);
            textTeacherPhoneNumber = itemView.findViewById(R.id.textTeacherPhoneNumber);
            timestamp = itemView.findViewById(R.id.timestamp);

            itemView.setOnClickListener(view -> {
                mListener.onTeacherSelected(listItems.get(getAdapterPosition()));
            });

        }
    }

    @Override
    public Filter getFilter() {
        return teacherFilter;
    }

    private Filter teacherFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Teacher> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(listItemsFiltered);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Teacher item : listItemsFiltered) {
                    if (item.getName().toLowerCase().contains(filterPattern) || item.getPhone().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listItems.clear();
            listItems.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };

    public interface TeacherAdapterListener {
        void onTeacherSelected(Teacher teacher);
    }

    public void filterList(ArrayList<Teacher> filteredList) {
        listItems = filteredList;
        notifyDataSetChanged();

    }

}
