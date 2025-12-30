package com.example.Varsani.Staff.Trainer.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.Varsani.R;
import com.example.Varsani.Staff.Trainer.Models.SubmittedAssignmentModel;
import com.example.Varsani.Staff.Trainer.ReadSubmittedAssignment;

import java.util.List;

public class AdapterSubmittedAssignment extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<SubmittedAssignmentModel> items,searchView;
    private Context ctx;
    private String packageId;

    public AdapterSubmittedAssignment(Context context, List<SubmittedAssignmentModel> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        private TextView txv_names,txv_trainee_id;
        public OriginalViewHolder(View v) {
            super(v);
            txv_names = v.findViewById(R.id.txv_names);
            txv_trainee_id = v.findViewById(R.id.txv_trainee_id);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_submitted_assignment, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            final OriginalViewHolder view = (OriginalViewHolder) holder;

            final SubmittedAssignmentModel data = items.get(position);

            view.txv_names.setText("Name: "+data.getNames());
            view.txv_trainee_id.setText("ID: "+data.getTrainee_id());


            view.itemView.setOnClickListener(v -> {
                Intent in = new Intent(ctx, ReadSubmittedAssignment.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                in.putExtra("names", data.getNames());
                in.putExtra("id", data.getTrainee_id());
                //in.putExtra("datePosted", data.getDatePosted());
                in.putExtra("pdfLink", data.getPdfLink());
                ctx.startActivity(in);

            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
