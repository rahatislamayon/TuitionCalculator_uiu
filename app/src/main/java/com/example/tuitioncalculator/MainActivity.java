package com.example.tuitioncalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText totalFee;
    RadioGroup scholarshipGroup;
    LinearLayout scholarshipLayout;
    Spinner scholarshipType;
    Button calculateBtn;
    TextView resultFirst, resultSecond, resultThird, resultTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalFee = findViewById(R.id.totalFee);
        scholarshipGroup = findViewById(R.id.scholarshipGroup);
        scholarshipLayout = findViewById(R.id.scholarshipLayout);
        scholarshipType = findViewById(R.id.scholarshipType);
        calculateBtn = findViewById(R.id.calculateBtn);
        resultFirst = findViewById(R.id.resultFirst);
        resultSecond = findViewById(R.id.resultSecond);
        resultThird = findViewById(R.id.resultThird);
        resultTotal = findViewById(R.id.resultTotal);

        scholarshipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.yesScholarship) {
                scholarshipLayout.setVisibility(View.VISIBLE);
            } else {
                scholarshipLayout.setVisibility(View.GONE);
            }
        });

        calculateBtn.setOnClickListener(v -> calculateInstallments());
    }

    private void calculateInstallments() {
        String feeInput = totalFee.getText().toString();

        if (feeInput.isEmpty()) {
            Toast.makeText(this, "Please enter a valid tuition amount", Toast.LENGTH_SHORT).show();
            return;
        }

        double total = Double.parseDouble(feeInput);
        int selectedScholarshipId = scholarshipGroup.getCheckedRadioButtonId();
        double discountedFee = total;

        if (selectedScholarshipId == R.id.yesScholarship) {
            String scholarshipText = scholarshipType.getSelectedItem().toString();
            int percent = Integer.parseInt(scholarshipText.replaceAll("[^0-9]", ""));
            discountedFee = total * (1 - percent / 100.0);
        }

        double first = discountedFee * 0.4;
        double second = discountedFee * 0.3;
        double third = discountedFee * 0.3;

        resultFirst.setText("1st Installment: " + String.format("%.2f", first));
        resultSecond.setText("2nd Installment: " + String.format("%.2f", second));
        resultThird.setText("3rd Installment: " + String.format("%.2f", third));
        resultTotal.setText("Total Amount: " + String.format("%.2f", discountedFee));
    }
}
