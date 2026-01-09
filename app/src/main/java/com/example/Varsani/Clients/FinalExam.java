package com.example.Varsani.Clients;

import static com.example.Varsani.utils.Urls.URL_EXAM_STATUS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.R;

import org.json.JSONException;
import org.json.JSONObject;

public class FinalExam extends AppCompatActivity {
    private TextView quiz_info_text, clearance_info_text;
    Button start_quiz_button;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_exam);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        quiz_info_text = findViewById(R.id.quiz_info_text);
        clearance_info_text = findViewById(R.id.clearance_info_text);
        start_quiz_button = findViewById(R.id.start_quiz_button);

        requestQueue = Volley.newRequestQueue(this);

        // Check exam status
        checkExamStatus();

        // OnClickListener for the button to start the quiz
        start_quiz_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StartQuiz.class);
                startActivity(intent);
            }
        });
    }

    private void checkExamStatus() {
        String url = URL_EXAM_STATUS;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String examStatus = response.getString("exam_status");

                            if (examStatus.equals("unlocked")) {
                                start_quiz_button.setEnabled(true);
                                quiz_info_text.setText("Exam is unlocked. You can start.");
                            } else if (examStatus.equals("locked")) {
                                start_quiz_button.setEnabled(false);
                                quiz_info_text.setText("Exam is locked. You cannot start yet.");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            quiz_info_text.setText("Error parsing exam status");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        quiz_info_text.setText("Error fetching exam status");
                        error.printStackTrace();
                    }
                }
        );

        // Add the request to the RequestQueue
        requestQueue.add(jsonObjectRequest);
    }

}