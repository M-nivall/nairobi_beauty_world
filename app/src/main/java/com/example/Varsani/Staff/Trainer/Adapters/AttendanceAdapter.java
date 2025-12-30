package com.example.Varsani.Staff.Trainer.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Varsani.R;
import com.example.Varsani.Staff.Trainer.Models.AttendanceModel;
import com.example.Varsani.Staff.Trainer.ViewAttendance;

import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {

    private Context context;
    private List<AttendanceModel> attendanceList;

    // Constructor
    public AttendanceAdapter(Context context, List<AttendanceModel> attendanceList) {
        this.context = context;
        this.attendanceList = attendanceList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_attendance_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AttendanceModel model = attendanceList.get(position);
        holder.txtId.setText("Session ID: " + model.getId());
        holder.txtTopic.setText("Topic: " + model.getTopic());
        holder.txtSessionDate.setText("Date: " + model.getSessionDate());

        // Set click listener on card item
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ViewAttendance.class);
            intent.putExtra("sessionId", model.getId());
            intent.putExtra("topic", model.getTopic());
            intent.putExtra("sessionDate", model.getSessionDate());

            // Check if context is an instance of Activity and set the flag if it's not
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Add this flag to allow starting activity from non-Activity context
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return attendanceList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtId, txtTopic, txtSessionDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtId = itemView.findViewById(R.id.txtId);
            txtTopic = itemView.findViewById(R.id.txtTopic);
            txtSessionDate = itemView.findViewById(R.id.txtSessionDate);
        }
    }
}
