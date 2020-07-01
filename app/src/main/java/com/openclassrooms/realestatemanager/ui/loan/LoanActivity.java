package com.openclassrooms.realestatemanager.ui.loan;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.openclassrooms.realestatemanager.R;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoanActivity extends AppCompatActivity {

    @BindView(R.id.activity_loan_simulator_edit_amount)
    EditText editAmount;

    @BindView(R.id.activity_loan_simulator_edit_fee)
    EditText editFee;

    @BindView(R.id.activity_loan_simulator_seek_duration)
    IndicatorSeekBar seekDuration;

    @BindView(R.id.activity_loan_simulator_txt_duration)
    TextView txtDuration;

    @BindView(R.id.activity_loan_simulator_txt_monthly_cost)
    TextView txtMonthlyCost;

    float amount;
    float fee;
    int duration = 1;
    double monthlyCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_simulator);
        ButterKnife.bind(this);

        setTitle("Loan simulator");

        // Enable the Up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupEditText();
        setupSeekBar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }

    private void setupEditText() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                calculateMonthlyCost();
            }
        };

        editAmount.addTextChangedListener(textWatcher);
        editFee.addTextChangedListener(textWatcher);
    }

    private void setupSeekBar() {
        seekDuration.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
                duration = seekParams.progress;
                if (seekParams.progress == 1) {
                    txtDuration.setText(seekParams.progress + " an");
                } else {
                    txtDuration.setText(seekParams.progress + " ans");
                }
            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {
                calculateMonthlyCost();
            }
        });
    }

    private void calculateMonthlyCost() {
        if (editAmount.getText().toString().isEmpty() || editFee.getText().toString().isEmpty()) {
            return;
        }

        amount = Float.parseFloat(editAmount.getText().toString());
        fee = Float.parseFloat(editFee.getText().toString());

        if (amount <= 0 || fee <= 0) {
            return;
        }

        fee = fee / 100;

        monthlyCost = ((amount * fee) / 12) / (1 - Math.pow((1 + (fee / 12)), -12 * duration));

        if (monthlyCost <= 0) {
            return;
        }

        System.out.println(monthlyCost + "");
        txtMonthlyCost.setText(String.format("%.2f", monthlyCost) + " €");
    }


}
