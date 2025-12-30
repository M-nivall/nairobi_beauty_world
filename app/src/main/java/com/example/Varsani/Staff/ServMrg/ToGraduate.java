package com.example.Varsani.Staff.ServMrg;

import static com.example.Varsani.utils.Urls.URL_COMPLETED_TRAINEES;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.R;
import com.example.Varsani.Staff.ServMrg.Adapter.AdapterCompletedTrainee;
import com.example.Varsani.Staff.ServMrg.Adapter.Models.CompletedTraineeModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ToGraduate extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdapterCompletedTrainee adapter;
    private List<CompletedTraineeModel> traineeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_graduate);

        recyclerView = findViewById(R.id.recyclerViewCompletedTrainees);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        traineeList = new ArrayList<>();
        fetchCompletedTrainees();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void fetchCompletedTrainees() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_COMPLETED_TRAINEES,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString("status").equals("1")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("responseData");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject traineeObj = jsonArray.getJSONObject(i);
                                String traineeId = traineeObj.getString("traineeId");
                                String traineeNames = traineeObj.getString("traineeNames");
                                String assignMarks = traineeObj.getString("assignMarks");
                                String examMarks = traineeObj.getString("examMarks");
                                String practicalMarks = traineeObj.getString("practicalMarks");
                                String finalScore = traineeObj.getString("finalScore");

                                CompletedTraineeModel trainee = new CompletedTraineeModel(traineeId, traineeNames, assignMarks, examMarks, practicalMarks, finalScore);
                                traineeList.add(trainee);
                            }

                            // Pass context correctly when initializing adapter
                            adapter = new AdapterCompletedTrainee(ToGraduate.this, traineeList);
                            recyclerView.setAdapter(adapter);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> error.printStackTrace());

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