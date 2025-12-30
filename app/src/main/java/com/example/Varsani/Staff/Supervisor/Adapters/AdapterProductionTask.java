// AdapterProductionTask.java
package com.example.Varsani.Staff.Supervisor.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Varsani.R;
import com.example.Varsani.Staff.Supervisor.Models.ProductionTaskModel;
import com.example.Varsani.Staff.Supervisor.ProductionItems;

import java.util.List;

public class AdapterProductionTask extends RecyclerView.Adapter<AdapterProductionTask.ProductionViewHolder> {

    private Context context;
    private List<ProductionTaskModel> list;

    public AdapterProductionTask(Context context, List<ProductionTaskModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ProductionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.production_card, parent, false);
        return new ProductionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductionViewHolder holder, int position) {
        ProductionTaskModel model = list.get(position);

        holder.txtProduct.setText("Product: " + model.getProduct());
        holder.txtBatchNo.setText("Batch No: " + model.getBatch_no());
        holder.txtProductionDate.setText("Date: " + model.getProduction_date());
        holder.txtStatus.setText("Status: " + model.getProduction_status());

        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductionItems.class);
            intent.putExtra("product", model.getProduct());
            intent.putExtra("batch_no", model.getBatch_no());
            intent.putExtra("produce_qty", model.getProduce_qty());
            intent.putExtra("production_date", model.getProduction_date());
            intent.putExtra("material_name", model.getMaterial_name());
            intent.putExtra("quantity", model.getQuantity());
            intent.putExtra("unit", model.getUnit());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ProductionViewHolder extends RecyclerView.ViewHolder {
        TextView txtProduct, txtBatchNo, txtProductionDate, txtStatus;
        CardView cardView;

        public ProductionViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProduct = itemView.findViewById(R.id.txtProduct);
            txtBatchNo = itemView.findViewById(R.id.txtBatchNo);
            txtProductionDate = itemView.findViewById(R.id.txtProductionDate);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            cardView = itemView.findViewById(R.id.cardProduction);
        }
    }
}
