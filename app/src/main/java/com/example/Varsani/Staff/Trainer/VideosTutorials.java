package com.example.Varsani.Staff.Trainer;

import static com.example.Varsani.utils.Urls.URL_UNIT_VIDEO_TUTORIAL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;
import com.example.Varsani.Staff.Trainer.Adapters.AdapterVideoTutorials;
import com.example.Varsani.Staff.Trainer.Models.VideoTutorialsModel;
import com.example.Varsani.utils.SessionHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VideosTutorials extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<VideoTutorialsModel> list;
    private AdapterVideoTutorials adapter;
    private SessionHandler session;
    private UserModel user;
    private TextView txv_status;
    private ProgressBar progressBar;
    private String subjectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos_tutorials);

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        txv_status = findViewById(R.id.txv_status);

        Intent in=getIntent();
        subjectId=in.getStringExtra("subjectId");


        txv_status.setVisibility(View.GONE);


        session = new SessionHandler(this);
        user = session.getUserDetails();

        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);

        getMyPackages();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void getMyPackages() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UNIT_VIDEO_TUTORIAL,
                response -> {
                    try {

                        Log.e("RESPONSE ", response);
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("responseData");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsn = jsonArray.getJSONObject(i);
                                String id = jsn.getString("id");
                                String subjectId = jsn.getString("subjectId");
                                String title = jsn.getString("title");
                                String subjectName = jsn.getString("subjectName");
                                String videoLink = jsn.getString("videoLink");
                                String datePosted = jsn.getString("datePosted");
                                String details = jsn.getString("details");

                                VideoTutorialsModel model = new VideoTutorialsModel( id,  subjectId,subjectName,
                                        title,  videoLink,  datePosted, details);
                                list.add(model);
                            }
                            adapter = new AdapterVideoTutorials(getApplicationContext(), list);
                            recyclerView.setAdapter(adapter);
                            progressBar.setVisibility(View.GONE);

                        } else if (status.equals("0")) {
                            String msg = jsonObject.getString("message");
                            progressBar.setVisibility(View.GONE);
                            txv_status.setVisibility(View.VISIBLE);

                            txv_status.setText(msg);
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }

                }, error -> {
            error.printStackTrace();
            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("subjectId", subjectId);
                params.put("userId", user.getClientID());

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}