package com.example.Varsani.Staff.Trainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.Clients.SubmitAssignment;
import com.example.Varsani.R;
import com.example.Varsani.utils.SessionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Assignments extends AppCompatActivity {
    private SessionHandler sessionHandler;
    private UserModel userModel;
    private ListView listView;
    private TextView txvSubjectName;
    private String subjectId;
    private Intent intent;

    private Map<String, Class<?>> activityMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments);

        listView = findViewById(R.id.listView);
        txvSubjectName = findViewById(R.id.txv_subjectName);

        sessionHandler = new SessionHandler(this);
        userModel = sessionHandler.getUserDetails();

        Intent in = getIntent();
        txvSubjectName.setText(in.getStringExtra("subjectName"));
        subjectId = in.getStringExtra("subjectId");

        // Original list of items
        String[] listItems = {"Posted Assignments","Submit Assignment", "Upload Assignment", "Trainee Uploads"};

        // Map each item to the corresponding activity
        activityMap = new HashMap<>();
        activityMap.put("Posted Assignments", PostedAssignments.class);
        activityMap.put("Submit Assignment", SubmitAssignment.class);
        activityMap.put("Upload Assignment", UploadAssignment.class);
        activityMap.put("Trainee Uploads", TraineeUploads.class);

        // Check the user type (teacher or not)
        boolean isNotInstructor = (!userModel.getUser_type().equals("Trainer"));
        boolean isTrainee = userModel.getUser_type().equals("Client");

        List<String> filteredList = new ArrayList<>();

        // Add items to the filtered list based on the user type
        for (String item : listItems) {
            if (isNotInstructor && (item.equals("Upload Assignment") || item.equals("Trainee Uploads"))) {
                continue;
            }
            if (item.equals("Submit Assignment") && !isTrainee) {
                continue;
            }
            filteredList.add(item);
        }

        // Convert the filtered list back to an array
        String[] filteredItems = filteredList.toArray(new String[0]);

        // Set the adapter with the filtered list of items
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>
                (getApplicationContext(), R.layout.list_listview, R.id.textView, filteredItems);
        listView.setAdapter(arrayAdapter);

        setItemClickListener();
    }
    private void setItemClickListener() {
        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            String selectedItem = (String) adapterView.getItemAtPosition(position);

            // Get the corresponding activity for the selected item
            Class<?> activityClass = activityMap.get(selectedItem);

            if (activityClass != null) {
                intent = new Intent(getApplicationContext(), activityClass);
                intent.putExtra("subjectId", subjectId);
                startActivity(intent);
            }
        });
    }
}