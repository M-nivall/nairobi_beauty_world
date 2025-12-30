package com.example.Varsani.Staff.Trainer;

import static com.example.Varsani.utils.Urls.URL_POST_VIDEO_TUTORIAL;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

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

public class PostVideoTutorial extends AppCompatActivity {
    private SessionHandler sessionHandler;
    private UserModel userModel;
    private EditText edtTitle, edtDetails;
    private ProgressBar progressBar;
    private Button btnSubmit;
    private ImageView imageView;
    private VideoView videoView;
    private Uri selectedVideoUri;
    //private MultipartBody.Part videoPart;
    private static final int PICK_VIDEO_REQUEST = 1;

    private String subjectId;

    private RequestQueue rQueue;
    private ArrayList<HashMap<String, String>> arraylist;
    String url = "https://www.google.com";
    Uri uri;
    String displayName=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_video_tutorial);

        imageView = findViewById(R.id.imgVideo);
        videoView = findViewById(R.id.videoPreview);
        edtDetails = findViewById(R.id.edtDetails);
        edtTitle = findViewById(R.id.adtTitle);
        progressBar = findViewById(R.id.progressBar);
        btnSubmit = findViewById(R.id.btnSubmit);

        sessionHandler=new SessionHandler(this);
        userModel=sessionHandler.getUserDetails();

        progressBar.setVisibility(View.GONE);

        Intent in=getIntent();
        subjectId=in.getStringExtra("subjectId");

        imageView.setOnClickListener(v -> pickVideoFromGallery());

        btnSubmit.setOnClickListener(v -> uploadPDF(displayName,uri));
    }
    private void pickVideoFromGallery() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Video"), PICK_VIDEO_REQUEST);
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

            if (requestCode == PICK_VIDEO_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
                selectedVideoUri = data.getData();
                videoView.setVideoURI(selectedVideoUri);
                videoView.start();
            }

            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = this.getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        Log.d("name  ",displayName);


                        if(!TextUtils.isEmpty(displayName)){
                            imageView.setVisibility(View.VISIBLE);
                        }
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
                Log.d("name  ",displayName);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);

    }
    private void uploadPDF(final String pdfname, Uri pdffile){

        InputStream iStream = null;

        progressBar.setVisibility(View.VISIBLE);
        btnSubmit.setVisibility(View.GONE);

        final String title= edtTitle.getText().toString().trim();
        final String details= edtDetails.getText().toString().trim();

        if(TextUtils.isEmpty(title)){
            Toast.makeText(this, "Please enter title", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            btnSubmit.setVisibility(View.VISIBLE);

            return;
        }
        if(TextUtils.isEmpty(details)){
            Toast.makeText(this, "Please enter details", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            btnSubmit.setVisibility(View.VISIBLE);
            return;
        }
        if(TextUtils.isEmpty(displayName)){
            Toast.makeText(this, "Please a video to upload", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            btnSubmit.setVisibility(View.VISIBLE);
            return;
        }
        try {

            iStream = getContentResolver().openInputStream(pdffile);
            final byte[] inputData = getBytes(iStream);

            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest
                    (Request.Method.POST, URL_POST_VIDEO_TUTORIAL,
                            response -> {
                                Log.d("dfdf",new String(response.data));
                                rQueue.getCache().clear();
                                try {
                                    JSONObject jsonObject = new JSONObject(new String(response.data));
                                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                                    jsonObject.toString().replace("\\\\","");
                                    finish();
//                                    Intent in=new Intent(getApplicationContext(), MyAdverts.class);
//                                    startActivity(in);

                                } catch (JSONException e) {
                                    e.printStackTrace();

                                    progressBar.setVisibility(View.GONE);
                                    btnSubmit.setVisibility(View.VISIBLE);

                                    Log.e("ERROR e",""+ e);
                                }
                            },
                            error -> {
                                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                                progressBar.setVisibility(View.GONE);
                                btnSubmit.setVisibility(View.VISIBLE);
                                Log.e("ERROR ",""+error.toString());
                            }) {

                /*
                 * If you want to add more parameters with the image
                 * you can do it here
                 * here we have only one parameter with the image
                 * which is tags
                 * */
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("userId", userModel.getClientID());
                    params.put("subjectId", subjectId);
                    params.put("title", title);
                    params.put("details", details);
                    Log.e("PARAMS ",""+params);
                    return params;
                }

                /*
                 *pass files using below method
                 * */
                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();

                    params.put("filename", new DataPart(pdfname ,inputData));
                    Log.e("FILE NAME ",""+params);
                    return params;
                }
            };


            volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            rQueue = Volley.newRequestQueue(PostVideoTutorial.this);
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