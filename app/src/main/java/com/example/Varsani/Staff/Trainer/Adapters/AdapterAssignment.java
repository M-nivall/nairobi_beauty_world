package com.example.Varsani.Staff.Trainer.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.Varsani.R;
import com.example.Varsani.Staff.Trainer.Models.AssignmentsModel;
import com.example.Varsani.Staff.Trainer.ReadAssignment;

import java.util.List;

public class AdapterAssignment extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<AssignmentsModel> items,searchView;
    private Context ctx;
    private String packageId;

    public AdapterAssignment(Context context, List<AssignmentsModel> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        private TextView txvTitle,txvDatePosted;
        public OriginalViewHolder(View v) {
            super(v);
            txvTitle = v.findViewById(R.id.txvTitle);
            txvDatePosted = v.findViewById(R.id.txvDatePosted);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_assignment, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            final OriginalViewHolder view = (OriginalViewHolder) holder;

            final AssignmentsModel data = items.get(position);

            view.txvTitle.setText(data.getTitle());
            view.txvDatePosted.setText("Posted on: "+data.getDatePosted());


            view.itemView.setOnClickListener(v -> {
                Intent in = new Intent(ctx, ReadAssignment.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                in.putExtra("id", data.getId());
                in.putExtra("title", data.getTitle());
                in.putExtra("datePosted", data.getDatePosted());
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
