package com.example.Varsani.Clients;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.example.Varsani.MainActivity;
import com.example.Varsani.Staff.Store_mrg.RequestSupplier;
import com.example.Varsani.utils.SessionHandler;
import com.example.Varsani.utils.Urls;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CheckOut2 extends AppCompatActivity {
    private SessionHandler session;
    private UserModel user;

    private RelativeLayout layout_bottom;
    private TextView txv_name,txv_phoneNo,txv_email;
    private TextView txv_course, txv_duration, txv_fee;
    private EditText et_startingDate,edt_payment_code;
    private ProgressBar progressBar;
    private Button btn_checkout;
    private RadioGroup pay_group,rgStudyMode;
    private RadioButton selectedPayment,selectedStudyMode;



    private String courseID, course, fee, duration;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    DatePickerDialog datePicker;



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out2);

        getSupportActionBar().setTitle("Fill  Quotation Request ");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layout_bottom=findViewById(R.id.layout_bottom);
        progressBar=findViewById(R.id.progressBar);


        btn_checkout=findViewById(R.id.btn_order);
        txv_email=findViewById(R.id.txv_email);
        txv_name=findViewById(R.id.txv_name);
        txv_phoneNo=findViewById(R.id.txv_phoneNo);

        txv_course = findViewById(R.id.txv_course);
        txv_duration = findViewById(R.id.txv_duration);
        txv_fee = findViewById(R.id.txv_fee);

        et_startingDate = findViewById(R.id.et_startingDate);
        edt_payment_code = findViewById(R.id.edt_payment_code);

        edt_payment_code.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        date = dateFormat.format(calendar.getTime());
        //et_startingDate.setText(date);

        final Calendar calendar2 = Calendar.getInstance();
        final int day = calendar2.get(Calendar.DAY_OF_MONTH);
        final int year = calendar2.get(Calendar.YEAR);
        final int month = calendar2.get(Calendar.MONTH);

        datePicker = new DatePickerDialog(CheckOut2.this);


        session=new SessionHandler(getApplicationContext());
        user=session.getUserDetails();

        progressBar.setVisibility(View.GONE);

        Intent intent = getIntent();
        courseID = intent.getStringExtra("courseID");
        course = intent.getStringExtra("course");
        fee = intent.getStringExtra("fee");
        duration = intent.getStringExtra("duration");

        txv_name.setText("Name: " + user.getFirstname()+" "+user.getLastname());
        txv_email.setText("Email: " + user.getEmail());
        txv_phoneNo.setText("Phone No: " + user.getPhoneNo());

        txv_course.setText("Course: " + course );
        txv_duration.setText("Duration: " + duration );
        txv_fee.setText("Fee: " + fee );


        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertOrderNow(v);

            }
        });

        et_startingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker = new DatePickerDialog(CheckOut2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        et_startingDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMinDate(calendar.getTimeInMillis());

                // show the dialog
                datePicker.show();
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

    public void alertOrderNow(final  View v){
        AlertDialog.Builder  builder=new AlertDialog.Builder(v.getContext());
        builder.setTitle("Are you sure the entered details are correct!")
                .setNegativeButton("No",null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        orderNow();
                        dialog.dismiss();
                    }
                }).create().show();
    }

    public void orderNow() {

        progressBar.setVisibility(View.VISIBLE);
        btn_checkout.setVisibility(View.GONE);
        final String startingDate=et_startingDate.getText().toString().trim();
        final String paymentMethod = getSelectedPaymentMethod();
        final String studyMode = getSelectedStudyMode();
        final String payment_code=edt_payment_code.getText().toString().trim();

        if(TextUtils.isEmpty(studyMode)){
            Toast.makeText(getApplicationContext(),"Please select mode of study",Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            btn_checkout.setVisibility(View.VISIBLE);
            return;
        } if(TextUtils.isEmpty(paymentMethod)){
            Toast.makeText(getApplicationContext(),"Please select Payment Method",Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            btn_checkout.setVisibility(View.VISIBLE);
            return;
        }
        if(TextUtils.isEmpty(startingDate)){
            Toast.makeText(getApplicationContext(),"Please enter starting Date",Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            btn_checkout.setVisibility(View.VISIBLE);
            return;
        }
        if(TextUtils.isEmpty(payment_code)){
            Toast.makeText(getApplicationContext(),"Please enter payment code",Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            btn_checkout.setVisibility(View.VISIBLE);
            return;
        }
        if(payment_code.length()>10 ||payment_code.length()<10){
            Toast.makeText(getApplicationContext(), "Payment code  should contain 10 digits", Toast.LENGTH_SHORT).show();
            btn_checkout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;
        }
        if(!payment_code.matches("^(?=.*[A-Z])(?=.*[0-9])[A-Z0-9]+$")){
            Toast.makeText(getApplicationContext(), "Payment code should have  characters and digits",
                    Toast.LENGTH_LONG).show();
            btn_checkout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;
        }



        StringRequest stringRequest=new StringRequest(Request.Method.POST, Urls.URL_SUBMIT_REQUEST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            String msg=jsonObject.getString("message");

                            if(status.equals("1")){
                                progressBar.setVisibility(View.GONE);
                                AlertDialog.Builder  builder=new AlertDialog.Builder(CheckOut2.this);
                                builder.setTitle("SUCCESS ");
                                builder.setMessage(msg)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                                startActivity(i);
                                                dialog.dismiss();
                                            }
                                        });
                                builder.setCancelable(false)
                                        .create().show();
                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
                            }else if(status.equals("0")){
                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                btn_checkout.setVisibility(View.VISIBLE);

                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            Log.e("Error",e.toString());
                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.e("Error",error.toString());
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params= new HashMap<>();
                params.put("clientID",user.getClientID());
                params.put("studyMode",studyMode);
                params.put("startingDate",startingDate);
                params.put("paymentMethod",paymentMethod);
                params.put("payment_code",payment_code);
                params.put("courseID",courseID);
                params.put("course",course);
                params.put("fee",fee);
                params.put("duration",duration);
                Log.e("values",""+params);
                return params;
            }

        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    private String getSelectedPaymentMethod() {
        pay_group = findViewById(R.id.pay_group);
        int selectedId = pay_group.getCheckedRadioButtonId();

        if (selectedId == -1) {
            // No option selected
            return null;
        }

        selectedPayment = findViewById(selectedId);
        return selectedPayment.getText().toString();
    }
    private String getSelectedStudyMode() {
        rgStudyMode = findViewById(R.id.rgStudyMode);
        int selectedId = rgStudyMode.getCheckedRadioButtonId();

        if (selectedId == -1) {
            // No option selected
            return null;
        }

        selectedStudyMode = findViewById(selectedId);
        return selectedStudyMode.getText().toString();
    }
}
