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
import com.example.quranapp.model.Student;
import com.example.quranapp.model.Teacher;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> implements Filterable {
    private List<Student> listItems;
    private List<Student> listItemsFiltered;

    public StudentAdapter(List<Student> listItems, Context context) {
        this.listItems = listItems;
        listItemsFiltered = new ArrayList<>(listItems);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_student, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student listItem = listItems.get(position);
        holder.textStudentName.setText("اسم الدارس : " + listItem.getName());
        holder.textTeacherName.setText("اسم الشيخ : " + listItem.getTeacherName());
        holder.textStudentAddress.setText("عنوان الدارس : " + listItem.getAddress());
        holder.textStudentPhoneNumber.setText("هاتف الدارس : " + listItem.getPhone());
        holder.timestamp.setText(listItem.getDate());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textStudentName;
        public TextView textTeacherName;
        public TextView textStudentAddress;
        public TextView textStudentPhoneNumber;
        public TextView timestamp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textStudentName = itemView.findViewById(R.id.textStudentName);
            textTeacherName = itemView.findViewById(R.id.textteacherName);
            textStudentAddress = itemView.findViewById(R.id.textStudentAddress);
            textStudentPhoneNumber = itemView.findViewById(R.id.textStudentPhone);
            timestamp = itemView.findViewById(R.id.timestamp);

        }
    }

    @Override
    public Filter getFilter() {
        return contactFilter;
    }

    private Filter contactFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Student> filteredList =new ArrayList<>();

            if (constraint==null|| constraint.length()==0){
                filteredList.addAll(listItemsFiltered);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Student item : listItemsFiltered){
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
    public interface StudentAdapterListener {
        void onStudentSelected(Student student);
    }

    public void filterList(ArrayList<Student> filteredList) {
        listItems = filteredList;
        notifyDataSetChanged();

    }
}
