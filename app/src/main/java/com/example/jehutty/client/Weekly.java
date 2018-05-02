package com.example.jehutty.client;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Weekly extends Fragment{

    private static final String TAG = "Weekly";
    private TextView mondayTextView;
    private TextView tuesdayTextView;
    private TextView wednesdayTextView;
    private TextView thursdayTextView;
    private TextView fridayTextView;
    private ListView mondayListView;
    private ListView tuesdayListView;
    private ListView wednesdayListView;
    private ListView thursdayListView;
    private ListView fridayListView;

    private SharedPreferences _user_course;
    private String usercourse;

    private Courses courses = new Courses();
    private ArrayList<Module> modulesList;
    private HashMap<String, ArrayList<String>> availableDays;
    private ArrayList<String> mondayCourses;
    private ArrayList<String> tuesdayCourses;
    private ArrayList<String> wednesdayCourses;
    private ArrayList<String> thursdayCourses;
    private ArrayList<String> fridayCourses;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weekly, container, false);


        mondayTextView = (TextView) view.findViewById(R.id.mondayTextView);
        tuesdayTextView = (TextView) view.findViewById(R.id.tuesdayTextView);
        wednesdayTextView = (TextView) view.findViewById(R.id.wednesdayTextView);
        thursdayTextView = (TextView) view.findViewById(R.id.thursdayTextView);
        fridayTextView = (TextView) view.findViewById(R.id.fridayTextView);

        mondayListView = (ListView) view.findViewById(R.id.mondayListView);
        tuesdayListView = (ListView) view.findViewById(R.id.tuesdayListView);
        wednesdayListView = (ListView) view.findViewById(R.id.wednesdayListView);
        thursdayListView = (ListView) view.findViewById(R.id.thursdayListView);
        fridayListView = (ListView) view.findViewById(R.id.fridayListView);

        mondayCourses = new ArrayList<>();
        tuesdayCourses = new ArrayList<>();
        wednesdayCourses = new ArrayList<>();
        thursdayCourses = new ArrayList<>();
        fridayCourses = new ArrayList<>();
        availableDays = new HashMap<>();

//        Date c = Calendar.getInstance().getTime();
//        SimpleDateFormat df = new SimpleDateFormat("EEEE");
//        String formattedDay = df.format(c);
        HashMap<String, ArrayList<Module>> coursesAndModules = courses.getCoursesAndModules();
        _user_course = getContext().getSharedPreferences("course", 0);
        usercourse = _user_course.getString("course", usercourse);


        for(Map.Entry<String, ArrayList<Module>> entry : coursesAndModules.entrySet()) {
            if (entry.getKey().equals(usercourse)) {
                Log.e("ENTRY KEY", entry.toString());
                for (Module m : entry.getValue()) {
                    Log.e("VALUE SET",m.toString());
                    for(String day : m.getDays()){
                        if(day.equals("Monday")){
                            mondayCourses.add(m.getCourseName() + "\n" + m.getStartsFrom() + ":00 - " + m.getEndsAt() + ":00");
                        }
                        if(day.equals("Tuesday")){
                            tuesdayCourses.add(m.getCourseName() + "\n" + m.getStartsFrom() + ":00 - " + m.getEndsAt() + ":00");
                        }
                        if(day.equals("Wednesday")){
                            wednesdayCourses.add(m.getCourseName() + "\n" + m.getStartsFrom() + ":00 - " + m.getEndsAt() + ":00");
                        }
                        if(day.equals("Thursday")){
                            thursdayCourses.add(m.getCourseName()+ "\n" + m.getStartsFrom() + ":00 - " + m.getEndsAt() + ":00");
                        }
                        if(day.equals("Friday")){
                            fridayCourses.add(m.getCourseName()+ "\n" + m.getStartsFrom() + ":00 - " + m.getEndsAt() + ":00");
                        }
                    }
                }
            }
        }

        availableDays.put("Monday", mondayCourses);
        availableDays.put("Tuesday", tuesdayCourses);
        availableDays.put("Wednesday", wednesdayCourses);
        availableDays.put("Thursday", thursdayCourses);
        availableDays.put("Friday", fridayCourses);


        if(availableDays.get("Monday").size()!=0){
            mondayTextView.setText("MONDAY");
            mondayListView.setVisibility(View.VISIBLE);

            String[] mondayArray = mondayCourses.toArray(new String[mondayCourses.size()]);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, mondayArray);
            mondayListView.setAdapter(adapter);
        }else{
            mondayTextView.setText("");
            mondayTextView.setVisibility(View.GONE);
            mondayTextView.setVisibility(View.GONE);
        }

        if(availableDays.get("Tuesday").size()!=0){
            tuesdayTextView.setText("TUESDAY");
            tuesdayListView.setVisibility(View.VISIBLE);

            String[]tuesdayArray = tuesdayCourses.toArray(new String[tuesdayCourses.size()]);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, tuesdayArray);
            tuesdayListView.setAdapter(adapter);
        }else{
            tuesdayTextView.setText("");
            tuesdayTextView.setVisibility(View.GONE);
            tuesdayListView.setVisibility(View.GONE);
        }

        if(availableDays.get("Wednesday").size()!=0){
            wednesdayTextView.setText("WEDNESDAY");
            wednesdayListView.setVisibility(View.VISIBLE);

            String[] wednesdayArray = wednesdayCourses.toArray(new String[wednesdayCourses.size()]);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, wednesdayArray);
            wednesdayListView.setAdapter(adapter);
        }else{
            wednesdayTextView.setText("");
            wednesdayTextView.setVisibility(View.GONE);
            wednesdayListView.setVisibility(View.GONE);
        }

        if(availableDays.get("Thursday").size()!=0){
            thursdayTextView.setText("THURSDAY");
            thursdayListView.setVisibility(View.VISIBLE);

            String[] thursdayArray = thursdayCourses.toArray(new String[thursdayCourses.size()]);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, thursdayArray );
            thursdayListView.setAdapter(adapter);
        }else{
            thursdayTextView.setText("");
            thursdayTextView.setVisibility(View.GONE);
            thursdayListView.setVisibility(View.GONE);
        }

        if(availableDays.get("Friday").size()!=0){
            fridayTextView.setText("FRIDAY");
            fridayListView.setVisibility(View.VISIBLE);

            String[] fridayArray = fridayCourses.toArray(new String[fridayCourses.size()]);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, fridayArray);
            fridayListView.setAdapter(adapter);
        }else{
            fridayTextView.setText("");
            fridayTextView.setVisibility(View.GONE);
            fridayListView.setVisibility(View.GONE);
        }

//        Log.e("Avail days", availableDays.get("Monday").toString());
        return view;
    }
}
