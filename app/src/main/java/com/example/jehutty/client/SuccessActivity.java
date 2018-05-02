package com.example.jehutty.client;

import android.content.SharedPreferences;
import android.support.v4.util.ArraySet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class SuccessActivity extends AppCompatActivity {
    SharedPreferences _user_rewardpoints;
    int rewardpoints;

    private TextView successmessageTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        String successmessage = getIntent().getStringExtra("MESSAGE");
        String reward = getIntent().getStringExtra("REWARDPOINTS");
        ArrayList<String> attendance_record_module_0 = getIntent().getStringArrayListExtra("MODULE_0_ATTENDANCE_RECORD" );
        ArrayList<String> attendance_record_module_1 = getIntent().getStringArrayListExtra("MODULE_1_ATTENDANCE_RECORD" );
        ArrayList<String> attendance_record_module_2 = getIntent().getStringArrayListExtra("MODULE_2_ATTENDANCE_RECORD" );
        ArrayList<String> attendance_record_module_3 = getIntent().getStringArrayListExtra("MODULE_3_ATTENDANCE_RECORD" );
        ArraySet<String> update_record_module_0 = new ArraySet<>(attendance_record_module_0);
        ArraySet<String> update_record_module_1 = new ArraySet<>(attendance_record_module_1);
        ArraySet<String> update_record_module_2 = new ArraySet<>(attendance_record_module_2);
        ArraySet<String> update_record_module_3 = new ArraySet<>(attendance_record_module_3);
        RegisterActivity.writeUserAttendance(getApplicationContext(), "module_0_attendance", update_record_module_0);
        RegisterActivity.writeUserAttendance(getApplicationContext(), "module_1_attendance",update_record_module_1);
        RegisterActivity.writeUserAttendance(getApplicationContext(), "module_2_attendance", update_record_module_2);
        RegisterActivity.writeUserAttendance(getApplicationContext(), "module_3_attendance", update_record_module_3);

        successmessageTextView = (TextView) findViewById(R.id.successMessageTextView);
        successmessageTextView.setText(successmessage + " you have been rewarded " + reward + " points!");

        _user_rewardpoints = getApplicationContext().getSharedPreferences("rewardpoints", 0);
        rewardpoints = _user_rewardpoints.getInt("rewardpoints", rewardpoints);
        rewardpoints += Integer.parseInt(reward);
        RegisterActivity.writeUserRewardPoints(getApplicationContext(),"rewardpoints", rewardpoints);

    }
}
