package com.example.jehutty.client;



import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Current extends Fragment {

    private static final String TAG = "Current";
    private ListView modulesListView;
    private Button attendButton;
    private Courses courses = new Courses();
    private SharedPreferences _user_course;
    private String usercourse;
    private ArrayList<String> modulesList;
    private ArrayList<String> mDeviceList = new ArrayList<String>();
    private String []modulesArray;
    private TextView userInfoTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now, container, false);
        modulesListView = (ListView) view.findViewById(R.id.modulesListView);
        userInfoTextView = (TextView) view.findViewById(R.id.currentInfoTextView);
        attendButton = (Button) view.findViewById(R.id.attendButton);
        _user_course = getContext().getSharedPreferences("course", 0);
        usercourse = _user_course.getString("course", usercourse);
        HashMap<String, ArrayList<Module>> coursesAndModules = courses.getCoursesAndModules();
        modulesList = new ArrayList<>();


        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MMM-dd");
        SimpleDateFormat hf = new SimpleDateFormat("HH");
        String hoursformat = hf.format(c);
        String formmatedDate = df.format(c);
        for(Map.Entry<String, ArrayList<Module>> entry : coursesAndModules.entrySet()){
           if(entry.getKey().equals(usercourse)){
               for(Module m : entry.getValue()){
                   int startsFrom = Integer.parseInt(m.getStartsFrom());
                   int endsAt = Integer.parseInt(m.getEndsAt());
                   int currentHour = Integer.parseInt(hoursformat);
                   Log.e("STARTS FROM" ,  " " + Integer.parseInt(m.getStartsFrom()));
                   Log.e("ENDS AT" ,  " " + Integer.parseInt(m.getEndsAt()));
                   Log.e("CURRENT HOUR", " " + Integer.parseInt(hoursformat));
                   Log.e("CURRENT DAY", " " + formmatedDate);

                  if( (currentHour >= startsFrom) && (currentHour < endsAt)){
                      for(String date : m.getAvailabledates()){
                          if(date.equals(formmatedDate)){
                              modulesList.add(m.getModuleCode() + ": " + m.getCourseName());
                          }
                      }
                  }
               }
           }
        }
        modulesArray = modulesList.toArray(new String[modulesList.size()]);
        Log.e("AVAILABLE COURSES", " " + modulesArray.length);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_single_choice, modulesArray);
        modulesListView.setAdapter(adapter);
        modulesListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        if(modulesArray.length != 0){
            attendButton.setVisibility(View.VISIBLE);
            userInfoTextView.setText("Ongoing modules");
        }else{
            attendButton.setVisibility(View.GONE);
            userInfoTextView.setText("No modules today! Have a great day");
        }
        

        attendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = modulesListView.getCheckedItemPosition();
                if(position != modulesListView.INVALID_POSITION){
                    String selected = ((TextView) modulesListView.getChildAt(position)).getText().toString().substring(0,((TextView) modulesListView.getChildAt(position)).getText().toString().indexOf(':'));

                    Toast.makeText(getContext(), "Selected item is " + selected, Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getContext(), AttendActivity.class);
                    i.putExtra("SELECTED_COURSE", selected);
                    startActivity(i);
                }else{
                    Toast.makeText(getContext(), "No module to attend was selected...", Toast.LENGTH_LONG).show();

                }
            }
        });



        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
    }





}
