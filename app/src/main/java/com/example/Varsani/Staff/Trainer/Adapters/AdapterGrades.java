package com.example.Varsani.Staff.Trainer.Adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Varsani.R;
import com.example.Varsani.Staff.Trainer.Models.GradeModel;

import java.util.List;

public class AdapterGrades extends RecyclerView.Adapter<AdapterGrades.GradeViewHolder> {

    private final List<GradeModel> traineeList;

    public AdapterGrades(List<GradeModel> traineeList) {
        this.traineeList = traineeList;
    }

    @NonNull
    @Override
    public GradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grade, parent, false);
        return new GradeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GradeViewHolder holder, int position) {
        GradeModel trainee = traineeList.get(position);

        // Set trainee name
        holder.textViewName.setText(trainee.getTraineeName());

        // Set assignment marks
        holder.textViewAssignmentMarks.setText(trainee.getAssignmentMarks());

        // Set exam marks
        holder.textViewExamMarks.setText(trainee.getExamMarks());

        // Remove any existing TextWatcher to prevent multiple triggers
        if (holder.editTextTheoryMarks.getTag() instanceof TextWatcher) {
            holder.editTextTheoryMarks.removeTextChangedListener((TextWatcher) holder.editTextTheoryMarks.getTag());
        }

        // Set the theory marks if they exist
        holder.editTextTheoryMarks.setText(trainee.getTheoryMarks());

        // Add a new TextWatcher to capture text input changes
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                trainee.setTheoryMarks(s.toString()); // Save the input to the model
            }
        };

        holder.editTextTheoryMarks.addTextChangedListener(textWatcher);
        holder.editTextTheoryMarks.setTag(textWatcher); // Store the TextWatcher reference
    }

    @Override
    public int getItemCount() {
        return traineeList.size();
    }

    public List<GradeModel> getTraineeList() {
        return traineeList;
    }

    static class GradeViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewAssignmentMarks, textViewExamMarks;
        EditText editTextTheoryMarks;

        GradeViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewAssignmentMarks = itemView.findViewById(R.id.textViewAssignmentMarks);
            textViewExamMarks = itemView.findViewById(R.id.textViewExamMarks);
            editTextTheoryMarks = itemView.findViewById(R.id.editTextTheoryMarks);
        }
    }

}
