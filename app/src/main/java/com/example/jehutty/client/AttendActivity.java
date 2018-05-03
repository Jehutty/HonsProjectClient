package com.example.jehutty.client;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.JsonArray;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AttendActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback{

    private final static int REQUEST_ENABLE_BT = 1;
    private BluetoothAdapter mBluetoothAdapter;
    static final int TIME_OUT = 5000;
    final int REQUEST_READ_PHONE_STATE = 1;
    static final int MSG_DISMISS_DIALOG = 0;
    static ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private TextView attendInfoTextView;
    private Button confirmButton;
    private String selectedCourse;
    private Courses courses = new Courses();

    private SharedPreferences _user_surname;
    private SharedPreferences _user_firstname;
    private SharedPreferences _user_matnum;
    private SharedPreferences _user_course;
    private String surname;
    private String firstname;
    private int matnum;
    private String usercourse;

    private RaspberryPIAddresses rpi3 = new RaspberryPIAddresses();
    private HashMap<String, String> mDeviceList = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attend);

        attendInfoTextView = (TextView)findViewById(R.id.attendInfoTextView);
        confirmButton = (Button)findViewById(R.id.jsonbutton);
        selectedCourse = getIntent().getStringExtra("SELECTED_COURSE");
        attendInfoTextView.setText("You have chosen to attend " + selectedCourse + " your device is scanning for available module servers nearby, please confirm your attendance by pressing the confirm button when it becomes available");
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothAdapter.cancelDiscovery();

        //GET DEVICE'S UUID
        TelephonyManager tManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String uuid = tManager.getDeviceId();
        Log.e("UUID", uuid);


        //TURN THE WIFI ON IF ITS DISABLED
        WifiManager wifi = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (!wifi.isWifiEnabled()){
            wifi.setWifiEnabled(true);
        }




        //BLUETOOTH HANDLING

        if (mBluetoothAdapter == null) {
            // Device doesn't support Bluetooth
        }

        //TURN BLUETOOTH ON IF ITS DISABLED
        if (!mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.enable();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    IntentFilter filter = new IntentFilter();
                    filter.addAction(BluetoothDevice.ACTION_FOUND);
                    filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
                    registerReceiver(mReceiver, filter);
                    mBluetoothAdapter.startDiscovery();
                }
            }, 4000);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    confirmButton.setEnabled(true);
                }
            }, 2);

        }else{
            IntentFilter filter = new IntentFilter();
            filter.addAction(BluetoothDevice.ACTION_FOUND);
            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
            registerReceiver(mReceiver, filter);
            mBluetoothAdapter.startDiscovery();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    confirmButton.setEnabled(true);
                }
            }, 2);
        }



    }


    public void SendAttendance(View view) {
        confirmButton.setEnabled(false);
        new JSONProcess().execute();
    }

    class JSONProcess extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AttendActivity.this);
            pDialog.setMessage("Sending attendance request...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            mHandler.sendEmptyMessageDelayed(MSG_DISMISS_DIALOG, TIME_OUT);
        }

        @Override
        protected String doInBackground(String... args) {
            boolean flag = false;
            for(Map.Entry<String, String> m : mDeviceList.entrySet()){
                if (m.getKey().equals("rpiserver") && m.getValue().equals(rpi3.getRPI3_UUID())) {
                    flag = true;
                }
            }

            if(flag){
                _user_surname = getApplicationContext().getSharedPreferences("surname", 0);
                surname = _user_surname.getString("surname", surname);
                _user_firstname  = getApplicationContext().getSharedPreferences("firstname", 0);
                firstname = _user_firstname.getString("firstname", firstname);
                _user_matnum = getApplicationContext().getSharedPreferences("matnum", 0);

                matnum = _user_matnum.getInt("matnum", matnum);
                _user_course = getApplicationContext().getSharedPreferences("course", 0);
                usercourse = _user_course.getString("course", usercourse);
                TelephonyManager tManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
                @SuppressLint("MissingPermission") String uuid = tManager.getDeviceId();
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("MMM-dd");
                String formmatedDate = df.format(c);
                ArrayList<Module> modulesList;
                modulesList = courses.getModules(usercourse);
                int index = 0;
                for(Module m : modulesList){
                    if(m.getModuleCode().equals(selectedCourse)){
                        index = m.getIndex();
                    }
                }

                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("firstname", firstname));
                params.add(new BasicNameValuePair("surname", surname));
                params.add(new BasicNameValuePair("matnum", ""+matnum));
                params.add(new BasicNameValuePair("deviceID", uuid));
                params.add(new BasicNameValuePair("course", usercourse));
                params.add(new BasicNameValuePair("date", formmatedDate));
                params.add(new BasicNameValuePair("index",""+index));
                params.add(new BasicNameValuePair("coursecode", selectedCourse));
                params.add(new BasicNameValuePair("secret", rpi3.getStudentSecret()));
                //RPI3 addresses

                //heroku ip "https://attendanceserver-hons.herokuapp.com/api/attend"
                JSONObject json = jsonParser.makeHttpRequest(rpi3.getAPI_IP(), "POST", params);


                try {
                    if(json != null){

                        int success = json.getInt("success");
                        if(success == 1){
                            pDialog.dismiss();
                            Intent i = new Intent(getApplicationContext(), SuccessActivity.class);
                            if(json.getString("message") != null){
                                i.putExtra("MESSAGE", json.getString("message"));
                            }
                            if(json.getInt("rewardpoints") != 0){
                                i.putExtra("REWARDPOINTS", "" + json.getInt("rewardpoints"));
                            }

                            if(json.getJSONArray("attendance_0")!=null){
                                JSONArray attendance_module_0_jsonarray = json.getJSONArray("attendance_0");
                                JSONArray attendance_module_1_jsonarray = json.getJSONArray("attendance_1");
                                JSONArray attendance_module_2_jsonarray = json.getJSONArray("attendance_2");
                                JSONArray attendance_module_3_jsonarray = json.getJSONArray("attendance_3");

                                ArrayList<String> attendance_record_module_0 = new ArrayList<>();
                                ArrayList<String> attendance_record_module_1 = new ArrayList<>();
                                ArrayList<String> attendance_record_module_2 = new ArrayList<>();
                                ArrayList<String> attendance_record_module_3 = new ArrayList<>();

                                for(int x =0; x < attendance_module_0_jsonarray.length(); x++){
                                    attendance_record_module_0.add(attendance_module_0_jsonarray.getString(x));
                                }
                                for(int x =0; x < attendance_module_1_jsonarray.length(); x++){
                                    attendance_record_module_1.add(attendance_module_1_jsonarray.getString(x));
                                }
                                for(int x =0; x < attendance_module_2_jsonarray.length(); x++){
                                    attendance_record_module_2.add(attendance_module_2_jsonarray.getString(x));
                                }
                                for(int x =0; x < attendance_module_3_jsonarray.length(); x++){
                                    attendance_record_module_3.add(attendance_module_3_jsonarray.getString(x));
                                }


                                i.putExtra("MODULE_0_ATTENDANCE_RECORD", attendance_record_module_0);
                                i.putExtra("MODULE_1_ATTENDANCE_RECORD", attendance_record_module_1);
                                i.putExtra("MODULE_2_ATTENDANCE_RECORD", attendance_record_module_2);
                                i.putExtra("MODULE_3_ATTENDANCE_RECORD", attendance_record_module_3);

                            }
                            startActivity(i);
                            finish();
                        }else if(success == 0){
                            pDialog.dismiss();
                            Intent i = new Intent(getApplicationContext(), FailureActivity.class);
                            if(json.getString("message") != null){
                                i.putExtra("MESSAGE",json.getString("message"));
                            }

                            startActivity(i);
                            finish();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                Intent i = new Intent(getApplicationContext(), RangeFailureActivity.class);
                startActivity(i);
                finish();
            }



            return null;
        }

        protected void onPostExecute(String file_url){
            pDialog.dismiss();
        }
    }


    static Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_DISMISS_DIALOG:
                    if (pDialog != null && pDialog.isShowing()) {
                        pDialog.dismiss();
                    }
                    break;

                default:
                    break;
            }
        }
    };



    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {

                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if(device.getName() != null){
                    String deviceName = device.getName().toString();
                    String deviceID = device.getAddress().toString();
                    mDeviceList.put(deviceName, deviceID);
                    Log.e("BT DEVICE FOUND:", mDeviceList.toString() );
                }
            }

            if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
                Log.e("Discovery finished", "Bluetooth discovery ended");
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // unregister the ACTION_FOUND receiver so it doesn't perform leaks.
        try{
            unregisterReceiver(mReceiver);
        }catch (Exception e){
            e.printStackTrace();
        }
    }




}
