package com.example.Varsani.Staff.Trainer.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.Varsani.Clients.FinalExam;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;
import com.example.Varsani.Staff.Trainer.Models.UnitsModel;
import com.example.Varsani.Staff.Trainer.SelectAction;
import com.example.Varsani.Staff.Trainer.SetExam;
import com.example.Varsani.utils.SessionHandler;

import java.util.List;

public class AdapterUnits extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<UnitsModel> items;
    private Context ctx;
    private String packageId;
    private SessionHandler session;
    private UserModel user;

    public AdapterUnits(Context context, List<UnitsModel> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        private TextView txvSubjectName, txvModule;
        private ImageView img_thumb_up;

        public OriginalViewHolder(View v) {
            super(v);
            txvSubjectName = v.findViewById(R.id.txvSubjectName);
            txvModule = v.findViewById(R.id.txv_module);
            img_thumb_up = v.findViewById(R.id.img_ic);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_units, parent, false);
        return new OriginalViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            final OriginalViewHolder view = (OriginalViewHolder) holder;
            final UnitsModel data = items.get(position);

            session=new SessionHandler(ctx.getApplicationContext());
            user=session.getUserDetails();

            view.txvSubjectName.setText(data.getUnitName());
            view.txvModule.setText(data.getUnitStatus());

            view.img_thumb_up.setVisibility(View.GONE);

            view.itemView.setOnClickListener(v -> {
                // Check if it's case 3 (Exam Week)
                if (position == 3) {
                    // Check user type from SessionHandler
                    if (user.getUser_type().equalsIgnoreCase("Trainer")) {
                        Intent in = new Intent(ctx, SetExam.class);
                        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        ctx.startActivity(in);
                    } else if (user.getUser_type().equalsIgnoreCase("Client")) {
                        Intent in = new Intent(ctx, FinalExam.class);
                        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        ctx.startActivity(in);
                    }
                } else {
                    // For cases other than 3, navigate to SelectAction activity
                    Intent in = new Intent(ctx, SelectAction.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    in.putExtra("subjectId", data.getUnitId());
                    in.putExtra("packageId", packageId);
                    in.putExtra("subjectName", data.getUnitName());
                    ctx.startActivity(in);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
