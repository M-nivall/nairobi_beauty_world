package com.example.Varsani.Clients.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Varsani.Clients.Models.ClassAttendanceModel;
import com.example.Varsani.Clients.SignAttendance;
import com.example.Varsani.R;

import java.util.List;

public class AdapterClassAttendance extends RecyclerView.Adapter<AdapterClassAttendance.ViewHolder> {

    private Context context;
    private List<ClassAttendanceModel> attendanceList;

    public AdapterClassAttendance(Context context, List<ClassAttendanceModel> attendanceList) {
        this.context = context;
        this.attendanceList = attendanceList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_class_attendance, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClassAttendanceModel model = attendanceList.get(position);

        holder.txtTopic.setText("Topic: " + model.getTopic());
        holder.txtInstructor.setText("Instructor Name: " + model.getInstructorName());
        holder.txtSessionDate.setText("Date: " + model.getSessionDate());

        // Set OnClickListener for CardView
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, SignAttendance.class);
            intent.putExtra("sessionId", model.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTopic, txtInstructor, txtSessionDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTopic = itemView.findViewById(R.id.txtTopic);
            txtInstructor = itemView.findViewById(R.id.txtInstructorName);
            txtSessionDate = itemView.findViewById(R.id.txtSessionDate);
        }
    }
}
