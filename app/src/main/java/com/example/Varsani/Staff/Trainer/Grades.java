package com.example.Varsani.Staff.Trainer;

import static com.example.Varsani.utils.Urls.URL_GET_GRADES;
import static com.example.Varsani.utils.Urls.URL_SUBMIT_TRAINEE_MARKS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.R;
import com.example.Varsani.Staff.Trainer.Adapters.AdapterGrades;
import com.example.Varsani.Staff.Trainer.Models.GradeModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grades extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<GradeModel> traineeList;
    private AdapterGrades adapter;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        recyclerView = findViewById(R.id.recyclerViewTrainees);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        traineeList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchTraineeData();

        buttonSubmit.setOnClickListener(v -> submitTheoryMarks());
    }
    private void fetchTraineeData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_GET_GRADES,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString("status").equals("1")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("responseData");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsn = jsonArray.getJSONObject(i);
                                String id = jsn.getString("trainee_id");
                                String name = jsn.getString("traineeNames");
                                String examMarks = jsn.getString("exam_marks");
                                String assignmentMarks = jsn.getString("assignment_marks");

                                GradeModel model = new GradeModel(id, name, examMarks,assignmentMarks);
                                traineeList.add(model);
                            }

                            adapter = new AdapterGrades(traineeList);
                            recyclerView.setAdapter(adapter);
                        } else {
                            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> error.printStackTrace()) {
            @Override
            protected Map<String, String> getParams() {
                // Params for the request, if needed
                return new HashMap<>();
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void submitTheoryMarks() {
        List<GradeModel> updatedTraineeList = adapter.getTraineeList();
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Prepare the array for ids and marks
        final StringBuilder traineeIds = new StringBuilder();
        final StringBuilder theoryMarks = new StringBuilder();

        for (int i = 0; i < updatedTraineeList.size(); i++) {
            GradeModel trainee = updatedTraineeList.get(i);
            String traineeId = trainee.getTraineeId();
            String theoryMark = trainee.getTheoryMarks();

            // Append ids and marks to the StringBuilder
            traineeIds.append(traineeId);
            theoryMarks.append(theoryMark);

            if (i < updatedTraineeList.size() - 1) {
                traineeIds.append(","); // Comma-separated
                theoryMarks.append(","); // Comma-separated
            }
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SUBMIT_TRAINEE_MARKS,
                response -> {
                    // Handle success response
                    Toast.makeText(this, "Marks submitted successfully!", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    // Handle error
                    Toast.makeText(this, "Failed to submit marks", Toast.LENGTH_SHORT).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("trainee_ids", traineeIds.toString());
                params.put("theory_marks", theoryMarks.toString());
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}