package com.example.todo_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirestoreRegistrar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ItemAdapter extends FirestoreRecyclerAdapter<Item,
        ItemAdapter.ItemHolder> {
    public ItemAdapter(@NonNull FirestoreRecyclerOptions<Item> options) {

        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull ItemHolder holder, int position,
                                    @NonNull Item model) {
        holder.textViewTitle.setText(model.getTitle());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(model.getDueDate());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(calendar.getTime());

        holder.textViewDueDate.setText(date);
        holder.textViewResponsiblePerson.setText(model.getResponsiblePerson());
        holder.textViewDescription.setText(model.getDescription());
        holder.textViewPriority.setText(String.valueOf(model.getPriority()));
    }
    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.recycler_item, parent, false);
        return new ItemHolder(v);
    }
    class ItemHolder extends RecyclerView.ViewHolder{
        TextView textViewTitle;
        TextView textViewDueDate;
        TextView textViewResponsiblePerson;
        TextView textViewPriority;
        TextView textViewDescription;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDueDate = itemView.findViewById(R.id.text_view_dueDate);
            textViewResponsiblePerson =
                    itemView.findViewById(R.id.text_view_responsiblePerson);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);
            textViewDescription =
                    itemView.findViewById(R.id.text_view_description);
        }
    }
    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }
}