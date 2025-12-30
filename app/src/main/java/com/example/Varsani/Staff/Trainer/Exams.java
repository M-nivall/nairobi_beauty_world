package com.example.Varsani.Staff.Trainer;

import static com.example.Varsani.utils.Urls.URL_QUESTIONS_LIST;
import static com.example.Varsani.utils.Urls.URL_SUBMIT_SELECTED_QUESTIONS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;
import com.example.Varsani.Staff.Trainer.Adapters.AdapterQuizList;
import com.example.Varsani.Staff.Trainer.Models.QuestionsListModel;
import com.example.Varsani.utils.SessionHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Exams extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<QuestionsListModel> list;
    private AdapterQuizList adapter;
    private SessionHandler session;
    private UserModel user;
    private ProgressBar progressBar;
    private Button btnSubmitQuestions;
    private List<Integer> selectedQuestions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exams);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        btnSubmitQuestions = findViewById(R.id.btnSubmitQuestions); // Find the submit button

        session = new SessionHandler(this);
        user = session.getUserDetails();

        list = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);

        // Initialize the adapter with the list and the selectedQuestions list
        adapter = new AdapterQuizList(this, list, selectedQuestions);
        recyclerView.setAdapter(adapter);

        // Fetch the questions from the server
        getQuestions();

        // Set the onClickListener for the submit button
        btnSubmitQuestions.setOnClickListener(v -> submitSelectedQuestions());
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void getQuestions() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_QUESTIONS_LIST,
                response -> {
                    try {
                        Log.e("RESPONSE ", response);
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("responseData");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsn = jsonArray.getJSONObject(i);
                                Integer quizId = Integer.valueOf(jsn.getString("id"));
                                String question = jsn.getString("question");
                                String multiple1 = jsn.getString("multiple_1");
                                String multiple2 = jsn.getString("multiple_2");
                                String correctAnswer = jsn.getString("correct_answer");

                                QuestionsListModel model = new QuestionsListModel(quizId, question, multiple1, multiple2, correctAnswer);
                                list.add(model);
                            }
                            // Notify the adapter that data has changed
                            adapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                        } else if (status.equals("0")) {
                            String msg = jsonObject.getString("message");
                            progressBar.setVisibility(View.GONE);
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
                params.put("instructorId", user.getClientID());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    // Method to handle submit button click
    public void submitSelectedQuestions() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SUBMIT_SELECTED_QUESTIONS,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        String message = jsonObject.getString("message");

                        if (status.equals("1")) { // Check if submission was successful
                            Toast.makeText(getApplicationContext(), "Questions submitted successfully!", Toast.LENGTH_SHORT).show();
                            finish(); // Close the current activity
                        } else {
                            // Handle unsuccessful response
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Response parsing error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
            error.printStackTrace();
            Toast.makeText(getApplicationContext(), "Network error: " + error.toString(), Toast.LENGTH_SHORT).show();
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                // Send selected questions with their multiple options and correct answers
                JSONArray questionsArray = new JSONArray();
                for (Integer selectedId : selectedQuestions) {
                    for (QuestionsListModel question : list) {
                        if (question.getQuizId().equals(selectedId)) {
                            try {
                                JSONObject questionObj = new JSONObject();
                                questionObj.put("quiz_id", question.getQuizId());
                                questionObj.put("multiple_1", question.getMultiple_1());
                                questionObj.put("multiple_2", question.getMultiple_2());
                                questionObj.put("correct_answer", question.getCorrectAnswer());
                                questionsArray.put(questionObj);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                params.put("selectedQuestions", questionsArray.toString());
                params.put("instructorId", user.getClientID());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}