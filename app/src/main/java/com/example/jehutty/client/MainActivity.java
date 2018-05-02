package com.example.jehutty.client;

import android.Manifest;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    SharedPreferences _Registered;
    boolean _user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Date c = Calendar.getInstance().getTime();
        Log.e("Date", c.toString());
        SimpleDateFormat df = new SimpleDateFormat("MMM-dd");
        SimpleDateFormat hf = new SimpleDateFormat("HH");
        String hoursformat = hf.format(c);
        String formmatedDate = df.format(c);

        Log.e("Date", formmatedDate);
        Log.e("Hours", hoursformat);
        _Registered = getApplicationContext().getSharedPreferences("Registered", 0);
        _user = _Registered.getBoolean("Registered", _user);
        super.onCreate(savedInstanceState);
        Log.e("Is USER registered? : ", " " + _user);

        if(_user == true){
            try{
                Intent i = new Intent(getApplicationContext(), TabbedActivity.class);
                startActivity(i);
                finish();
//                overridePendingTransition(R.anim.TabbedActivity.class, R.anim.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(i);
            finish();
        }

    }


}
