package com.example.Varsani.Staff.ServMrg;

import static com.example.Varsani.utils.Urls.URL_APPROVE_LICENCE;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ApproveCertificate extends AppCompatActivity {
    private TextView textViewTraineeId, textViewTraineeNames,textViewAssignMarks;
    private TextView textViewExamMarks, textViewPracticalMarks, textViewFinalScore;
    private Button buttonApproveLicence;

    private String traineeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_certificate);

        // Initialize TextViews for Trainee Details
        textViewTraineeId = findViewById(R.id.textViewTraineeId);
        textViewTraineeNames = findViewById(R.id.textViewTraineeNames);


        // Initialize TextViews for Grades
        textViewAssignMarks = findViewById(R.id.textViewAssignMarks);
        textViewExamMarks = findViewById(R.id.textViewExamMarks);
        textViewPracticalMarks = findViewById(R.id.textViewPracticalMarks);
        textViewFinalScore = findViewById(R.id.textViewFinalScore);

        // Initialize EditText for Licence Number and Button
        buttonApproveLicence = findViewById(R.id.buttonApproveLicence);

        // Get the passed data from Intent
        traineeId = getIntent().getStringExtra("traineeId");
        String traineeNames = getIntent().getStringExtra("traineeNames");
        String dateOfBirth = getIntent().getStringExtra("dateOfBirth");
        String nationalID = getIntent().getStringExtra("nationalID");
        String gender = getIntent().getStringExtra("gender");
        String county = getIntent().getStringExtra("county");
        String address = getIntent().getStringExtra("address");
        String assignMarks = getIntent().getStringExtra("assignMarks");
        String examMarks = getIntent().getStringExtra("examMarks");
        String practicalMarks = getIntent().getStringExtra("practicalMarks");
        String finalScore = getIntent().getStringExtra("finalScore");

        // Set the data to the respective TextViews in Trainee Details section
        textViewTraineeId.setText("Trainee ID: " + traineeId);
        textViewTraineeNames.setText("Name: " + traineeNames);

        // Set the data to the respective TextViews in Grades section
        textViewAssignMarks.setText("Assignment Marks: "+ assignMarks);
        textViewExamMarks.setText("Exam Marks: " + examMarks);
        textViewPracticalMarks.setText("Practical Marks: " + practicalMarks);
        textViewFinalScore.setText("Final Score: " + finalScore);

        buttonApproveLicence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new androidx.appcompat.app.AlertDialog.Builder(ApproveCertificate.this)
                        .setTitle("Confirm Approval")
                        .setMessage("Are you sure you want to approve the certificate for this trainee?")
                        .setPositiveButton("Yes", (dialog, which) -> approveLicence(traineeId))
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void approveLicence(String traineeId) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_APPROVE_LICENCE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Parse the JSON response
                            JSONObject jsonResponse = new JSONObject(response);
                            String status = jsonResponse.getString("status");
                            String message = jsonResponse.getString("message");

                            // Check if the response is successful
                            if (status.equals("success")) {
                                Toast.makeText(ApproveCertificate.this, message, Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(ApproveCertificate.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(ApproveCertificate.this, "Error parsing response", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ApproveCertificate.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("trainee_id", traineeId);
                return params;
            }
        };

        // Add the request to the RequestQueue
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