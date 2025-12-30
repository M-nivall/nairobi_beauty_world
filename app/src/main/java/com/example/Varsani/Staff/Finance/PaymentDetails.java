package com.example.Varsani.Staff.Finance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.example.Varsani.Staff.Adapters.AdapterQuotItems;
import com.example.Varsani.Staff.Models.ClientItemsModal;
import com.example.Varsani.R;
import com.example.Varsani.Staff.Adapters.AdapterClientItems;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.Varsani.utils.Urls.URL_APPROVE_SERV_PAYMENTS;
import static com.example.Varsani.utils.Urls.URL_GET_APPROVE_ORDERS;
import static com.example.Varsani.utils.Urls.URL_GET_CLIENT_ITEMS;
import static com.example.Varsani.utils.Urls.URL_QUOTATION_ITEMS;

public class PaymentDetails extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView txv_bookingID,txv_course,txv_fee,txv_paymentMode,txv_duration,txv_amount,
            txv_paymentCode,txv_date,txv_status,txv_name,txv_phone,txv_study_mode,txv_starting_date;
    private List<ClientItemsModal>list;
    private AdapterQuotItems adapterQuotItems;
    private RelativeLayout layout_bottom;
    private Button btn_appprove,btn_reject;

    private String bookingID,clientID,clientName,course,fee,paymentMethod,paymentCode,bookingDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        getSupportActionBar().setSubtitle("Payment Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layout_bottom=findViewById(R.id.layout_bottom);
        progressBar=findViewById(R.id.progressBar);
        txv_name=findViewById(R.id.txv_name);
        txv_phone=findViewById(R.id.txv_phone);
        txv_bookingID=findViewById(R.id.txv_bookingID);
        txv_paymentCode=findViewById(R.id.txv_paymentCode);
        txv_fee=findViewById(R.id.txv_fee);
        txv_status=findViewById(R.id.txv_status);
        txv_date=findViewById(R.id.txv_date);
        txv_course=findViewById(R.id.txv_course);
        txv_study_mode=findViewById(R.id.txv_study_mode);
        txv_duration=findViewById(R.id.txv_duration);
        txv_starting_date=findViewById(R.id.txv_starting_date);
        txv_paymentMode=findViewById(R.id.txv_paymentMode);
        txv_amount=findViewById(R.id.txv_amount);
        btn_appprove=findViewById(R.id.btn_submit);

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

        txv_paymentMode.setText("Payment Mode :" + paymentMethod);
        txv_paymentCode.setText("Payment Code " + paymentCode);
        txv_fee.setText("Fee: " + fee );
        txv_status.setText("Status:  " + bookingStatus);
        txv_date.setText("Date: " + bookingDate);
        txv_bookingID.setText("#ID: " + bookingID );
        txv_phone.setText("Phone No: " + phoneNo );
        txv_name.setText("Name: " + clientName );
        txv_course.setText("Course : " + course );
        txv_study_mode.setText("Study Mode: " + studyMode);
        txv_duration.setText("Duration: " + duration);
        txv_starting_date.setText("Starting Date: " + startingDate);
        txv_amount.setText("Amount: " + fee);

        list=new ArrayList<>();

        btn_appprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertApprove();
            }
        });
//         btn_reject.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void approveOrder(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_APPROVE_SERV_PAYMENTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.e("RESPONSE",response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            String msg=jsonObject.getString("message");
                            if (status.equals("1")){

                                Toast toast= Toast.makeText(PaymentDetails.this, msg, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP,0,250);
                                toast.show();
                                finish();
                            }else{

                                Toast toast= Toast.makeText(PaymentDetails.this, msg, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP,0,250);
                                toast.show();
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            Toast toast= Toast.makeText(PaymentDetails.this, e.toString(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP,0,250);
                            toast.show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast toast= Toast.makeText(PaymentDetails.this, error.toString(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,250);
                toast.show();
            }
        }){
            @Override
            protected Map<String,String>getParams()throws AuthFailureError{
                Map<String,String> params=new HashMap<>();
                params.put("bookingID",bookingID);
                params.put("clientID",clientID);
                params.put("clientName",clientName);
                params.put("course",course);
                params.put("fee",fee);
                params.put("paymentMethod",paymentMethod);
                params.put("paymentCode",paymentCode);
                params.put("bookingDate",bookingDate);
                Log.e("PARAMS",""+params);
                return params;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void alertApprove(){
        android.app.AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage("Approve This Payment");
        alertDialog.setCancelable(false);
        alertDialog.setButton2("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

                return;
            }
        });
        alertDialog.setButton("Approve ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                approveOrder();
                return;
            }
        });
        alertDialog.show();
    }
}
