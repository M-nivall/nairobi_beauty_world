package com.example.Varsani.Staff.Supervisor;

import static com.example.Varsani.utils.Urls.URL_ASSIGN_PRODUCTION;
import static com.example.Varsani.utils.Urls.URL_GET_PRODUCTION_TECH;

import androidx.appcompat.app.AppCompatActivity;

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

public class ProductionItems extends AppCompatActivity {

    private TextView txtBatch, txtProduct, txt_produce_qty, txt_production_date, txt_production_status;
    private TableLayout tableMaterials;
    private EditText edt_tech;
    private Button btn_assign;
    private ArrayList<String> techs;
    private ArrayList<String> techFullNames;
    private String batch_no,product,productionDate,quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_production_items);

        txtBatch = findViewById(R.id.txt_batch_number);
        txtProduct = findViewById(R.id.txt_product_name);
        txt_produce_qty = findViewById(R.id.txt_produce_qty);
        txt_production_date = findViewById(R.id.txt_production_date);
        txt_production_status = findViewById(R.id.txt_production_status);
        tableMaterials = findViewById(R.id.table_materials);
        edt_tech = findViewById(R.id.edt_tech);
        btn_assign = findViewById(R.id.btn_assign);

        batch_no = getIntent().getStringExtra("batch_no");
        product = getIntent().getStringExtra("product");
        productionDate = getIntent().getStringExtra("productionDate");
        String status = getIntent().getStringExtra("status");
        quantity = getIntent().getStringExtra("quantity");
        String materialsJson = getIntent().getStringExtra("materials_json");

        txtBatch.setText("Batch No: " + batch_no);
        txtProduct.setText("Product: " + product);
        txt_produce_qty.setText("Estimated Quantity: " + quantity);
        txt_production_date.setText("Production Date: " + productionDate);
        txt_production_status.setText("Status: " + status);

        techs = new ArrayList<>();
        techFullNames = new ArrayList<>();

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
        edt_tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAlertTechs(v);
            }
        });

        btn_assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAlertAssign(v);
            }
        });
        getTechs();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void getTechs() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_GET_PRODUCTION_TECH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("RESPONSE", response);
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String msg = jsonObject.getString("message");

                            if (status.equals("1")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("details");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsn = jsonArray.getJSONObject(i);
                                    String username = jsn.getString("username");
                                    String fullName = jsn.getString("fullName"); // Assume this field is in the JSON response
                                    techs.add(username);
                                    techFullNames.add(fullName); // Add the full name to the list
                                }
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
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void assign() {
        final String username = edt_tech.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            Toast toast = Toast.makeText(getApplicationContext(), "Please select Production Tech", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 250);
            toast.show();
            return;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ASSIGN_PRODUCTION,
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
                params.put("username", username);
                params.put("product", product);
                params.put("quantity", quantity);
                params.put("productionDate", productionDate);
                Log.e("Params", "" + params);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void getAlertTechs(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Production Tech");

        // Create a string array of full names for the dialog
        String[] fullNamesArray = techFullNames.toArray(new String[0]);

        builder.setItems(fullNamesArray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // When an instructor is selected, set the username in the EditText
                edt_tech.setText(techs.get(which)); // Get the corresponding username
            }
        });

        builder.show();
    }
    public void getAlertAssign(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Assign");

        builder.setMessage("Are you sure you want to Assign Production?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //progressBar1.setVisibility(View.VISIBLE);
                assign();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }
}
