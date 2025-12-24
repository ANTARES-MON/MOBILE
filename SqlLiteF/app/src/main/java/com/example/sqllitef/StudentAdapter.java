package com.example.sqllitef;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private List<Student> students = new ArrayList<>();

    public void setStudents(List<Student> students) {
        this.students = students;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student currentStudent = students.get(position);
        holder.textViewNom.setText(currentStudent.getNom() + " " + currentStudent.getPrenom());
        holder.textViewNiveau.setText("Niveau: " + currentStudent.getNiveau());
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewNom;
        public TextView textViewNiveau;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNom = itemView.findViewById(android.R.id.text1);
            textViewNiveau = itemView.findViewById(android.R.id.text2);
        }
    }
}