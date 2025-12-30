package com.example.Varsani.Staff.Supervisor;

import static com.example.Varsani.utils.Urls.URL_PENDING_PRODUCTION;
import static com.example.Varsani.utils.Urls.URL_TRACK_PRODUCTION;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.R;
import com.example.Varsani.Staff.Supervisor.Adapters.BatchAdapter;
import com.example.Varsani.Staff.Supervisor.Adapters.TrackProductionAdapter;
import com.example.Varsani.Staff.Supervisor.Models.MaterialModel;
import com.example.Varsani.Staff.Supervisor.Models.ProductionBatchModel;
import com.example.Varsani.Staff.Supervisor.Models.TrackProductionModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TrackProduction extends AppCompatActivity {
    private List<TrackProductionModel> batchList;
    private TrackProductionAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_production);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize globally declared list and adapter
        batchList = new ArrayList<>();
        adapter = new TrackProductionAdapter(this, batchList);
        recyclerView.setAdapter(adapter);

        production();
    }
    public void production() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_TRACK_PRODUCTION, null,
                response -> {
                    try {
                        String status = response.getString("status");
                        String message = response.getString("message");

                        if (status.equals("1")) {
                            batchList.clear();
                            JSONArray detailsArray = response.getJSONArray("details");

                            for (int i = 0; i < detailsArray.length(); i++) {
                                JSONObject obj = detailsArray.getJSONObject(i);
                                String batch = obj.getString("batch_no");
                                String product = obj.getString("product");
                                String produceQty = obj.getString("produce_qty");
                                String productionDate = obj.getString("production_date");
                                String productionStatus = obj.getString("production_status");
                                String techName = obj.getString("techName");
                                String techNo = obj.getString("techNo");
                                String producedQuantity = obj.getString("produced_quantity");

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

                                batchList.add(new TrackProductionModel(batch, product, produceQty, productionDate, productionStatus, techName, techNo, producedQuantity, materials));
                            }

                            adapter.notifyDataSetChanged();
                        } else {
                            // No data found
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
                });

        Volley.newRequestQueue(this).add(request);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}