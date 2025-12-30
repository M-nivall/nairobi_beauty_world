package com.example.Varsani.Staff.Store_mrg;

import static com.example.Varsani.utils.Urls.URL_GET_STOCK_LIST;
import static com.example.Varsani.utils.Urls.URL_SUPPLIER;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.Staff.Adapters.AdapterGetStock;
import com.example.Varsani.Staff.Models.GetStockModel;
import com.example.Varsani.Staff.Store_mrg.Adapter.AdapterStock;
import com.example.Varsani.Staff.Store_mrg.Model.StockModel;
import com.example.Varsani.utils.Urls;
import com.example.Varsani.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewStock extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdapterStock adapterStock;
    private List<StockModel> model;
    private List<String> supplierList; // List to store suppliers
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        category = getIntent().getStringExtra("category");

        // Initialize RecyclerView and List
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        model = new ArrayList<>();
        supplierList = new ArrayList<>();

        // Initialize adapter with supplier list
        adapterStock = new AdapterStock(this, model, supplierList);
        recyclerView.setAdapter(adapterStock);

        // Fetch materials & supplier details
        fetchMaterialDetails(category);
        fetchSupplierList();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void fetchMaterialDetails(String category) {
        String url = URL_GET_STOCK_LIST;

        // Create a StringRequest with POST method
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            if (status.equals("1")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("responseData");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject roomObject = jsonArray.getJSONObject(i);
                                    String stockID = roomObject.getString("stockID");
                                    String productName = roomObject.getString("productName");
                                    String price = roomObject.getString("price");
                                    String stock = roomObject.getString("stock");

                                    model.add(new StockModel(stockID, productName, price, stock));
                                }

                                adapterStock.notifyDataSetChanged();
                            } else {
                                String msg = jsonObject.getString("message");
                                Toast.makeText(ViewStock.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ViewStock.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ViewStock.this, "Failed to fetch materials", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void fetchSupplierList(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SUPPLIER,
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
                                    supplierList.add(username);  // Correct the variable here
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast toast = Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0, 250);
                            toast.show();
                        }
                    }
                },
                new Response.ErrorListener() {
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

    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}
