package com.example.Varsani.Clients;

import static com.example.Varsani.utils.Urls.URL_GET_MY_GRADES;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.example.Varsani.utils.SessionHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyGrades extends AppCompatActivity {
    private TextView txtTraineeID, txtTraineeName, txtAssignmentMarks, txtExamMarks,
            txtTheoryMarks, txtTotalScore;
    private SessionHandler session;
    private UserModel user;
    private LinearLayout layout_grades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_grades);

        txtTraineeID = findViewById(R.id.tvTraineeID);
        txtTraineeName = findViewById(R.id.tvTraineeName);
        txtAssignmentMarks = findViewById(R.id.tvAssignmentMarks);
        txtExamMarks = findViewById(R.id.tvExamMarks);
        txtTheoryMarks = findViewById(R.id.tvTheoryMarks);
        txtTotalScore = findViewById(R.id.tvTotalScore);

        layout_grades = findViewById(R.id.layout_grades);

        session=new SessionHandler(getApplicationContext());
        user=session.getUserDetails();

        fetchGrades();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void fetchGrades() {

        StringRequest request = new StringRequest(Request.Method.POST, URL_GET_MY_GRADES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //progressBar.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            // Check if the request was successful
                            if (jsonObject.getString("status").equals("1")) {
                                JSONArray responseData = jsonObject.getJSONArray("responseData");
                                JSONObject gradeData = responseData.getJSONObject(0);

                                // Display the fetched data
                                txtTraineeID.setText("Trainee ID: " + gradeData.getString("trainee_id"));
                                txtTraineeName.setText("Trainee Name: " + gradeData.getString("traineeNames"));
                                txtAssignmentMarks.setText("Assignment Marks: " +gradeData.getString("assignment_marks"));
                                txtExamMarks.setText("Exam Marks: " + gradeData.getString("exam_marks"));
                                txtTheoryMarks.setText("Practical Marks: " + gradeData.getString("theory_marks"));
                                txtTotalScore.setText("Total Score: " + gradeData.getString("totalScore") + " %");
                            } else {
                                Toast.makeText(MyGrades.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                layout_grades.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MyGrades.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //progressBar.setVisibility(View.GONE);
                        Toast.makeText(MyGrades.this, "Failed to fetch grades", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams()throws AuthFailureError {
                Map<String,String>params=new HashMap<>();
                params.put("userID",user.getClientID());
                Log.e("PARAMS",""+params);
                return params;
            }
        };;

        // Add request to the Volley request queue
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}