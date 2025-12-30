package com.example.Varsani.Staff.Trainer;

import static com.example.Varsani.utils.Urls.URL_POST_PDF_TUTORIAL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;
import com.example.Varsani.VolleyMultipartRequest;
import com.example.Varsani.utils.SessionHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostPdfTutorial extends AppCompatActivity {
    private EditText title_edt, details_edt;
    private Button submit_btn;
    private ProgressBar progressBar;

    private SessionHandler session;
    private UserModel user;

    private String subjectId;


    private Button btn;
    private ImageView img_pdf;
    private RequestQueue rQueue;
    private ArrayList<HashMap<String, String>> arraylist;
    String url = "https://www.google.com";
    Uri uri;
    String displayName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_pdf_tutorial);

        progressBar = findViewById(R.id.progressBar);
        title_edt = findViewById(R.id.title_edt);
        details_edt = findViewById(R.id.details_edt);
        submit_btn = findViewById(R.id.submit_btn);
        img_pdf = findViewById(R.id.img_pdf);

        progressBar.setVisibility(View.GONE);
        img_pdf.setVisibility(View.GONE);


        session = new SessionHandler(this);
        user = session.getUserDetails();


        Intent in = getIntent();
        subjectId = in.getStringExtra("subjectId");


        submit_btn.setOnClickListener(v -> {
            uploadPDF(displayName, uri);

        });


        btn = findViewById(R.id.btn);

        btn.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("application/pdf");
            startActivityForResult(intent, 1);
        });

        submit_btn.setOnClickListener(v -> uploadPDF(displayName, uri));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // Get the Uri of the selected file
            uri = data.getData();
            String uriString = uri.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();
            displayName = null;

            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = this.getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        Log.d("name  ", displayName);


                        if (!TextUtils.isEmpty(displayName)) {
                            img_pdf.setVisibility(View.VISIBLE);
                        }
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
                Log.d("name  ", displayName);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);

    }
    private void uploadPDF(final String pdfname, Uri pdffile) {

        InputStream iStream = null;

        progressBar.setVisibility(View.VISIBLE);
        btn.setVisibility(View.GONE);
        submit_btn.setVisibility(View.GONE);

        final String details = details_edt.getText().toString().trim();
        final String title = title_edt.getText().toString().trim();

        if (TextUtils.isEmpty(title)) {
            Toast.makeText(this, "Please enter a title", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            btn.setVisibility(View.VISIBLE);
            submit_btn.setVisibility(View.VISIBLE);

            return;
        }
        if (TextUtils.isEmpty(details)) {
            Toast.makeText(this, "Pdf details required", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            btn.setVisibility(View.VISIBLE);
            submit_btn.setVisibility(View.VISIBLE);
            return;
        }

        try {

            iStream = getContentResolver().openInputStream(pdffile);
            final byte[] inputData = getBytes(iStream);

            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URL_POST_PDF_TUTORIAL,
                    response -> {
                        Log.d("ressssssoo", new String(response.data));
                        rQueue.getCache().clear();
                        try {
                            JSONObject jsonObject = new JSONObject(new String(response.data));
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                            jsonObject.toString().replace("\\\\", "");
                            finish();
//                                Intent in=new Intent(getApplicationContext(),ReportsPendingApproval.class);
//                                startActivity(in);

                        } catch (JSONException e) {
                            e.printStackTrace();

                            progressBar.setVisibility(View.GONE);
                            btn.setVisibility(View.VISIBLE);
                            submit_btn.setVisibility(View.VISIBLE);

                            Log.e("E ", "" + e);
                        }
                    },
                    error -> {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                        progressBar.setVisibility(View.GONE);
                        btn.setVisibility(View.VISIBLE);
                        submit_btn.setVisibility(View.VISIBLE);
                        Log.e("ERROR ", "" + error);
                    }) {

                /*
                 * If you want to add more parameters with the pdf
                 * you can do it here
                 * here we have only one parameter with the image
                 * which is tags
                 * */
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("userId", user.getClientID());
                    params.put("subjectId", subjectId);
                    params.put("title", title);
                    params.put("details", details);
                    Log.e("PARAMS ", "" + params);
                    return params;
                }

                /*
                 *pass files using below method
                 * */
                @Override
                protected Map<String, VolleyMultipartRequest.DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();

                    params.put("filename", new DataPart(pdfname, inputData));
                    Log.e("FILE NAME ", "" + params);
                    return params;
                }
            };


            volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            rQueue = Volley.newRequestQueue(PostPdfTutorial.this);
            rQueue.add(volleyMultipartRequest);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

}