package com.example.Varsani.Clients;

import static com.example.Varsani.utils.Urls.URL_EXAM_QUIZ;
import static com.example.Varsani.utils.Urls.URL_SUBMIT_SCORE;

import androidx.appcompat.app.AppCompatActivity;
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
import com.example.Varsani.Clients.Adapters.AdapterExamQuiz;
import com.example.Varsani.Clients.Models.ExamQuizModel;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;
import com.example.Varsani.utils.SessionHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StartQuiz extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<ExamQuizModel> list;
    private AdapterExamQuiz adapter;
    private SessionHandler session;
    private UserModel user;
    private ProgressBar progressBar;
    private Button btnSubmitQuestions;
    private static final int QUESTION_SCORE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_quiz);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        btnSubmitQuestions = findViewById(R.id.btnSubmitQuestions);

        session = new SessionHandler(this);
        user = session.getUserDetails();

        list = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        getQuestions();

        btnSubmitQuestions.setOnClickListener(v -> {
            int totalMarks = calculateTotalMarks();
            submitScore(totalMarks);
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void getQuestions() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_EXAM_QUIZ,
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
                                String correct_answer = jsn.getString("correct_answer");
                                String multiple_1 = jsn.getString("multiple_1");
                                String multiple_2 = jsn.getString("multiple_2");

                                ExamQuizModel model = new ExamQuizModel(quizId, question, correct_answer, multiple_1, multiple_2);
                                list.add(model);
                            }
                            adapter = new AdapterExamQuiz(getApplicationContext(), list);
                            recyclerView.setAdapter(adapter);
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
    private int calculateTotalMarks() {
        int totalMarks = 0;
        Map<Integer, String> selectedAnswers = adapter.getSelectedAnswers();
        for (ExamQuizModel quiz : list) {
            String selectedAnswer = selectedAnswers.get(quiz.getQuizId());
            if (selectedAnswer != null && selectedAnswer.equals(quiz.getCorrect_answer())) {
                totalMarks += QUESTION_SCORE;
            }
        }
        return totalMarks;
    }
    private void submitScore(int totalMarks) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SUBMIT_SCORE,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");

                        if (status.equals("1")) {
                            // Show success message
                            Toast.makeText(getApplicationContext(), "Score submitted successfully!", Toast.LENGTH_SHORT).show();

                            // Clear the activity
                            finish();
                        } else if (status.equals("0")) {
                            // Handle unsuccessful response
                            String msg = jsonObject.getString("message");
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Response parsing error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Network error: " + error.toString(), Toast.LENGTH_SHORT).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("traineeId", user.getClientID());
                params.put("totalMarks", String.valueOf(totalMarks));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}