package com.example.Varsani.Clients;

import androidx.appcompat.app.AppCompatActivity;
import androidx.print.PrintHelper;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Varsani.R;

public class Receipt extends AppCompatActivity {
    private TextView orderID, clientName, payment_mode, startingDate, course, studyMode, orderDate, fee,
            phoneNo, mpesaCode,receipt_number,receipt_date;
    private ImageView btn_printfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        // Initialize TextViews
        orderID = findViewById(R.id.receipt_orderID);
        clientName = findViewById(R.id.receipt_clientName);
        payment_mode = findViewById(R.id.payment_mode);
        startingDate = findViewById(R.id.receipt_startingDate);
        course = findViewById(R.id.receipt_course);
        studyMode = findViewById(R.id.receipt_studyMode);
        orderDate = findViewById(R.id.receipt_orderDate);
        fee = findViewById(R.id.receipt_fee);
        phoneNo = findViewById(R.id.receipt_phoneNo);
        mpesaCode = findViewById(R.id.receipt_mpesaCode);
        receipt_number = findViewById(R.id.receipt_number);
        receipt_date = findViewById(R.id.receipt_date);
        btn_printfile = findViewById(R.id.btn_printfile);

        // Retrieve and display values from the Intent
        orderID.setText("Booking No: " + getIntent().getStringExtra("bookingID"));
        clientName.setText("Name: " + getIntent().getStringExtra("clientName"));
        payment_mode.setText("Payment Mode: " + getIntent().getStringExtra("paymentMode"));
        startingDate.setText("Starting Date: " + getIntent().getStringExtra("startingDate"));
        course.setText("Course: " + getIntent().getStringExtra("course"));
        studyMode.setText("Mode of Study: " + getIntent().getStringExtra("studyMode"));
        orderDate.setText("Booking Date: " + getIntent().getStringExtra("bookingDate"));
        fee.setText("Amount: " + getIntent().getStringExtra("fee"));
        phoneNo.setText("Phone No: " + getIntent().getStringExtra("contact"));
        mpesaCode.setText("Payment Code" + getIntent().getStringExtra("paymentCode"));
        receipt_number.setText("Receipt No: RD202411-N0" +getIntent().getStringExtra("bookingID"));
        receipt_date.setText("Date: " + getIntent().getStringExtra("bookingDate"));

        btn_printfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                print();
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
    private void print(){
        btn_printfile.setVisibility(View.GONE);

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

        btn_printfile.setVisibility(View.VISIBLE);
    }
    private void showToast(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 250);
        toast.show();
    }
    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}