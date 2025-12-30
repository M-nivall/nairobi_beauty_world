package com.example.Varsani.Clients;

import static com.example.Varsani.utils.Urls.URL_CLASS_ATTENDANCE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.Clients.Adapters.AdapterClassAttendance;
import com.example.Varsani.Clients.Models.ClassAttendanceModel;
import com.example.Varsani.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyAttendance extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdapterClassAttendance adapter;
    private List<ClassAttendanceModel> attendanceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_attendance);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        attendanceList = new ArrayList<>();

        fetchAttendanceData();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchAttendanceData() {
        String url = URL_CLASS_ATTENDANCE;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        if (response.getInt("status") == 1) {
                            JSONArray dataArray = response.getJSONArray("responseData");
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject item = dataArray.getJSONObject(i);
                                String id = item.getString("Id");
                                String topic = item.getString("topic");
                                String instructorID = item.getString("instructorID");
                                String instructorName = item.getString("instructorName");
                                String sessionDate = item.getString("sessionDate");

                                ClassAttendanceModel model = new ClassAttendanceModel(id, topic, instructorID, instructorName, sessionDate);
                                attendanceList.add(model);
                            }
                            adapter = new AdapterClassAttendance(this, attendanceList);
                            recyclerView.setAdapter(adapter);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Parse Error", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Volley Error", Toast.LENGTH_SHORT).show()
        );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}