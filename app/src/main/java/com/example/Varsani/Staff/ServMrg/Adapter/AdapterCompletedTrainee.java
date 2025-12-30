package com.example.Varsani.Staff.ServMrg.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Varsani.R;
import com.example.Varsani.Staff.ServMrg.Adapter.Models.CompletedTraineeModel;
import com.example.Varsani.Staff.ServMrg.ApproveCertificate;

import java.util.List;

public class AdapterCompletedTrainee extends RecyclerView.Adapter<AdapterCompletedTrainee.TraineeViewHolder> {

    private final List<CompletedTraineeModel> traineeList;
    private final Context context;

    public AdapterCompletedTrainee(Context context, List<CompletedTraineeModel> traineeList) {
        this.context = context;
        this.traineeList = traineeList;
    }

    @NonNull
    @Override
    public TraineeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_completed_trainee, parent, false);
        return new TraineeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TraineeViewHolder holder, int position) {
        CompletedTraineeModel trainee = traineeList.get(position);
        holder.textViewTraineeId.setText("TraineeID: " + trainee.getTraineeId());
        holder.textViewTraineeNames.setText("Name: " + trainee.getTraineeNames());
        holder.textViewStatus.setText("Stays: To Graduate");

        // Set onClickListener to handle the item click
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ApproveCertificate.class);
            // Pass data to ApproveLicence activity
            intent.putExtra("traineeId", trainee.getTraineeId());
            intent.putExtra("traineeNames", trainee.getTraineeNames());
            intent.putExtra("finalScore", trainee.getFinalScore());
            intent.putExtra("examMarks", trainee.getExamMarks());
            intent.putExtra("assignMarks", trainee.getAssignMarks());
            intent.putExtra("practicalMarks", trainee.getPracticalMarks());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return traineeList.size();
    }

    static class TraineeViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTraineeId, textViewTraineeNames,textViewStatus;

        public TraineeViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTraineeId = itemView.findViewById(R.id.textViewTraineeId);
            textViewTraineeNames = itemView.findViewById(R.id.textViewTraineeNames);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
        }
    }
}
