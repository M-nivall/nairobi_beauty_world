package com.example.Varsani.Staff.ServMrg;

import static com.example.Varsani.utils.Urls.URL_COMPLETED_HISTORY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.R;
import com.example.Varsani.Staff.ServMrg.Adapter.AdapterCompletedHistory;
import com.example.Varsani.Staff.ServMrg.Adapter.Models.CompletedHistoryModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CompletedHistory extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdapterCompletedHistory adapter;
    private List<CompletedHistoryModel> completedHistoryList;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_history);

        recyclerView = findViewById(R.id.recyclerViewCompletedHistory);
        progressBar = findViewById(R.id.progressBar);
        completedHistoryList = new ArrayList<>();
        adapter = new AdapterCompletedHistory(this, completedHistoryList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        fetchCompletedHistory();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void fetchCompletedHistory() {
        progressBar.setVisibility(View.VISIBLE);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_COMPLETED_HISTORY, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            if (response.getString("status").equals("1")) {
                                JSONArray jsonArray = response.getJSONArray("responseData");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);


                                    String bookingID = jsonObject.getString("bookingID");
                                    String clientID = jsonObject.getString("clientID");
                                    String course = jsonObject.getString("course");
                                    String studyMode = jsonObject.getString("studyMode");
                                    String fee = jsonObject.getString("fee");
                                    String duration = jsonObject.getString("duration");
                                    String traineeNames = jsonObject.getString("traineeNames");
                                    String phoneNo = jsonObject.getString("phoneNo");
                                    String startingDate = jsonObject.getString("startingDate");
                                    String paymentMethod = jsonObject.getString("paymentMethod");
                                    String paymentCode = jsonObject.getString("paymentCode");
                                    String bookingDate = jsonObject.getString("bookingDate");
                                    String rating = jsonObject.getString("rating");
                                    String examMarks = jsonObject.getString("examMarks");
                                    String practicalMarks = jsonObject.getString("practicalMarks");
                                    String assignmentMarks = jsonObject.getString("assignmentMarks");
                                    String finalScore = jsonObject.getString("finalScore");
                                    String certificateNo = jsonObject.getString("certificateNo");

                                    CompletedHistoryModel model = new CompletedHistoryModel(
                                            bookingID,clientID,course,studyMode,fee,duration,traineeNames,startingDate,
                                            paymentMethod,paymentCode,bookingDate,rating,examMarks,practicalMarks,
                                            assignmentMarks,finalScore,certificateNo,phoneNo
                                    );
                                    completedHistoryList.add(model);
                                }
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(CompletedHistory.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(CompletedHistory.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

}