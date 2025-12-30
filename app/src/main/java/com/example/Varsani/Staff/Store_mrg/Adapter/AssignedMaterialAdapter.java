package com.example.Varsani.Staff.Store_mrg.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.Varsani.R;
import com.example.Varsani.Staff.Store_mrg.Model.AssignedMaterial;

import java.util.List;

public class AssignedMaterialAdapter extends RecyclerView.Adapter<AssignedMaterialAdapter.ViewHolder> {

    private List<AssignedMaterial> materialList;

    public AssignedMaterialAdapter(List<AssignedMaterial> materialList) {
        this.materialList = materialList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtQuantity, txtUnit;
        Button btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_material_name);
            txtQuantity = itemView.findViewById(R.id.txt_quantity);
            txtUnit = itemView.findViewById(R.id.txt_unit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }

    @Override
    public AssignedMaterialAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assigned_material_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AssignedMaterialAdapter.ViewHolder holder, int position) {
        AssignedMaterial item = materialList.get(position);
        holder.txtName.setText(item.getMaterialName());
        holder.txtQuantity.setText(item.getQuantity());
        holder.txtUnit.setText(item.getUnit());

        holder.btnDelete.setOnClickListener(v -> {
            materialList.remove(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return materialList.size();
    }
}
