package com.example.Varsani.Staff.ServMrg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.Varsani.R;

public class ViewCompletedTrainees extends AppCompatActivity {

    private TextView tvBookingID, tvClientID, tvDuration, tvCourse, tvStudyMode,
            tvTraineeNames, tvContact, tvExamMarks, tvPracticalMarks, tvFee,
            tvAssignmentMarks, tvFinalScore, tvCertNo, tvGuestRating;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_completed_trainees);

        // Initialize TextViews
        tvBookingID = findViewById(R.id.tvBookingID);
        tvClientID = findViewById(R.id.tvClientID);
        tvDuration = findViewById(R.id.tvDuration);
        tvCourse = findViewById(R.id.tvCourse);
        tvFee = findViewById(R.id.tvFee);
        tvStudyMode = findViewById(R.id.tvStudyMode);
        tvGuestRating = findViewById(R.id.tvGuestRating);
        ratingBar = findViewById(R.id.ratingBar);
        tvTraineeNames = findViewById(R.id.tvTraineeNames);
        tvContact = findViewById(R.id.tvContact);
        tvExamMarks = findViewById(R.id.tvExamMarks);
        tvPracticalMarks = findViewById(R.id.tvPracticalMarks);
        tvAssignmentMarks = findViewById(R.id.tvAssignmentMarks);
        tvFinalScore = findViewById(R.id.tvFinalScore);
        tvCertNo = findViewById(R.id.tvCertNo);

        // Get data from Intent
        tvBookingID.setText("Booking ID: " + getIntent().getStringExtra("bookingID"));
        tvClientID.setText("Trainee ID: " + getIntent().getStringExtra("clientID"));
        tvDuration.setText("Duration: " + getIntent().getStringExtra("duration"));
        tvCourse.setText("Course: " + getIntent().getStringExtra("course"));
        tvStudyMode.setText("Mode of Study: " + getIntent().getStringExtra("studyMode"));
        tvFee.setText("Fee: " + getIntent().getStringExtra("fee"));
        tvGuestRating.setText("Trainee Rating: " + getIntent().getStringExtra("rating"));
        tvTraineeNames.setText("Trainee Name: " + getIntent().getStringExtra("traineeNames"));
        tvContact.setText("Contact: " + getIntent().getStringExtra("contact"));
        tvExamMarks.setText("Exam Score: " + getIntent().getStringExtra("examMarks")+" %");
        tvPracticalMarks.setText("Practical Marks: " + getIntent().getStringExtra("practicalMarks")+" %");
        tvAssignmentMarks.setText("Assignment Marks: " + getIntent().getStringExtra("assignMarks")+" %");
        tvFinalScore.setText("Final Score: " + getIntent().getStringExtra("finalScore")+" %");
        tvCertNo.setText("Certificate Number: " + getIntent().getStringExtra("certNo"));

        float rating = Float.parseFloat(getIntent().getStringExtra("rating"));
        ratingBar.setRating(rating);
        tvGuestRating.setText("Rating: " + rating);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

}