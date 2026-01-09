package com.example.Varsani.Clients;

import static com.example.Varsani.utils.Urls.URL_BOOKING_HISTORY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.Clients.Adapters.AdapterBookingHistory;
import com.example.Varsani.Clients.Models.BookingHistoryModel;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;
import com.example.Varsani.utils.SessionHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingHistory extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterBookingHistory adapter;
    private List<BookingHistoryModel> orderList;
    private SessionHandler session;
    private UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        session=new SessionHandler(getApplicationContext());
        user=session.getUserDetails();

        orderList = new ArrayList<>();
        adapter = new AdapterBookingHistory(this, orderList);
        recyclerView.setAdapter(adapter);

        fetchBookingHistory();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void fetchBookingHistory() {
        String url = URL_BOOKING_HISTORY;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("RESPONSE", response);
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");

                            if (status.equals("1")) {
                                JSONArray detailsArray = jsonObject.getJSONArray("details");

                                for (int i = 0; i < detailsArray.length(); i++) {
                                    JSONObject orderObject = detailsArray.getJSONObject(i);

                                    String bookingID = orderObject.getString("bookingID");
                                    String clientName = orderObject.getString("clientName");
                                    String phoneNo = orderObject.getString("phoneNo");
                                    String course = orderObject.getString("course");
                                    String studyMode = orderObject.getString("studyMode");
                                    String fee = orderObject.getString("fee");
                                    String duration = orderObject.getString("duration");
                                    String startingDate = orderObject.getString("startingDate");
                                    String paymentMethod = orderObject.getString("paymentMethod");
                                    String paymentCode = orderObject.getString("paymentCode");
                                    String bookingDate = orderObject.getString("bookingDate");
                                    String bookingStatus = orderObject.getString("bookingStatus");
                                    String remarks = orderObject.getString("remarks");

                                    BookingHistoryModel order = new BookingHistoryModel(bookingID, clientName, phoneNo, course, studyMode, fee, duration,
                                            startingDate, paymentMethod, paymentCode, bookingDate, bookingStatus, remarks);
                                    orderList.add(order);
                                }

                                adapter.notifyDataSetChanged();
                            } else {
                                String msg = jsonObject.getString("message");
                                Toast toast = Toast.makeText(BookingHistory.this, msg, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP, 0, 250);
                                toast.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast toast = Toast.makeText(BookingHistory.this, e.toString(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0, 250);
                            toast.show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast toast = Toast.makeText(BookingHistory.this, error.toString(), Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP, 0, 250);
                        toast.show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("clientID", user.getClientID());
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