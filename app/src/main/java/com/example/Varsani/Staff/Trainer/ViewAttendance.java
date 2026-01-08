package com.example.Varsani.Staff.Trainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.R;
import com.example.Varsani.Staff.Trainer.Adapters.ViewAttendanceAdapter;
import com.example.Varsani.Staff.Trainer.Models.AttendanceTraineeModel;
import com.example.Varsani.utils.Urls;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewAttendance extends AppCompatActivity {

    private TextView txtSessionId, txtTopic, txtSessionDate;
    private RecyclerView recyclerView;
    private ViewAttendanceAdapter adapter;
    private List<AttendanceTraineeModel> traineeList;
    private String sessionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);

        txtSessionId = findViewById(R.id.txtSessionId);
        txtTopic = findViewById(R.id.txtTopic);
        txtSessionDate = findViewById(R.id.txtSessionDate);
        recyclerView = findViewById(R.id.recyclerView);

        // Get passed values from intent
        sessionId = getIntent().getStringExtra("sessionId");
        String topic = getIntent().getStringExtra("topic");
        String sessionDate = getIntent().getStringExtra("sessionDate");

        // Set values in TextViews
        txtSessionId.setText("Session ID: " + sessionId);
        txtTopic.setText("Topic: " + topic);
        txtSessionDate.setText("Date: " + sessionDate);

        // Initialize trainee list and set up RecyclerView
        traineeList = new ArrayList<>();
        adapter = new ViewAttendanceAdapter(this, traineeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Fetch trainee data from backend
        fetchTraineeData();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchTraineeData() {
        String url = Urls.URL_VIEW_ATTENDANCE_LIST;

        // Create a StringRequest for a POST request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        Log.e("RESPONSE", response);
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        String message = jsonObject.getString("message");

                        if (status.equals("1")) {
                            JSONArray dataArray = jsonObject.getJSONArray("responseData");

                            // Clear the list before adding new data
                            traineeList.clear();

                            // Parse each trainee in the responseData array
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject item = dataArray.getJSONObject(i);
                                String traineeId = item.getString("traineeId");
                                String traineeName = item.getString("traineeName");

                                // Add the trainee to the list
                                traineeList.add(new AttendanceTraineeModel(traineeId, traineeName));
                            }

                            // Notify the adapter of data changes
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast toast = Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP, 0, 250);
                        toast.show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast toast = Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 250);
                    toast.show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                // Adding parameters for the POST request
                params.put("sessionId", sessionId);
                Log.e("Params", "" + params);
                return params;
            }
        };

        // Create a request queue and add the request
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