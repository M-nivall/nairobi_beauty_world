package com.example.Varsani.Staff.ProductionTech;

import static com.example.Varsani.utils.Urls.URL_ASSIGNED_PRODUCTION;
import static com.example.Varsani.utils.Urls.URL_PENDING_PRODUCTION;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;
import com.example.Varsani.Staff.ProductionTech.Adapters.ProductionAdapter;
import com.example.Varsani.Staff.Supervisor.Adapters.BatchAdapter;
import com.example.Varsani.Staff.Supervisor.Models.MaterialModel;
import com.example.Varsani.Staff.Supervisor.Models.ProductionBatchModel;
import com.example.Varsani.utils.SessionHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssignedProduction extends AppCompatActivity {
    private List<ProductionBatchModel> batchList;
    private ProductionAdapter adapter;
    private SessionHandler session;
    private UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigned_production);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        session=new SessionHandler(getApplicationContext());
        user=session.getUserDetails();

        // Initialize globally declared list and adapter
        batchList = new ArrayList<>();
        adapter = new ProductionAdapter(this, batchList);
        recyclerView.setAdapter(adapter);

        requests();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void requests() {
        StringRequest request = new StringRequest(Request.Method.POST, URL_ASSIGNED_PRODUCTION,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        String message = jsonObject.getString("message");

                        if (status.equals("1")) {
                            batchList.clear();
                            JSONArray detailsArray = jsonObject.getJSONArray("details");

                            for (int i = 0; i < detailsArray.length(); i++) {
                                JSONObject obj = detailsArray.getJSONObject(i);
                                String batch = obj.getString("batch_no");
                                String product = obj.getString("product");
                                String produceQty = obj.getString("produce_qty");
                                String productionDate = obj.getString("production_date");
                                String productionStatus = obj.getString("production_status");

                                JSONArray mats = obj.getJSONArray("materials");
                                List<MaterialModel> materials = new ArrayList<>();

                                for (int j = 0; j < mats.length(); j++) {
                                    JSONObject m = mats.getJSONObject(j);
                                    materials.add(new MaterialModel(
                                            m.getString("material_name"),
                                            m.getString("quantity"),
                                            m.getString("unit")
                                    ));
                                }

                                batchList.add(new ProductionBatchModel(batch, product, produceQty, productionDate, productionStatus, materials));
                            }

                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Error parsing data: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Network Error: " + error.toString(), Toast.LENGTH_LONG).show();
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userID", user.getClientID());
                Log.e("PARAMS", "" + params);
                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}