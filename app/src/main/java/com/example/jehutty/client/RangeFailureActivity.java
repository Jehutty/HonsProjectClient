package com.example.jehutty.client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class RangeFailureActivity extends AppCompatActivity {
    private TextView failureRangeMessageTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_range_failure);

        failureRangeMessageTextView = (TextView) findViewById(R.id.failureRangeCheckTextView);
        failureRangeMessageTextView.setText("Oops! It seems your device is not in range with a raspberry pi module server! \n\nPlease get closer to one and try again");
    }
}
