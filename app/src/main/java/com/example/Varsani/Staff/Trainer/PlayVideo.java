package com.example.Varsani.Staff.Trainer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.Varsani.R;
import com.example.Varsani.utils.Urls;

public class PlayVideo extends AppCompatActivity {
    private TextView txvTitle, txvDatePosted,txvDetails,txvSubjectName;
    private ProgressBar progressBar;

    private VideoView videoView;
    private int position = 0;
    private ProgressDialog progressDialog;
    private MediaController mediaControls;
    private Urls urls;
    private String videoName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        txvTitle=findViewById(R.id.txvTitle);
        txvDetails=findViewById(R.id.txvDetails);
        txvSubjectName=findViewById(R.id.txvSubjectName);
        txvDatePosted=findViewById(R.id.txvDatePosted);
        videoView=findViewById(R.id.videoView);

        Intent in=getIntent();
        videoName=in.getStringExtra("videoLink");
        txvTitle.setText(in.getStringExtra("title"));
        txvSubjectName.setText(in.getStringExtra("subjectName"));
        txvDetails.setText(in.getStringExtra("details"));
        txvDatePosted.setText("Posted On: "+in.getStringExtra("datePosted"));

        if (mediaControls == null) {
            mediaControls = new MediaController(this);
        }

        // Find your VideoView in your video_main.xml layout
        videoView = (VideoView) findViewById(R.id.videoView);

        // Create a progressbar

        progressDialog = new ProgressDialog(this);
        // Set progressbar title
        progressDialog.setTitle("Please wait");
        // Set progressbar message
        progressDialog.setMessage("Loading...");

        progressDialog.setCancelable(true);
        // Show progressbar
        progressDialog.show();

        try {
            Uri video = Uri.parse(urls.ROOT_URL_VIDEOS+videoName);
            Log.e("VIDEO URL ",urls.ROOT_URL_VIDEOS+videoName);
            videoView.setVideoURI(video);
            videoView.setMediaController(mediaControls);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        videoView.requestFocus();
        // Close the progress bar and play the video
        videoView.setOnPreparedListener(mp -> {
            progressDialog.dismiss();
            videoView.seekTo(position);
            if (position == 0) {
                videoView.start();
            } else {
                videoView.pause();
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
}