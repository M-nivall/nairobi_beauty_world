package com.example.Varsani.Staff.Store_mrg.Adapter;

import static com.example.Varsani.utils.Urls.URL_REQUEST_STOCK;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.R;
import com.example.Varsani.Staff.Store_mrg.Model.StockModel;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterStock extends RecyclerView.Adapter<AdapterStock.ViewHolder> {
    private List<StockModel> roomList;
    private Context context;
    private List<String> supplierList; // List of suppliers

    public AdapterStock(Context context, List<StockModel> roomList, List<String> supplierList) {
        this.context = context;
        this.roomList = roomList;
        this.supplierList = supplierList; // Pass the list of suppliers
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txv_material_name, txv_stock_level;
        Button btn_request_stock;

        public ViewHolder(View view) {
            super(view);
            txv_material_name = view.findViewById(R.id.txv_material_name);
            txv_stock_level = view.findViewById(R.id.txv_stock_level);
            btn_request_stock = view.findViewById(R.id.btn_request_stock);
        }
    }

    @Override
    public AdapterStock.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.material_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        StockModel c = roomList.get(position);
        holder.txv_material_name.setText("Material: " + c.getProductName());
        holder.txv_stock_level.setText("Quantity: " + c.getStock() + " Litres");

        holder.btn_request_stock.setOnClickListener(v -> showRequestStockDialog(c.getProductName()));
    }

    private void showRequestStockDialog(String materialName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_request_stock, null);
        builder.setView(dialogView);

        TextView txtMaterial = dialogView.findViewById(R.id.txt_material_name);
        Spinner spnSupplier = dialogView.findViewById(R.id.spn_supplier);
        EditText edtQuantity = dialogView.findViewById(R.id.edt_quantity);
        Button btnSubmit = dialogView.findViewById(R.id.btn_submit_request);

        txtMaterial.setText("Requesting: " + materialName);

        // Populate supplier spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, supplierList);
        spnSupplier.setAdapter(adapter);

        AlertDialog dialog = builder.create();
        dialog.show();

        btnSubmit.setOnClickListener(v -> {
            String selectedSupplier = spnSupplier.getSelectedItem().toString();
            String quantity = edtQuantity.getText().toString().trim();

            if (quantity.isEmpty()) {
                edtQuantity.setError("Enter quantity");
                return;
            }

            sendRequest(selectedSupplier, materialName, quantity);
            dialog.dismiss();
        });
    }
    private void sendRequest(String selectedSupplier, String materialName, String quantity) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REQUEST_STOCK,
                response -> {
                    try {
                        Log.e("RESPONSE", response);
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        String msg = jsonObject.getString("message");

                        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP, 0, 250);
                        toast.show();

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast toast = Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP, 0, 250);
                        toast.show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast toast = Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 250);
                    toast.show();
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("supplier", selectedSupplier);
                params.put("item", materialName);
                params.put("quantity", quantity);
                Log.e("PARAMS", "" + params);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }
}
