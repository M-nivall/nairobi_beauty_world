package com.example.Varsani.Clients;

import static com.example.Varsani.utils.Urls.URL_CERTIFICATE_DETAILS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.print.PrintHelper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GetCertificate extends AppCompatActivity {
    private SessionHandler session;
    private UserModel user;

    private TextView tv_fullName;
    private TextView  tv_certificateNo,txv_issue_date ;
    private Button btnPrint;
    private CardView cardDCertificate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_certificate);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setSubtitle("Service Requests");

        // Initialize session and user
        session = new SessionHandler(getApplicationContext());
        user = session.getUserDetails();

        // Find the TextViews from the layout
        tv_fullName = findViewById(R.id.tv_fullName);
        tv_certificateNo = findViewById(R.id.tv_certificateNo);
        txv_issue_date =findViewById(R.id.txv_issue_date);
        btnPrint = findViewById(R.id.btn_print);

        cardDCertificate = findViewById(R.id.cardDCertificate);

        // Fetch and display the license details
        getLicenceDetails();

        // Setup print button action
        btnPrint.setOnClickListener(v -> printCertificate());
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void getLicenceDetails() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CERTIFICATE_DETAILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("RESPONSE", response);
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String msg = jsonObject.getString("message");

                            if (status.equals("1")) {
                                JSONObject jsn = jsonObject.getJSONArray("details").getJSONObject(0);

                                // Populate the TextViews with data

                                tv_fullName.setText(jsn.getString("fullName"));
                                tv_certificateNo.setText("Serial No: " + jsn.getString("certificateNo"));
                                txv_issue_date.setText(jsn.getString("dateOfIssue"));

                            } else {
                                // Set CardView visibility to GONE if status is not 1
                                cardDCertificate.setVisibility(View.GONE);
                                btnPrint.setVisibility(View.GONE);
                                showToast(msg);
                            }

                        } catch (Exception e) {
                            showToast("Error: " + e.getMessage());
                            Log.e("ERROR", e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showToast("Error: " + error.toString());
                Log.e("ERROR", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userID", user.getClientID());
                Log.e("PARAMS", "" + params);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    private void showToast(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 250);
        toast.show();
    }

    private void printCertificate(){
        btnPrint.setVisibility(View.GONE);

        View view = getWindow().getDecorView().findViewById(android.R.id.content);
        view.setDrawingCacheEnabled(true);
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),View. MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        PrintHelper photoPrinter = new PrintHelper(this); // Assume that 'this' is your activity
        photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        photoPrinter.printBitmap("print", bitmap);

        btnPrint.setVisibility(View.VISIBLE);
    }
    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}