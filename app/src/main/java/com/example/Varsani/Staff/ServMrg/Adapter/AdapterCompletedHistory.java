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
import com.example.Varsani.Staff.ServMrg.Adapter.Models.CompletedHistoryModel;
import com.example.Varsani.Staff.ServMrg.ViewCompletedTrainees;

import java.util.List;

public class AdapterCompletedHistory extends RecyclerView.Adapter<AdapterCompletedHistory.ViewHolder> {
    private Context context;
    private List<CompletedHistoryModel> historyList;

    public AdapterCompletedHistory(Context context, List<CompletedHistoryModel> historyList) {
        this.context = context;
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lv_completed_history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CompletedHistoryModel model = historyList.get(position);
        holder.traineeNames.setText("Trainee: " + model.getTraineeNames());
        holder.course.setText("Course: " + model.getCourse());
        holder.tvStatus.setText("Status: Graduated");

        // Set OnClickListener to navigate to ViewCompletedTrainees
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ViewCompletedTrainees.class);
            intent.putExtra("bookingID", model.getBookingID());
            intent.putExtra("clientID", model.getClientID());
            intent.putExtra("course", model.getCourse());
            intent.putExtra("studyMode", model.getStudyMode());
            intent.putExtra("fee", model.getFee());
            intent.putExtra("duration", model.getDuration());
            intent.putExtra("traineeNames", model.getTraineeNames());
            intent.putExtra("startingDate", model.getStartingDate());
            intent.putExtra("paymentMethod", model.getPaymentMethod());
            intent.putExtra("paymentCode", model.getPaymentCode());
            intent.putExtra("bookingDate", model.getBookingDate());
            intent.putExtra("rating", model.getRating());
            intent.putExtra("examMarks", model.getExamMarks());
            intent.putExtra("practicalMarks", model.getPracticalMarks());
            intent.putExtra("assignMarks", model.getAssignmentMarks());
            intent.putExtra("finalScore", model.getFinalScore());
            intent.putExtra("certNo", model.getCertificateNo());
            intent.putExtra("contact", model.getPhoneNo());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView traineeNames, course, tvStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            traineeNames = itemView.findViewById(R.id.tvTraineeNames);
            course = itemView.findViewById(R.id.tvCourse);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}
