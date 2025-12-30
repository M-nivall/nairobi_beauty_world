package com.example.Varsani.Staff.Trainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.Varsani.R;
import com.example.Varsani.utils.Urls;

public class ReadAssignment extends AppCompatActivity {
    private TextView txvDatePosted,txvTitle,
            txvDetails;
    private ImageView img_pdf;
    private String files_url = Urls.ROOT_URL_ASSIGNMENTS;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_assignment);

        txvDatePosted=findViewById(R.id.txvDatePosted);
        txvTitle=findViewById(R.id.txvTitle);
        //txvDetails=findViewById(R.id.txvDetails);
        img_pdf=findViewById(R.id.img_pdf);

        Intent in=getIntent();
        txvDatePosted.setText("Posted on: "+ in.getStringExtra("datePosted"));
        txvTitle.setText(in.getStringExtra("title"));
        url=files_url+in.getStringExtra("pdfLink");
        Log.e("URL FILE"," "+url);

        img_pdf.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}