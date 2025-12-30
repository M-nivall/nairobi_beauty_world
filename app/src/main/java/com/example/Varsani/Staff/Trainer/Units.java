package com.example.Varsani.Staff.Trainer;

import static com.example.Varsani.utils.Urls.URL_UNITS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;
import com.example.Varsani.Staff.Trainer.Adapters.AdapterUnits;
import com.example.Varsani.Staff.Trainer.Models.UnitsModel;
import com.example.Varsani.utils.SessionHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Units extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<UnitsModel> list;
    private AdapterUnits adapter;
    private SessionHandler session;
    private UserModel user;
    private TextView txv_status;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_units);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setSubtitle("Student");

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        txv_status = findViewById(R.id.txv_status);


        txv_status.setVisibility(View.GONE);


        session = new SessionHandler(this);
        user = session.getUserDetails();

        list = new ArrayList<>();


        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);

        getMyPackages();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void getMyPackages() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UNITS,
                response -> {
                    try {

                        Log.e("RESPONSE ", response);
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("responseData");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsn = jsonArray.getJSONObject(i);
                                String unitId = jsn.getString("unitId");
                                String unitName = jsn.getString("unitName");
                                String unitStatus = jsn.getString("unitStatus");

                                UnitsModel model = new UnitsModel( unitId,  unitName,  unitStatus);
                                list.add(model);
                            }
                            adapter = new AdapterUnits(getApplicationContext(), list);
                            recyclerView.setAdapter(adapter);
                            progressBar.setVisibility(View.GONE);

                        } else if (status.equals("0")) {
                            String msg = jsonObject.getString("message");
                            progressBar.setVisibility(View.GONE);
                            txv_status.setVisibility(View.VISIBLE);

                            txv_status.setText(msg);
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }

                }, error -> {
            error.printStackTrace();
            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("trainerID", user.getClientID());

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}