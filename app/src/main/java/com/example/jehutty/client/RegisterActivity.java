package com.example.jehutty.client;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.util.ArraySet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {
    private EditText surnameEditText;
    private EditText firstnameEditText;
    private EditText matnumberEditText;
    private Spinner courseSpinner;
    private Button registerBtn;
    private Courses courses = new Courses();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        ArrayList<String> coursesList = courses.getCoursenames();
        HashMap<String, ArrayList<Module>> modules = courses.getCoursesAndModules();
        for(Map.Entry<String, ArrayList<Module>> entry : modules.entrySet()){
            System.out.println(entry.getKey() + " : " + entry.getValue().get(0).getCourseName());
        }
        String[] coursesArray = coursesList.toArray(new String[coursesList.size()]);

        //Hooks for layout elements
        courseSpinner = (Spinner)findViewById(R.id.courseSpinner);
        registerBtn = (Button)findViewById(R.id.registerButton);
        surnameEditText = (EditText)findViewById(R.id.surnameEditText);
        firstnameEditText = (EditText)findViewById(R.id.firstnameEditText);
        matnumberEditText = (EditText)findViewById(R.id.matNumEditText);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, coursesArray);
        courseSpinner.setAdapter(adapter);


        registerBtn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                try{
                    String surname = surnameEditText.getText().toString();
                    String firstname = firstnameEditText.getText().toString();
                    Integer matNumber = Integer.parseInt(matnumberEditText.getText().toString().trim());
                    String selectedCourse = courseSpinner.getSelectedItem().toString();
                    ArraySet<String> module_0_attendance = new ArraySet<>();
                    ArraySet<String> module_1_attendance = new ArraySet<>();
                    ArraySet<String> module_2_attendance = new ArraySet<>();
                    ArraySet<String> module_3_attendance = new ArraySet<>();

                    if((!surname.equals(' ') && surname!=null) && (!firstname.equals(' ') && firstname!=null) && (!matNumber.equals(' ') && matNumber!=null)){

                        writeRegistration(getApplicationContext(), "Registered", true);
                        writeUserSurname(getApplicationContext(), "surname", surname);
                        writeUserFirstname(getApplicationContext(), "firstname", firstname);
                        writeUserCourse(getApplicationContext(), "course", selectedCourse);
                        writeUserMatnum(getApplicationContext(), "matnum", matNumber);
                        writeUserRewardPoints(getApplicationContext(), "rewardpoints", 0);
                        writeUserAttendance(getApplicationContext(), "module_0_attendance", module_0_attendance);
                        writeUserAttendance(getApplicationContext(), "module_1_attendance", module_1_attendance);
                        writeUserAttendance(getApplicationContext(), "module_2_attendance", module_2_attendance);
                        writeUserAttendance(getApplicationContext(), "module_3_attendance", module_3_attendance);



                        Intent i = new Intent(getApplicationContext(), TabbedActivity.class);
                        startActivity(i);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "Make sure you provide your information for all fields", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Make sure you provide your information for all fields", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    /**
     * write SharedPreferences
     * @param context
     * @param name, name of preferences
     * @param value, value of preferences
     */
    public static void writeRegistration(Context context, String name, Boolean value) {
        SharedPreferences setting= context.getSharedPreferences("Registered", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=setting.edit();
        editor.putBoolean(name, value);
        editor.commit();
    }

    public static void writeUserSurname(Context context, String name, String value){
        SharedPreferences setting = context.getSharedPreferences("surname", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString(name, value);
        editor.commit();
    }

    public static void writeUserFirstname(Context context, String name, String value){
        SharedPreferences setting = context.getSharedPreferences("firstname", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString(name, value);
        editor.commit();
    }

    public static void writeUserCourse(Context context, String name, String value){
        SharedPreferences setting = context.getSharedPreferences("course", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString(name, value);
        editor.commit();
    }

    public static void writeUserMatnum(Context context, String name, Integer value){
        SharedPreferences setting = context.getSharedPreferences("matnum", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putInt(name, value);
        editor.commit();
    }

    public static void writeUserRewardPoints(Context context, String name, Integer value){
        SharedPreferences setting = context.getSharedPreferences("rewardpoints", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putInt(name, value);
        editor.commit();
    }

    public static void writeUserAttendance(Context context, String name, ArraySet<String> attendanceset){
        SharedPreferences setting = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putStringSet(name, attendanceset);
        editor.commit();
    }





}
