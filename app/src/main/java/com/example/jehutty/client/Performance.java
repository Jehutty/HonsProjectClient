package com.example.jehutty.client;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArraySet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

public class Performance extends Fragment {
    private static final String TAG = "Performance";

    private TextView rewardsTextView;
    private TextView module0TextView;
    private TextView module1TextView;
    private TextView module2TextView;
    private TextView module3TextView;

    SharedPreferences _user_surname;
    SharedPreferences _user_firstname;
    SharedPreferences _user_matnum;
    SharedPreferences _user_course;
    SharedPreferences _user_rewardpoints;
    SharedPreferences _user_attendance_module_0;
    SharedPreferences _user_attendance_module_1;
    SharedPreferences _user_attendance_module_2;
    SharedPreferences _user_attendance_module_3;

    int rewardpoints;
    int matnum;
    String surname;
    String firstname;
    String course;
    Set<String> module_0_attendance;
    Set<String> module_1_attendance;
    Set<String> module_2_attendance;
    Set<String> module_3_attendance;

    Courses courses = new Courses();




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_performance, container, false);

        rewardsTextView = (TextView) view.findViewById(R.id.rewardPointsTextView);
        module0TextView = (TextView) view.findViewById(R.id.firstModulePerformance);
        module1TextView = (TextView) view.findViewById(R.id.secondModulePerformance);
        module2TextView = (TextView) view.findViewById(R.id.thirdModulePerformance);
        module3TextView = (TextView) view.findViewById(R.id.fourthModulePerformance);

        //Student's surname
        _user_surname = getContext().getSharedPreferences("surname", 0);
        surname = _user_surname.getString("surname", surname);
        //Student's firstname
        _user_firstname  = getContext().getSharedPreferences("firstname", 0);
        firstname = _user_firstname.getString("firstname", firstname);
        //Student's matriculation number
        _user_matnum = getContext().getSharedPreferences("matnum", 0);
        matnum = _user_matnum.getInt("matnum", matnum);
        //Student's course
        _user_course = getContext().getSharedPreferences("course", 0);
        course = _user_course.getString("course", course);
        //Student's reward points
        _user_rewardpoints = getContext().getSharedPreferences("rewardpoints", 0);
        rewardpoints = _user_rewardpoints.getInt("rewardpoints", rewardpoints);
        //Student's attendance
        _user_attendance_module_0 = getContext().getSharedPreferences("module_0_attendance", 0);
        _user_attendance_module_1 = getContext().getSharedPreferences("module_1_attendance", 0);
        _user_attendance_module_2 = getContext().getSharedPreferences("module_2_attendance", 0);
        _user_attendance_module_3 = getContext().getSharedPreferences("module_3_attendance", 0);

        module_0_attendance = new ArraySet<>();
        module_1_attendance = new ArraySet<>();
        module_2_attendance = new ArraySet<>();
        module_3_attendance = new ArraySet<>();
        module_0_attendance = _user_attendance_module_0.getStringSet("module_0_attendance", module_0_attendance);
        module_1_attendance = _user_attendance_module_1.getStringSet("module_1_attendance", module_1_attendance);
        module_2_attendance = _user_attendance_module_2.getStringSet("module_2_attendance", module_2_attendance);
        module_3_attendance = _user_attendance_module_3.getStringSet("module_3_attendance", module_3_attendance);

        ArrayList<String> module_0_dates = new ArrayList<>();
        ArrayList<String> module_1_dates = new ArrayList<>();
        ArrayList<String> module_2_dates = new ArrayList<>();
        ArrayList<String> module_3_dates = new ArrayList<>();
        module_0_dates = courses.getModules(course).get(0).getAvailabledates();
        module_1_dates = courses.getModules(course).get(1).getAvailabledates();
        module_2_dates = courses.getModules(course).get(2).getAvailabledates();
        module_3_dates = courses.getModules(course).get(3).getAvailabledates();


        if(rewardpoints!=0){
            rewardsTextView.setText("Reward Points: " + rewardpoints);
        }else{
            rewardsTextView.setText("You have no reward points yet, start attending to gather them!");
        }
        Log.e("Module 0 times attended", ""+ module_0_attendance.size());
        Log.e("Module 1 times attended", ""+ module_1_attendance.size());
        Log.e("Module 2 times attended", ""+ module_2_attendance.size());
        Log.e("Module 3 times attended", ""+ module_3_attendance.size());
        Log.e("Module 0 times", ""+ module_0_attendance.toString());
        Log.e("Module 1 times ", ""+ module_1_attendance.toString());
        Log.e("Module 2 times", ""+ module_2_attendance.toString());
        Log.e("Module 3 times ", ""+ module_3_attendance.toString());

        if(module_0_attendance.size() != 0){

            try {

                int index = getAvailableDatesUpToToday(module_0_dates);
                if(index!=0){
                    float attendanceAverage = (float) ( ((float)module_0_attendance.size()/(float)index) )* 100;
                    String formattedAverage = String.format("%.0f", attendanceAverage);
//                    Log.e("average attendance", ""+ formattedAverage + " %" );
//                    Log.e("module 0 avail dates ", ""+index);
                    module0TextView.setText("Your attendance for " + courses.getModules(course).get(0).getModuleCode() + " is : " + formattedAverage + " %");
                }else{
                    module0TextView.setText(courses.getModules(course).get(0).getModuleCode() + ": is not available yet.");
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }else{
            module0TextView.setText(courses.getModules(course).get(0).getModuleCode() + ": You haven't attended this module yet.");
        }

        if(module_1_attendance.size() != 0){

            try {
                int index = getAvailableDatesUpToToday(module_1_dates);
                if(index!=0){
                    float attendanceAverage = (float) ( ((float)index/module_1_attendance.size()/(float)index) )* 100;
                    String formattedAverage = String.format("%.0f", attendanceAverage);
//                    Log.e("average attendance", ""+ formattedAverage + " %" );
//                    Log.e("module 1 avail dates", ""+index);
                    module1TextView.setText("Your attendance for " + courses.getModules(course).get(1).getModuleCode() + " is : " + formattedAverage + " %");
                }else{
                    module1TextView.setText(courses.getModules(course).get(1).getModuleCode() + ": is not available yet.");
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }else{
            module1TextView.setText(courses.getModules(course).get(1).getModuleCode() + ": You haven't attended this module yet.");
        }

        if(module_2_attendance.size() != 0){

            try {
                int index = getAvailableDatesUpToToday(module_2_dates);
                if(index!=0){
                    float attendanceAverage = (float) ( ((float)module_2_attendance.size()/(float)index) )* 100;
                    String formattedAverage = String.format("%.0f", attendanceAverage);
                    module2TextView.setText("Your attendance for " + courses.getModules(course).get(2).getModuleCode() + " is : " + formattedAverage + " %");
                }else{
                    module2TextView.setText( courses.getModules(course).get(2).getModuleCode() + ": is not available yet." );
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }else{
            module2TextView.setText(courses.getModules(course).get(2).getModuleCode() + ": You haven't attended this module yet.");
        }


        if(module_3_attendance.size() != 0){

            try {
                int index = getAvailableDatesUpToToday(module_3_dates);
                if(index != 0){
                    float attendanceAverage = (float) ( ((float)module_3_attendance.size()/(float)index) )* 100;
                    String formattedAverage = String.format("%.0f", attendanceAverage);
//                    Log.e("average attendance", ""+ formattedAverage + " %" );
//                    Log.e("module 3 avail dates", ""+index);
                    module3TextView.setText("Your attendance for " + courses.getModules(course).get(3).getModuleCode() + " is : " + formattedAverage + " %");
                }else{
                    module3TextView.setText( courses.getModules(course).get(3).getModuleCode() + ": is not available yet." );
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }else{
            module3TextView.setText(courses.getModules(course).get(3).getModuleCode() + ": You haven't attended this module yet.");
        }



        return view;
    }


    public int getAvailableDatesUpToToday(ArrayList<String> availabledates) throws ParseException {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MMM-dd");
        String todayformatted = df.format(c);

        Date today = new SimpleDateFormat("MMM-dd").parse(todayformatted);
        int index = 0;
        for(int i =0; i < availabledates.size(); i++){
            Date availableDate = new SimpleDateFormat("MMM-dd").parse(availabledates.get(i));
            if(today.after(availableDate)){
                index = i+1;
            }
        }

        return index;
    }
}
