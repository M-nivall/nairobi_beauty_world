package com.example.Varsani.Staff.Supervisor;

import static com.example.Varsani.utils.Urls.URL_APPROVE_COMPLETION;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.R;
import com.example.Varsani.Staff.Supervisor.Models.MaterialModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrackItems extends AppCompatActivity {
    private TextView txtBatch, txtProduct, txt_produce_qty, txt_production_date, txt_production_status,
            txt_final_qty, txt_production_tech, txt_tell;
    private TableLayout tableMaterials;
    private CardView card_bottom;
    private Button btn_confirm;
    private String batch_no,product,productionDate,quantity,status,final_qty,techName,techNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_items);

        txtBatch = findViewById(R.id.txt_batch_number);
        txtProduct = findViewById(R.id.txt_product_name);
        txt_produce_qty = findViewById(R.id.txt_produce_qty);
        txt_production_date = findViewById(R.id.txt_production_date);
        txt_production_status = findViewById(R.id.txt_production_status);
        txt_tell = findViewById(R.id.txt_tell);
        txt_production_tech = findViewById(R.id.txt_production_tech);
        tableMaterials = findViewById(R.id.table_materials);
        card_bottom = findViewById(R.id.card_bottom);
        txt_final_qty = findViewById(R.id.txt_final_qty);
        btn_confirm = findViewById(R.id.btn_confirm);

        batch_no = getIntent().getStringExtra("batch_no");
        product = getIntent().getStringExtra("product");
        productionDate = getIntent().getStringExtra("productionDate");
        status = getIntent().getStringExtra("status");
        quantity = getIntent().getStringExtra("quantity");
        final_qty = getIntent().getStringExtra("qty");
        techName = getIntent().getStringExtra("techName");
        techNo = getIntent().getStringExtra("techNo");
        String materialsJson = getIntent().getStringExtra("materials_json");

        txtBatch.setText("Batch No: " + batch_no);
        txtProduct.setText("Product: " + product);
        txt_produce_qty.setText("Estimated Quantity: " + quantity);
        txt_production_date.setText("Production Date: " + productionDate);
        txt_production_status.setText("Status: " + status);
        txt_production_tech.setText("Technician: " + techName);
        txt_tell.setText("Phone No: " + techNo);


        if (status.equalsIgnoreCase("Completed")) {
            card_bottom.setVisibility(View.VISIBLE);
            txt_final_qty.setText("Produced Quantity: " + final_qty);
        }

        Gson gson = new Gson();
        Type listType = new TypeToken<List<MaterialModel>>() {}.getType();
        List<MaterialModel> materials = gson.fromJson(materialsJson, listType);

        // Add table header
        TableRow header = new TableRow(this);
        header.setPadding(0, 0, 0, 16);

        TextView h1 = new TextView(this);
        h1.setText("Material");
        h1.setTypeface(null, Typeface.BOLD);
        h1.setPadding(16, 8, 16, 8);
        h1.setTextColor(Color.parseColor("#333333"));

        TextView h2 = new TextView(this);
        h2.setText("Qty");
        h2.setTypeface(null, Typeface.BOLD);
        h2.setPadding(16, 8, 16, 8);
        h2.setTextColor(Color.parseColor("#333333"));

        TextView h3 = new TextView(this);
        h3.setText("Unit");
        h3.setTypeface(null, Typeface.BOLD);
        h3.setPadding(16, 8, 16, 8);
        h3.setTextColor(Color.parseColor("#333333"));

        header.addView(h1);
        header.addView(h2);
        header.addView(h3);
        tableMaterials.addView(header);

        // Populate table rows with materials
        for (MaterialModel material : materials) {
            TableRow row = new TableRow(this);

            TextView name = new TextView(this);
            TextView qty = new TextView(this);
            TextView unit = new TextView(this);

            name.setText(material.getMaterialName());
            name.setPadding(16, 8, 16, 8);

            qty.setText(material.getQuantity());
            qty.setPadding(16, 8, 16, 8);

            unit.setText(material.getUnit());
            unit.setPadding(16, 8, 16, 8);

            row.addView(name);
            row.addView(qty);
            row.addView(unit);
            tableMaterials.addView(row);
        }

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAlertConfirm(v);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void confirm() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_APPROVE_COMPLETION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("RESPONSE", response);
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String msg = jsonObject.getString("message");
                            if (status.equals("1")) {
                                Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP, 0, 250);
                                toast.show();
                                finish();
                            } else {
                                Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP, 0, 250);
                                toast.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast toast = Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0, 250);
                            toast.show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast toast = Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 250);
                toast.show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("batch_no", batch_no);
                Log.e("Params", "" + params);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void getAlertConfirm(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Completion");

        builder.setMessage("Make sure production is complete and product meets the required standards");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //progressBar1.setVisibility(View.VISIBLE);
                confirm();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }
}