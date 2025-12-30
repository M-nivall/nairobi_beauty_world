package com.example.Varsani.Staff.Store_mrg.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Varsani.R;
import com.example.Varsani.Staff.Store_mrg.CheckCompletedItems;
import com.example.Varsani.Staff.Store_mrg.Model.CompletedProductionModel;
import com.example.Varsani.Staff.Supervisor.Adapters.TrackProductionAdapter;
import com.example.Varsani.Staff.Supervisor.Models.TrackProductionModel;
import com.example.Varsani.Staff.Supervisor.TrackItems;
import com.google.gson.Gson;

import java.util.List;

public class CompletedProductionAdapter extends RecyclerView.Adapter<CompletedProductionAdapter.BatchViewHolder>{
    Context context;
    List<CompletedProductionModel> list;

    public CompletedProductionAdapter(Context context, List<CompletedProductionModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CompletedProductionAdapter.BatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CompletedProductionAdapter.BatchViewHolder(LayoutInflater.from(context).inflate(R.layout.item_batch_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedProductionAdapter.BatchViewHolder holder, int position) {
        CompletedProductionModel model = list.get(position);
        holder.txtBatch.setText("Batch No: " + model.getBatchNo());
        holder.txtProduct.setText("Product: " + model.getProduct());
        holder.txt_produce_date.setText("Production Date: " + model.getProductionDate());
        holder.txt_produce_status.setText("Status: " + model.getProductionStatus());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CheckCompletedItems.class);
            intent.putExtra("batch_no", model.getBatchNo());
            intent.putExtra("product", model.getProduct());
            intent.putExtra("productionDate", model.getProductionDate());
            intent.putExtra("status", model.getProductionStatus());
            intent.putExtra("quantity", model.getProduceQty());
            intent.putExtra("qty", model.getProducedQuantity());
            intent.putExtra("techName", model.getTechName());
            intent.putExtra("techNo", model.getTechNo());

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
