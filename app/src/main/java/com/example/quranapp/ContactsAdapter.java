package com.example.quranapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder>
        implements Filterable {
    private Context context;
    private List<Teacher> mTeacherList;
    private List<Teacher> mTeacherListFiltered;
    private ContactsAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, phone;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.teacher_name_textView);
            phone = view.findViewById(R.id.phone_num_textView);
            thumbnail = view.findViewById(R.id.teacher_image_view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(mTeacherListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }


    public ContactsAdapter(Context context, List<Teacher> teacherList, ContactsAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.mTeacherList = teacherList;
        this.mTeacherListFiltered = teacherList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_row_teach, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Teacher teacher = mTeacherListFiltered.get(position);
        holder.name.setText(teacher.getName());
        holder.phone.setText(teacher.getPhone());

//        Glide.with(context).load(contact.getImage()).apply(RequestOptions.circleCropTransform()).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return mTeacherListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mTeacherListFiltered = mTeacherList;
                } else {
                    List<Teacher> filteredList = new ArrayList<>();
                    for (Teacher row : mTeacherList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getPhone().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    mTeacherListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mTeacherListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mTeacherListFiltered = (ArrayList<Teacher>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactsAdapterListener {
        void onContactSelected(Teacher teacher);
    }
}
