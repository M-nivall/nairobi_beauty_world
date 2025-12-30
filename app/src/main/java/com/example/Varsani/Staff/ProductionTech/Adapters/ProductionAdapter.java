package com.example.Varsani.Staff.ProductionTech.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Varsani.R;
import com.example.Varsani.Staff.ProductionTech.ProductionTasks;
import com.example.Varsani.Staff.Supervisor.Adapters.BatchAdapter;
import com.example.Varsani.Staff.Supervisor.Models.ProductionBatchModel;
import com.example.Varsani.Staff.Supervisor.ProductionItems;
import com.google.gson.Gson;

import java.util.List;

public class ProductionAdapter extends RecyclerView.Adapter<ProductionAdapter.BatchViewHolder>{
    Context context;
    List<ProductionBatchModel> list;

    public ProductionAdapter(Context context, List<ProductionBatchModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ProductionAdapter.BatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductionAdapter.BatchViewHolder(LayoutInflater.from(context).inflate(R.layout.item_batch_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductionAdapter.BatchViewHolder holder, int position) {
        ProductionBatchModel model = list.get(position);
        holder.txtBatch.setText("Batch No: " + model.getBatchNo());
        holder.txtProduct.setText("Product: " + model.getProduct());
        holder.txt_produce_date.setText("Production Date: " + model.getProductionDate());
        holder.txt_produce_status.setText("Status: " + model.getProductionStatus());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductionTasks.class);
            intent.putExtra("batch_no", model.getBatchNo());
            intent.putExtra("product", model.getProduct());
            intent.putExtra("productionDate", model.getProductionDate());
            intent.putExtra("status", model.getProductionStatus());
            intent.putExtra("quantity", model.getProduceQty());

            // Pass materials as JSON
            Gson gson = new Gson();
            intent.putExtra("materials_json", gson.toJson(model.getMaterials()));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return list.size(); }

    class BatchViewHolder extends RecyclerView.ViewHolder {
        TextView txtBatch, txtProduct,txt_produce_date,txt_produce_status;
        public BatchViewHolder(@NonNull View itemView) {
            super(itemView);
            txtBatch = itemView.findViewById(R.id.txt_batch);
            txtProduct = itemView.findViewById(R.id.txt_product);
            txt_produce_date = itemView.findViewById(R.id.txt_produce_date);
            txt_produce_status = itemView.findViewById(R.id.txt_produce_status);
        }
    }
}
