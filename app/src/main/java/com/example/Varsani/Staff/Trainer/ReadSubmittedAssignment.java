package com.example.Varsani.Staff.Trainer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.R;
import com.example.Varsani.utils.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ReadSubmittedAssignment extends AppCompatActivity {
    private TextView txv_trainee_name, txv_traineeId;
    private Button btn_enter_marks;
    private ImageView img_pdf;
    private String files_url = Urls.ROOT_URL_SUBMITTED_ASSIGNMENTS;
    private String url, traineeID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_submitted_assignment);

        // Initialize views
        txv_trainee_name = findViewById(R.id.txv_trainee_name);
        txv_traineeId = findViewById(R.id.txv_traineeId);
        img_pdf = findViewById(R.id.img_pdf);
        btn_enter_marks = findViewById(R.id.btn_enter_marks);

        // Get data from intent
        Intent in = getIntent();
        traineeID = in.getStringExtra("id");
        txv_trainee_name.setText("Trainee Name: " + in.getStringExtra("names"));
        txv_traineeId.setText("Trainee ID" + in.getStringExtra("id"));
        url = files_url + in.getStringExtra("pdfLink");
        Log.e("URL FILE", " " + url);

        // Open PDF in browser
        img_pdf.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        });

        // Set OnClickListener for the Enter Marks button
        btn_enter_marks.setOnClickListener(v -> showMarksDialog());
    }
    // Method to show AlertDialog for entering marks
    private void showMarksDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Assignment Marks");

        // Create EditText input field
        final EditText input = new EditText(this);
        input.setHint("Enter marks here");
        input.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        // Set Positive Button
        builder.setPositiveButton("Submit", (dialog, which) -> {
            String marks = input.getText().toString().trim();
            if (!marks.isEmpty()) {
                submitMarks(traineeID, marks);
            } else {
                Toast.makeText(ReadSubmittedAssignment.this, "Please enter valid marks", Toast.LENGTH_SHORT).show();
            }
        });

        // Set Negative Button
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        // Show the dialog
        builder.show();
    }
    // Method to submit marks to the backend
    private void submitMarks(String traineeID, String marks) {
        String url = Urls.URL_SUBMIT_ASSIGNMENT_MARKS;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            String message = jsonObject.getString("message");

                            if (status == 1) {
                                Toast.makeText(ReadSubmittedAssignment.this, "Marks submitted successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ReadSubmittedAssignment.this, "Failed to submit marks: " + message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(ReadSubmittedAssignment.this, "JSON Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage = (error.getMessage() != null) ? error.getMessage() : "Network Error";
                        Toast.makeText(ReadSubmittedAssignment.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("traineeID", traineeID);
                params.put("marks", marks);
                return params;
            }
        };

        // Add request to Volley queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}