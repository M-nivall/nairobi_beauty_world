package com.example.Varsani.Clients;

import static com.example.Varsani.utils.Urls.URL_SIGN_ATTENDANCE;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
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

public class SignAttendance extends AppCompatActivity {
    private String sessionId;
    private Button btn_sign_attendance;
    private SessionHandler session;
    private UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_attendance);

        btn_sign_attendance = findViewById(R.id.btn_sign_attendance);

        session=new SessionHandler(getApplicationContext());
        user=session.getUserDetails();

        // Get sessionId from Intent
        sessionId = getIntent().getStringExtra("sessionId");

        // Set click listener for the button
        btn_sign_attendance.setOnClickListener(v -> alertAssign());
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    // Method to show the alert dialog before signing attendance
    public void alertAssign() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage("Sign Attendance?");
        alertDialog.setCancelable(false);
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Call signAttendance method after user clicks "Yes"
                signAttendance();
            }
        });
        alertDialog.show();
    }

    // Method to sign attendance and send sessionId to backend
    public void signAttendance() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SIGN_ATTENDANCE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String msg = jsonObject.getString("message");

                            // Display the response message as a toast
                            Toast toast = Toast.makeText(SignAttendance.this, msg, Toast.LENGTH_SHORT);
                            toast.show();

                            if (status.equals("1")) {
                                // Clear the activity and display a success message
                                Toast.makeText(SignAttendance.this, "Attendance signed successfully!", Toast.LENGTH_SHORT).show();
                                finish(); // This will close the current activity
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(SignAttendance.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(SignAttendance.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("sessionId", sessionId);
                params.put("userID",user.getClientID());
                return params;
            }
        };

        // Add the request to the request queue
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