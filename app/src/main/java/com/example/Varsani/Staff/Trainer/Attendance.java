package com.example.Varsani.Staff.Trainer;

import static com.example.Varsani.utils.Urls.URL_ACTIVATE_ATTENDANCE;
import static com.example.Varsani.utils.Urls.URL_ATTENDANCE_SESSIONS;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;
import com.example.Varsani.Staff.Trainer.Adapters.AttendanceAdapter;
import com.example.Varsani.Staff.Trainer.Models.AttendanceModel;
import com.example.Varsani.utils.SessionHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Attendance extends AppCompatActivity {
    private Button btnSetActivateAttendance;
    private RecyclerView recyclerView;
    private AttendanceAdapter adapter;
    private List<AttendanceModel> attendanceList;
    private SessionHandler session;
    private UserModel user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);


        // Initialize Views
        btnSetActivateAttendance = findViewById(R.id.btnSetActivateAttendance);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(layoutManager);

        attendanceList = new ArrayList<>();

        session=new SessionHandler(getApplicationContext());
        user=session.getUserDetails();

        // Click listener for the button to activate attendance
        btnSetActivateAttendance.setOnClickListener(view -> showTopicInputDialog());

        fetchAttendanceData();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showTopicInputDialog() {
        // Create an AlertDialog for input
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Topic Taught");

        // Create EditText for input
        final EditText input = new EditText(this);
        input.setHint("E.g., Core Techniques and Applications");
        builder.setView(input);

        // Set positive button
        builder.setPositiveButton("OK", (dialog, which) -> {
            String topic = input.getText().toString().trim();
            if (!topic.isEmpty()) {
                // Display Toast message
                Toast.makeText(this, "Topic Set: " + topic, Toast.LENGTH_SHORT).show();

                // Enable attendance process
                activateAttendance(topic);
            } else {
                Toast.makeText(this, "Please enter a topic to continue", Toast.LENGTH_SHORT).show();
            }
        });

        // Set negative button
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        // Show the dialog
        builder.show();
    }

    private void activateAttendance(String topic) {

        // Check if the topic is empty
        if (topic.isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), "Please enter a topic", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 250);
            toast.show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ACTIVATE_ATTENDANCE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Log the response
                            Log.e("RESPONSE", response);
                            // Parse the JSON response
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String msg = jsonObject.getString("message");

                            // Display the response message
                            Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0, 250);
                            toast.show();

                            // If status is 1, close the activity
                            if (status.equals("1")) {
                                finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast toast = Toast.makeText(getApplicationContext(), "Error: " + e.toString(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0, 250);
                            toast.show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast toast = Toast.makeText(getApplicationContext(), "Volley Error: " + error.toString(), Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP, 0, 250);
                        toast.show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Create parameters to send to backend
                Map<String, String> params = new HashMap<>();
                params.put("topic", topic);
                params.put("instructorID",user.getClientID());
                Log.e("Params", params.toString());
                return params;
            }
        };

        // Add the request to the Volley request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    private void fetchAttendanceData() {
        String url = URL_ATTENDANCE_SESSIONS;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equals("1")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("responseData");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String id = object.getString("Id");
                                    String topic = object.getString("topic");
                                    String sessionDate = object.getString("sessionDate");

                                    attendanceList.add(new AttendanceModel(id, topic, sessionDate));
                                }

                                adapter = new AttendanceAdapter(getApplicationContext(), attendanceList);
                                recyclerView.setAdapter(adapter);
                            } else {
                                Toast.makeText(Attendance.this, "No data found", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
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