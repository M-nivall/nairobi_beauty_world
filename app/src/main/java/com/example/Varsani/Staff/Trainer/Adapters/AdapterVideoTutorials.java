package com.example.Varsani.Staff.Trainer.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.Varsani.R;
import com.example.Varsani.Staff.Trainer.Models.VideoTutorialsModel;
import com.example.Varsani.Staff.Trainer.PlayVideo;

import java.util.List;

public class AdapterVideoTutorials extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<VideoTutorialsModel> items,searchView;
    private Context ctx;
    private String packageId;

    public AdapterVideoTutorials(Context context, List<VideoTutorialsModel> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        private TextView txvTitle, txvDetails,txvDatePosted;
        public OriginalViewHolder(View v) {
            super(v);
            txvTitle = v.findViewById(R.id.txvTitle);
            txvDetails = v.findViewById(R.id.txvDetails);
            txvDatePosted = v.findViewById(R.id.txvDatePosted);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_video_tutorials, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            final OriginalViewHolder view = (OriginalViewHolder) holder;

            final VideoTutorialsModel data = items.get(position);

            view.txvTitle.setText(data.getTitle());
            view.txvDetails.setText(data.getDetails());
            view.txvDatePosted.setText("Posted on:: "+data.getDatePosted());


            view.itemView.setOnClickListener(v -> {
                Intent in = new Intent(ctx, PlayVideo.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                in.putExtra("subjectId", data.getSubjectId());
                in.putExtra("id", data.getId());
                in.putExtra("subjectName", data.getSubjectName());
                in.putExtra("title", data.getTitle());
                in.putExtra("details", data.getDetails());
                in.putExtra("videoLink", data.getVideoLink());
                in.putExtra("datePosted", data.getDatePosted());
                ctx.startActivity(in);

            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
