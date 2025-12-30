package com.example.Varsani.Staff.Trainer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Varsani.R;
import com.example.Varsani.Staff.Trainer.Models.AssignedTraineeModel;

import java.util.List;

public class AdapterAssignedTrainees extends RecyclerView.Adapter<AdapterAssignedTrainees.TraineeViewHolder> {

    private Context context;
    private List<AssignedTraineeModel> traineeList;

    public AdapterAssignedTrainees(Context context, List<AssignedTraineeModel> traineeList) {
        this.context = context;
        this.traineeList = traineeList;
    }

    @NonNull
    @Override
    public TraineeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_trainee_item, parent, false);
        return new TraineeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TraineeViewHolder holder, int position) {
        // Get the current trainee from the list
        AssignedTraineeModel trainee = traineeList.get(position);

        // Set the trainee details to the view elements
        holder.textViewId.setText(trainee.getTraineeId());
        holder.textViewName.setText(trainee.getTraineeName());
        holder.textViewEmail.setText(trainee.getEmail());
        holder.textViewPhone.setText(trainee.getPhoneNo());
        holder.textViewStartingDate.setText(trainee.getStartingDate());
    }

    @Override
    public int getItemCount() {
        return traineeList.size();
    }

    // ViewHolder class to hold the views for each item
    public static class TraineeViewHolder extends RecyclerView.ViewHolder {

        TextView textViewId, textViewName, textViewEmail, textViewPhone, textViewStartingDate;

        public TraineeViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize the view elements
            textViewId = itemView.findViewById(R.id.textViewId);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewPhone = itemView.findViewById(R.id.textViewPhone);
            textViewStartingDate = itemView.findViewById(R.id.textViewStartingDate);
        }
    }
}
