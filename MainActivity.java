package com.example.myzakat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextWeight, editTextGoldValue;
    private Spinner spinnerGoldType;
    private TextView textViewTotalValue, textViewZakatPayable, textViewTotalZakat;
    private Button buttonClear;
    private Button buttonCalculate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextWeight = findViewById(R.id.editTextWeight);
        editTextGoldValue = findViewById(R.id.editTextGoldValue);
        spinnerGoldType = findViewById(R.id.spinnerGoldType);

        textViewTotalValue = findViewById(R.id.textViewTotalValue);
        textViewZakatPayable = findViewById(R.id.textViewZakatPayable);
        textViewTotalZakat = findViewById(R.id.textViewTotalZakat);

        buttonCalculate = findViewById(R.id.buttonCalculate);
        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateValues();
            }
        });

        buttonClear = findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearValues();
            }
        });

        // Spinner setup
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.gold_types,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGoldType.setAdapter(adapter);

        // Set a listener for the spinner
        spinnerGoldType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Update calculations when the spinner item is selected
                calculateValues();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        // Call calculateValues initially
        calculateValues();
    }

    private void calculateValues() {
        try {
            double weight = Double.parseDouble(editTextWeight.getText().toString());
            double goldValue = Double.parseDouble(editTextGoldValue.getText().toString());
            int selectedPosition = spinnerGoldType.getSelectedItemPosition();

            // Define X values for gold keeping and wearing categories
            double xForKeeping = 85;
            double xForWearing = 200;

            // Calculate total value of the gold
            double totalValue = weight * goldValue;

            // Calculate total gold weight after deducting X (depending on the type of gold)
            double adjustedWeight = Math.max(0, weight - (selectedPosition == 0 ? xForKeeping : xForWearing));

            // Calculate total gold value that is zakat payable
            double zakatPayableValue = adjustedWeight * goldValue;

            // Calculate total zakat
            double totalZakat = 0.025 * zakatPayableValue; // 2.5%

            // Update TextViews with results
            textViewTotalValue.setText(getString(R.string.total_value, totalValue));
            textViewZakatPayable.setText(getString(R.string.zakat_payable_value, zakatPayableValue));
            textViewTotalZakat.setText(getString(R.string.total_zakat, totalZakat));

        } catch (NumberFormatException e) {
            // Handle the case where the input is not a valid number
            e.printStackTrace();
        }
    }

    private void clearValues() {
        editTextWeight.setText("");
        editTextGoldValue.setText("");
        spinnerGoldType.setSelection(0);
        textViewTotalValue.setText(getString(R.string.total_value, 0.00));
        textViewZakatPayable.setText(getString(R.string.zakat_payable_value, 0.00));
        textViewTotalZakat.setText(getString(R.string.total_zakat, 0.00));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
//                Toast.makeText(this, "Sharing", Toast.LENGTH_SHORT).show();
                shareApplicationUrl();
                return true;
            case R.id.item2:
                Toast.makeText(this, "Go To About", Toast.LENGTH_SHORT).show();
                // Start a new activity to display the "about.xml" layout
                Intent aboutIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
    private void shareApplicationUrl() {

        String appUrl = "https://drive.google.com/drive/folders/1FEb6EVKc5phaL8wXSgu6KQwifXEaEAff";

        // Create an Intent to share the application URL
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out and download my Zakat Calculator App now!!!: " + appUrl);

        // Display the share dialog
        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }
}
