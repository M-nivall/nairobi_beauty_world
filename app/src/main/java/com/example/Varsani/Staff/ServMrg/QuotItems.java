package com.example.Varsani.Staff.ServMrg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.Staff.Models.ClientItemsModal;
import com.example.Varsani.R;
import com.example.Varsani.Staff.Adapters.AdapterClientItems;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.Varsani.utils.Urls.URL_ASSIGN_TRAINER;
import static com.example.Varsani.utils.Urls.URL_GET_CLIENT_ITEMS;
import static com.example.Varsani.utils.Urls.URL_GET_DRIVERS;
import static com.example.Varsani.utils.Urls.URL_GET_TRAINERS;
import static com.example.Varsani.utils.Urls.URL_QUOTATION_ITEMS;
import static com.example.Varsani.utils.Urls.URL_SHIP_ORDER;

public class QuotItems extends AppCompatActivity {
    private ProgressBar progressBar,progressBar1;
    private TextView txv_course,txv_study_mode,txv_duration,txv_paymentCode,txv_date,
            txv_starting_date,txv_fee,txv_bookingID,txv_status,txv_name,txv_phone,
            txv_paymentMode,txv_amount;
    private List<ClientItemsModal> list;
    private AdapterClientItems adapterClientItems;
    private RelativeLayout layout_bottom;
    private Button btn_ship;
    private ArrayList<String> drivers;
    private ArrayList<String> driverFullNames;
    private EditText edt_trainer;

    private String bookingID,clientID,clientName,course,fee,paymentMethod,paymentCode,bookingDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quot_items);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layout_bottom=findViewById(R.id.layout_bottom);
        progressBar=findViewById(R.id.progressBar);
        progressBar1=findViewById(R.id.progressBar1);
        txv_fee=findViewById(R.id.txv_fee);
        txv_bookingID=findViewById(R.id.txv_bookingID);
        txv_paymentMode=findViewById(R.id.txv_paymentMode);
        txv_course=findViewById(R.id.txv_course);
        txv_amount=findViewById(R.id.txv_amount);
        txv_duration=findViewById(R.id.txv_duration);
        txv_study_mode=findViewById(R.id.txv_study_mode);
        txv_starting_date=findViewById(R.id.txv_starting_date);
        btn_ship=findViewById(R.id.btn_submit);
        edt_trainer=findViewById(R.id.edt_trainer);
        txv_paymentCode=findViewById(R.id.txv_paymentCode);
        txv_date=findViewById(R.id.txv_date);
        txv_status=findViewById(R.id.txv_status);
        txv_name=findViewById(R.id.txv_name);
        txv_phone=findViewById(R.id.txv_phone);

        progressBar1.setVisibility(View.GONE);
        edt_trainer.setFocusable(false);
        //edt_driver.setEnabled(false);

        drivers = new ArrayList<>();
        driverFullNames = new ArrayList<>();

        Intent intent=getIntent();
        bookingID=intent.getStringExtra("bookingID");
        clientID=intent.getStringExtra("clientID");
        clientName=intent.getStringExtra("clientName");
        course=intent.getStringExtra("course");
        String studyMode=intent.getStringExtra("studyMode");
        fee=intent.getStringExtra("fee");
        String duration=intent.getStringExtra("duration");
        String startingDate=intent.getStringExtra("startingDate");
        paymentMethod=intent.getStringExtra("paymentMethod");
        paymentCode=intent.getStringExtra("paymentCode");
        bookingDate=intent.getStringExtra("bookingDate");
        String phoneNo=intent.getStringExtra("phoneNo");
        String bookingStatus=intent.getStringExtra("bookingStatus");

        txv_starting_date.setText("Starting Date: " + startingDate);
        txv_duration.setText("Duration: " + duration );
        txv_course.setText("Course: " + course );
        txv_bookingID.setText("Booking ID: " + bookingID );
        txv_paymentMode.setText("Payment Mode: " + paymentMethod );
        txv_fee.setText("Fee: " + fee );
        txv_study_mode.setText("Study Mode: " + studyMode );
        txv_amount.setText("Amount: " + fee);
        txv_paymentCode.setText("Payment Code: " + paymentCode);
        txv_date.setText("Date: " + bookingDate);
        txv_status.setText("Status: " + bookingStatus);
        txv_phone.setText("Phone No: " + phoneNo);


        list=new ArrayList<>();

        edt_trainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAlertTrainers(v);
            }
        });



        btn_ship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAlertAssign(v);
            }
        });

        getTrainers();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void assign(){

        final String username=edt_trainer.getText().toString().trim();

        if(TextUtils.isEmpty(username)){
            Toast toast= Toast.makeText(getApplicationContext(), "Please select Trainer", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP,0,250);
            toast.show();
            return;
        }
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_ASSIGN_TRAINER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.e("RESPONSE",response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            String msg=jsonObject.getString("message");
                            if (status.equals("1")){

                                Toast toast= Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP,0,250);
                                toast.show();
                                finish();
                            }else{

                                Toast toast= Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP,0,250);
                                toast.show();
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            Toast toast= Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP,0,250);
                            toast.show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast toast= Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,250);
                toast.show();
            }
        }){
            @Override
            protected Map<String,String>getParams()throws AuthFailureError{
                Map<String,String> params=new HashMap<>();
                params.put("bookingID",bookingID);
                params.put("clientID",clientID);
                params.put("username",username);
                Log.e("PARAMS",""+params);
                return params;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void getTrainers() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_GET_TRAINERS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("RESPONSE", response);
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String msg = jsonObject.getString("message");

                            if (status.equals("1")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("details");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsn = jsonArray.getJSONObject(i);
                                    String username = jsn.getString("username");
                                    String fullName = jsn.getString("fullName"); // Assume this field is in the JSON response
                                    drivers.add(username);
                                    driverFullNames.add(fullName); // Add the full name to the list
                                }
                            } else {
                                Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP, 0, 250);
                                toast.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast toast = Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0, 250);
                            toast.show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast toast = Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 250);
                toast.show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void getAlertTrainers(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Trainer");

        // Create a string array of full names for the dialog
        String[] fullNamesArray = driverFullNames.toArray(new String[0]);

        builder.setItems(fullNamesArray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // When an instructor is selected, set the username in the EditText
                edt_trainer.setText(drivers.get(which)); // Get the corresponding username
            }
        });

        builder.show();
    }

    public void getAlertAssign(View v){
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Assign Selected Trainer?");
        final String[] array = drivers.toArray(new String[drivers.size()]);
        builder.setNegativeButton("Cancel",null);
        builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                assign();

                return;
            }
        });
        builder.show();

    }
}
