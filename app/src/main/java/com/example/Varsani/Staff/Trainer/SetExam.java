package com.example.Varsani.Staff.Trainer;

import static com.example.Varsani.utils.Urls.URL_EXAM_STATUS;
import static com.example.Varsani.utils.Urls.URL_LOCK_EXAM;
import static com.example.Varsani.utils.Urls.URL_UNLOCK_EXAM;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SetExam extends AppCompatActivity {

    private Button btnSetExam, btnUnlockExam, btnLockExam;
    private SessionHandler session;
    private UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_exam);

        btnSetExam = findViewById(R.id.btn_set_exam);
        btnUnlockExam = findViewById(R.id.btn_unlock_exam);
        btnLockExam = findViewById(R.id.btn_lock_exam);

        session = new SessionHandler(getApplicationContext());
        user = session.getUserDetails();

        // Check exam status from the backend
        checkExamStatus();

        btnSetExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetExam.this, Exams.class);
                startActivity(intent);
            }
        });

        btnUnlockExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnUnlockExam.setVisibility(View.GONE);
                alertUnlock();
            }
        });

        btnLockExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLockExam.setVisibility(View.GONE);
                alertLock();
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
    private void checkExamStatus() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_EXAM_STATUS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("exam_status");

                            if (status.equals("unlocked")) {
                                btnUnlockExam.setVisibility(View.GONE);  // Already unlocked
                                btnLockExam.setVisibility(View.VISIBLE);
                            } else if (status.equals("locked")) {
                                btnLockExam.setVisibility(View.GONE);  // Already locked
                                btnUnlockExam.setVisibility(View.VISIBLE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(SetExam.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(SetExam.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("instructorId", user.getClientID());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void unlockExam() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UNLOCK_EXAM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String msg = jsonObject.getString("message");

                            Toast toast = Toast.makeText(SetExam.this, msg, Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0, 250);
                            toast.show();

                            if (status.equals("1")) {
                                finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(SetExam.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(SetExam.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("instructorId", user.getClientID());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void lockExam() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOCK_EXAM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String msg = jsonObject.getString("message");

                            Toast toast = Toast.makeText(SetExam.this, msg, Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0, 250);
                            toast.show();

                            if (status.equals("1")) {
                                finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(SetExam.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(SetExam.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("instructorId", user.getClientID());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void alertUnlock() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage("Do you want to unlock the exam?");
        alertDialog.setCancelable(false);
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                unlockExam();
            }
        });
        alertDialog.show();
    }
    public void alertLock() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage("Do you want to lock the exam?");
        alertDialog.setCancelable(false);
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                lockExam();
            }
        });
        alertDialog.show();
    }
}