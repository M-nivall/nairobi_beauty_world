package com.example.Varsani.Staff.Trainer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Varsani.R;
import com.example.Varsani.Staff.Trainer.Models.AttendanceTraineeModel;

import java.util.List;

public class ViewAttendanceAdapter extends RecyclerView.Adapter<ViewAttendanceAdapter.ViewHolder> {

    private Context context;
    private List<AttendanceTraineeModel> traineeList;

    // Constructor
    public ViewAttendanceAdapter(Context context, List<AttendanceTraineeModel> traineeList) {
        this.context = context;
        this.traineeList = traineeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_attendance_trainee, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get current trainee
        AttendanceTraineeModel trainee = traineeList.get(position);

        // Set data to the TextViews
        holder.txtTraineeId.setText(trainee.getTraineeId());
        holder.txtTraineeName.setText(trainee.getTraineeName());
    }

    @Override
    public int getItemCount() {
        return traineeList.size();
    }

    // ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTraineeId, txtTraineeName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTraineeId = itemView.findViewById(R.id.txtTraineeId);
            txtTraineeName = itemView.findViewById(R.id.txtTraineeName);
        }
    }
}
