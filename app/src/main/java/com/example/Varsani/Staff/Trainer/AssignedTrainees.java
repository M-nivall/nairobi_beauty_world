package com.example.Varsani.Staff.Trainer;

import static com.example.Varsani.utils.Urls.URL_ASSIGNED_TRAINEES;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;
import com.example.Varsani.Staff.Trainer.Adapters.AdapterAssignedTrainees;
import com.example.Varsani.Staff.Trainer.Models.AssignedTraineeModel;
import com.example.Varsani.utils.SessionHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssignedTrainees extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<AssignedTraineeModel> list;
    private AdapterAssignedTrainees adapter;
    private SessionHandler session;
    private UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigned_trainees);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);

        session = new SessionHandler(this);
        user = session.getUserDetails();

        list = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);


        // Fetch the questions from the server
        getTrainees();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void getTrainees() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ASSIGNED_TRAINEES,
                response -> {
                    try {
                        Log.e("RESPONSE ", response);
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("responseData");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsn = jsonArray.getJSONObject(i);
                                String traineeId = jsn.getString("trainee_id");
                                String traineeName = jsn.getString("traineeNames");
                                String email = jsn.getString("email");
                                String phoneNo = jsn.getString("phone_no");
                                String startingDate = jsn.getString("starting_date");


                                AssignedTraineeModel model = new AssignedTraineeModel(traineeId, traineeName, email, phoneNo, startingDate);
                                list.add(model);
                            }
                            adapter = new AdapterAssignedTrainees(getApplicationContext(), list);
                            recyclerView.setAdapter(adapter);
                        } else if (status.equals("0")) {
                            String msg = jsonObject.getString("message");
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
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
}