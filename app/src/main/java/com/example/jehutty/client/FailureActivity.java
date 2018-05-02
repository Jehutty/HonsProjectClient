package com.example.jehutty.client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class FailureActivity extends AppCompatActivity {
    private TextView failuremessageTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_failure);
        String failuremessage = getIntent().getStringExtra("MESSAGE");


        failuremessageTextView = (TextView) findViewById(R.id.failureMessage);
        failuremessageTextView.setText(failuremessage);
    }
}
