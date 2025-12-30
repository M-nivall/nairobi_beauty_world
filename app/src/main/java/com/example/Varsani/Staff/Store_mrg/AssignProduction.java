package com.example.Varsani.Staff.Store_mrg;

import static com.example.Varsani.utils.Urls.URL_SUBMIT_PRODUCTION;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.R;
import com.example.Varsani.Staff.Store_mrg.Adapter.AssignedMaterialAdapter;
import com.example.Varsani.Staff.Store_mrg.Model.AssignedMaterial;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class AssignProduction extends AppCompatActivity {

    Spinner spinnerProduct, spinnerMaterial, spinnerUnit;
    EditText inputQuantity;
    Button btnAdd, btnSubmit;
    RecyclerView recyclerMaterials;
    EditText edt_produce_quantity,edt_batch_no,edt_production_date;

    List<AssignedMaterial> materialList;
    AssignedMaterialAdapter adapter;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    String date;

    DatePickerDialog datePicker;

    @RequiresApi(api = Build.VERSION_CODES.N)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_production);

        spinnerProduct = findViewById(R.id.spinner_product);
        spinnerMaterial = findViewById(R.id.spinner_material);
        spinnerUnit = findViewById(R.id.spinner_unit);
        inputQuantity = findViewById(R.id.input_quantity);
        edt_produce_quantity = findViewById(R.id.edt_produce_quantity);
        edt_batch_no = findViewById(R.id.edt_batch_no);
        edt_production_date = findViewById(R.id.edt_production_date);
        btnAdd = findViewById(R.id.btn_add);
        btnSubmit = findViewById(R.id.btn_submit);
        recyclerMaterials = findViewById(R.id.recycler_materials);

        materialList = new ArrayList<>();
        adapter = new AssignedMaterialAdapter(materialList);
        recyclerMaterials.setLayoutManager(new LinearLayoutManager(this));
        recyclerMaterials.setAdapter(adapter);

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        date = dateFormat.format(calendar.getTime());
        edt_production_date.setText(date);

        final Calendar calendar2 = Calendar.getInstance();
        final int day = calendar2.get(Calendar.DAY_OF_MONTH);
        final int year = calendar2.get(Calendar.YEAR);
        final int month = calendar2.get(Calendar.MONTH);

        datePicker = new DatePickerDialog(AssignProduction.this);

        edt_production_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker = new DatePickerDialog(AssignProduction.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        edt_production_date.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMinDate(calendar.getTimeInMillis());

                // show the dialog
                datePicker.show();
            }
        });

        // Sample spinner data
        ArrayAdapter<String> productAdapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_item,
                Arrays.asList("Nimson Glycerine", "Moehair Infused Conditioner", "Dove Shampoo",
                        "Avocado Oil", "Hair Mayonnaise", "Rosemary Essential Oil", "Lorys",
                        "Nourishing Hair Food", "Sasani Hair Conditioner", "Virgin Coconut Oil")
        );
        productAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerProduct.setAdapter(productAdapter);

        ArrayAdapter<String> materialAdapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_item,
                Arrays.asList("Aqua Water", "Coconut Oil", "Glycerin", "Cetyl Alcohol", "Lemon Oil", "Phenoxyethanol", "Potassium Sorbate")
        );
        materialAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerMaterial.setAdapter(materialAdapter);

        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_item,
                Arrays.asList("Litres", "mL", "kg", "g")
        );
        unitAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerUnit.setAdapter(unitAdapter);


        btnAdd.setOnClickListener(v -> {
            String material = spinnerMaterial.getSelectedItem().toString();
            String quantity = inputQuantity.getText().toString();
            String unit = spinnerUnit.getSelectedItem().toString();

            if (quantity.isEmpty()) {
                inputQuantity.setError("Enter quantity");
                return;
            }

            materialList.add(new AssignedMaterial(material, quantity, unit));
            adapter.notifyDataSetChanged();
            inputQuantity.setText("");
        });

        btnSubmit.setOnClickListener(v -> {
            if (materialList.isEmpty()) {
                Toast.makeText(this, "No materials to submit", Toast.LENGTH_SHORT).show();
                return;
            }

            final String product = spinnerProduct.getSelectedItem().toString();
            final String produce_quantity = edt_produce_quantity.getText().toString().trim();
            final String batch_no = edt_batch_no.getText().toString().trim();
            final String production_date = edt_production_date.getText().toString().trim();

            if (produce_quantity.isEmpty()) {
                edt_produce_quantity.setError("Enter quantity");
                return;
            }
            if (batch_no.isEmpty()) {
                edt_batch_no.setError("Enter batch number");
                return;
            }

            // Confirmation dialog
            new androidx.appcompat.app.AlertDialog.Builder(AssignProduction.this)
                    .setTitle("Confirm Submission")
                    .setMessage("Are you sure you want to submit the production details?")
                    .setPositiveButton("Yes", (dialog, which) -> {

                        StringRequest request = new StringRequest(Request.Method.POST, URL_SUBMIT_PRODUCTION,
                                response -> {
                                    Toast.makeText(this, "Production submitted successfully!", Toast.LENGTH_SHORT).show();

                                    // Clear data
                                    materialList.clear();
                                    adapter.notifyDataSetChanged();

                                    spinnerProduct.setSelection(0);
                                    spinnerMaterial.setSelection(0);
                                    spinnerUnit.setSelection(0);
                                    inputQuantity.setText("");
                                    edt_produce_quantity.setText("");
                                    edt_batch_no.setText("");
                                    edt_production_date.setText(dateFormat.format(Calendar.getInstance().getTime()));

                                    // Optionally finish activity
                                     finish();

                                },
                                error -> Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show()
                        ) {
                            @Override
                            protected HashMap<String, String> getParams() {
                                HashMap<String, String> params = new HashMap<>();
                                params.put("product", product);
                                params.put("produce_quantity", produce_quantity);
                                params.put("batch_no", batch_no);
                                params.put("production_date", production_date);

                                for (int i = 0; i < materialList.size(); i++) {
                                    AssignedMaterial m = materialList.get(i);
                                    params.put("material[" + i + "][name]", m.getMaterialName());
                                    params.put("material[" + i + "][qty]", m.getQuantity());
                                    params.put("material[" + i + "][unit]", m.getUnit());
                                }
                                return params;
                            }
                        };

                        Volley.newRequestQueue(this).add(request);

                    })
                    .setNegativeButton("Cancel", null)
                    .setCancelable(true)
                    .show();
        });

    }
}
