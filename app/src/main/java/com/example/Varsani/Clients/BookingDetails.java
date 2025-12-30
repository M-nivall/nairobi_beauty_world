package com.example.Varsani.Clients;

import static com.example.Varsani.utils.Urls.URL_SUBMIT_RATING;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;
import com.example.Varsani.utils.SessionHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BookingDetails extends AppCompatActivity {
    private TextView txv_bookingID, txv_clientName, txv_course, txv_study_mode, txv_duration, txv_fee, txv_starting_date,
            txv_booking_date, txv_phoneNo, txv_booking_status, txv_amount, txv_payment_code, txv_payment_mode, txv_viewReceipt;
    private RatingBar ratingBar;
    private Button btnRating;
    private CardView card_View_rating;
    private  String bookingID;
    private SessionHandler session;
    private UserModel user;
    private String remarks,bookingState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        // Initialize TextViews
        txv_bookingID = findViewById(R.id.txv_bookingID);
        txv_clientName = findViewById(R.id.txv_clientName);
        txv_course = findViewById(R.id.txv_course);
        txv_study_mode = findViewById(R.id.txv_study_mode);
        txv_duration = findViewById(R.id.txv_duration);
        txv_fee = findViewById(R.id.txv_fee);
        txv_starting_date = findViewById(R.id.txv_starting_date);
        txv_booking_date = findViewById(R.id.txv_booking_date);
        txv_phoneNo = findViewById(R.id.txv_phoneNo);
        txv_booking_status = findViewById(R.id.txv_booking_status);
        txv_amount = findViewById(R.id.txv_amount);
        txv_payment_code = findViewById(R.id.txv_payment_code);
        txv_payment_mode = findViewById(R.id.txv_payment_mode);
        txv_viewReceipt = findViewById(R.id.txv_viewReceipt);
        ratingBar = findViewById(R.id.ratingBar);
        btnRating = findViewById(R.id.btnRating);
        card_View_rating = findViewById(R.id.card_View_rating);

        card_View_rating.setVisibility(View.GONE);

        session=new SessionHandler(getApplicationContext());
        user=session.getUserDetails();

        bookingID = getIntent().getStringExtra("bookingID");
        remarks = getIntent().getStringExtra("remarks");
        bookingState = getIntent().getStringExtra("status");

        // Set values from the Intent
        txv_bookingID.setText("Booking ID: " + getIntent().getStringExtra("bookingID"));
        txv_clientName.setText("Name: " + getIntent().getStringExtra("clientName"));
        txv_course.setText("Course: " + getIntent().getStringExtra("course"));
        txv_study_mode.setText("Study Mode: " + getIntent().getStringExtra("studyMode"));
        txv_duration.setText("Duration: " + getIntent().getStringExtra("duration"));
        txv_fee.setText("Fee: " + getIntent().getStringExtra("fee"));
        txv_starting_date.setText("Starting Date: " + getIntent().getStringExtra("startingDate"));
        txv_booking_date.setText("Booking Date: " + getIntent().getStringExtra("bookingDate"));
        txv_amount.setText("Amount: " + getIntent().getStringExtra("fee"));
        txv_phoneNo.setText("Phone No: " + getIntent().getStringExtra("contact"));
        txv_payment_code.setText("Payment Code: " + getIntent().getStringExtra("paymentCode"));
        txv_payment_mode.setText("Methode of Payment: " + getIntent().getStringExtra("paymentMode"));
        txv_booking_status.setText("Status: " + getIntent().getStringExtra("status"));

        if (remarks.equals("2") && bookingState.equals("Learning")) {
            txv_booking_status.setText("Completed");
            card_View_rating.setVisibility(View.VISIBLE);
        }


        txv_viewReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookingDetails.this, Receipt.class);

                intent.putExtra("bookingID", getIntent().getStringExtra("bookingID"));
                intent.putExtra("clientName", getIntent().getStringExtra("clientName"));
                intent.putExtra("course", getIntent().getStringExtra("course"));
                intent.putExtra("startingDate", getIntent().getStringExtra("startingDate"));
                intent.putExtra("paymentMode", getIntent().getStringExtra("paymentMode"));
                intent.putExtra("studyMode", getIntent().getStringExtra("studyMode"));
                intent.putExtra("bookingDate", getIntent().getStringExtra("bookingDate"));
                intent.putExtra("fee", getIntent().getStringExtra("fee"));
                intent.putExtra("contact", getIntent().getStringExtra("contact"));
                intent.putExtra("paymentCode", getIntent().getStringExtra("paymentCode"));

                startActivity(intent);
            }
        });
        btnRating.setOnClickListener(view -> {
            btnRating.setVisibility(View.GONE);
            requestCheckout();
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void requestCheckout() {

        final Float ratingValue = ratingBar.getRating();

        // Check if the rating is 0.0 (empty rating)
        if (ratingValue == 0.0f) {
            Toast.makeText(getApplicationContext(), "Please rate our service", Toast.LENGTH_SHORT).show();
            btnRating.setVisibility(View.VISIBLE);
            return; // Do not proceed with the request
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SUBMIT_RATING,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString("status").equals("success")) {
                            Toast.makeText(BookingDetails.this, "Thank you for choosing Nairobi Beauty World..", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(BookingDetails.this, "Something went.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(BookingDetails.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(BookingDetails.this, "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show()) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("bookingID", bookingID);
                params.put("trainerID",user.getClientID());
                params.put("rating", String.valueOf(ratingValue));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}