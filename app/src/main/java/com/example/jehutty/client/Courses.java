package com.example.jehutty.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Courses {

    final String COMPUTER_SCIENCE_STAGE1 = "Computer Science(Stage 1)";
    final String COMPUTER_SCIENCE_STAGE2 = "Computer Science(Stage 2)";
    final String COMPUTER_SCIENCE_STAGE3 = "Computer Science(Stage 3)";
    final String COMPUTER_SCIENCE_STAGE4 = "Computer Science(Stage 4)";




    HashMap<String, ArrayList<Module>> coursesandmodules;
    ArrayList<String> coursenames;

    ArrayList<Module> CS1_MODULES;
    ArrayList<Module> CS2_MODULES;
    ArrayList<Module> CS3_MODULES;
    ArrayList<Module> CS4_MODULES;

    public Courses(){
        coursenames = new ArrayList<>();
        coursenames.add(COMPUTER_SCIENCE_STAGE1);
        coursenames.add(COMPUTER_SCIENCE_STAGE2);
        coursenames.add(COMPUTER_SCIENCE_STAGE3);
        coursenames.add(COMPUTER_SCIENCE_STAGE4);


        coursesandmodules = new HashMap<String, ArrayList<Module>>();

        //modules for stage 1 computer science course
        CS1_MODULES = new ArrayList<>();
        CS1_MODULES.add(
                new Module(
                "CM1013",
                "Collaborative and Professional Skills in Computing",
                "23",
                "25",
                new ArrayList<String>(Arrays.asList("Monday", "Tuesday","Wednesday")),
                new ArrayList<String>(Arrays.asList("Apr-30", "May-01", "May-02","May-07","May-08","May-09")),
                        0
                )
        );

        CS1_MODULES.add( new Module(
                "CM1014",
                "Problem Solving and Modelling",
                "13",
                "17",
                new ArrayList<String>(Arrays.asList("Monday", "Tuesday","Wednesday")),
                new ArrayList<String>(Arrays.asList("Apr-30", "May-01", "May-02","May-07","May-08","May-09")),
                1
            )
        );

        CS1_MODULES.add( new Module(
                "CM1015",
                "Software Design & Development",
                "00",
                "12",
                new ArrayList<String>(Arrays.asList("Thursday")),
                new ArrayList<String>(Arrays.asList("Apr-19","Apr-26", "May-03", "May-10")),
                2
            )
        );
        CS1_MODULES.add( new Module(
                        "CM1016",
                        "Computing Information Systems",
                        "13",
                        "16",
                        new ArrayList<String>(Arrays.asList("Thursday")),
                        new ArrayList<String>(Arrays.asList("Apr-19", "Apr-26", "May-03", "May-10")),
                3
                )
        );

        //Modules for Stage 2 computer science course;
        CS2_MODULES = new ArrayList<>();
        CS2_MODULES.add(
                new Module(
                        "CM2003",
                        "Dynamic Web Programming",
                        "9",
                        "13",
                        new ArrayList<String>(Arrays.asList("Monday", "Tuesday","Wednesday")),
                        new ArrayList<String>(Arrays.asList("Apr-30", "May-01", "May-02","May-07","May-08","May-09")),
                        0
                )
        );

        CS2_MODULES.add( new Module(
                        "CM2013",
                        "Professional Development in Computing",
                        "13",
                        "17",
                        new ArrayList<String>(Arrays.asList("Monday", "Tuesday","Wednesday")),
                        new ArrayList<String>(Arrays.asList("Apr-30", "May-01", "May-02","May-07","May-08","May-09")),
                1
                )
        );

        CS2_MODULES.add( new Module(
                        "CM2015",
                        "Object Oriented Software Design",
                        "9",
                        "12",
                        new ArrayList<String>(Arrays.asList("Thursday")),
                        new ArrayList<String>(Arrays.asList("Apr-19", "Apr-26", "May-03", "May-10")),
                2
                )
        );
        CS2_MODULES.add( new Module(
                        "CM2521",
                        "Introduction to Data Networks",
                        "13",
                        "16",
                        new ArrayList<String>(Arrays.asList("Thursday")),
                        new ArrayList<String>(Arrays.asList("Apr-19", "Apr-26", "May-03", "May-10")),
                3
                )
        );

        //Modules for Computer Science stage 3
        CS3_MODULES = new ArrayList<>();
        CS3_MODULES.add(
                new Module(
                        "CM3017",
                        "Database Systems",
                        "9",
                        "13",
                        new ArrayList<String>(Arrays.asList("Monday", "Tuesday","Wednesday")),
                        new ArrayList<String>(Arrays.asList("Apr-30", "May-01", "May-02","May-07","May-08","May-09")),
                        0
                )
        );

        CS3_MODULES.add( new Module(
                        "CM3019",
                        "Programming Mobile Devices",
                        "13",
                        "17",
                        new ArrayList<String>(Arrays.asList("Monday", "Tuesday","Wednesday")),
                        new ArrayList<String>(Arrays.asList("Apr-30", "May-01", "May-02","May-07","May-08","May-09")),
                1
                )
        );

        CS3_MODULES.add( new Module(
                        "CM3020",
                        "Operating Systems",
                        "9",
                        "12",
                        new ArrayList<String>(Arrays.asList("Thursday")),
                        new ArrayList<String>(Arrays.asList("Apr-19", "Apr-26","May-03", "May-10")),
                2
                )
        );
        CS3_MODULES.add( new Module(
                        "CM3028",
                        "Web Application Development",
                        "13",
                        "16",
                        new ArrayList<String>(Arrays.asList("Thursday")),
                        new ArrayList<String>(Arrays.asList("Apr-19", "Apr-26","May-03", "May-10")),
                3
                )
        );

        //Modules for Computer Science stage 4 course
        CS4_MODULES = new ArrayList<>();
        CS4_MODULES.add(
                new Module(
                        "CM4106",
                        "Languages and Compilers",
                        "9",
                        "12",
                        new ArrayList<String>(Arrays.asList("Monday", "Tuesday","Wednesday")),
                        new ArrayList<String>(Arrays.asList("Apr-30", "May-01", "May-02","May-07","May-08","May-09")),
                        0
                )
        );

        CS4_MODULES.add( new Module(
                        "CM4107",
                        "Advanced Artificial Intelligence",
                        "13",
                        "17",
                        new ArrayList<String>(Arrays.asList("Monday", "Tuesday","Wednesday")),
                        new ArrayList<String>(Arrays.asList("Apr-30", "May-01", "May-02","May-07","May-08","May-09")),
                1
                )
        );

        CS4_MODULES.add( new Module(
                        "CM4108",
                        "Cloud Computing",
                        "9",
                        "12",
                        new ArrayList<String>(Arrays.asList("Thursday")),
                        new ArrayList<String>(Arrays.asList("Apr-19", "Apr-26","May-03", "May-10")),
                2
                )
        );
        CS4_MODULES.add( new Module(
                        "CM4110",
                        "Human Computer Interaction",
                        "13",
                        "16",
                        new ArrayList<String>(Arrays.asList("Thursday")),
                        new ArrayList<String>(Arrays.asList("Apr-19", "Apr-26","May-03", "May-10")),
                3
                )
        );



        coursesandmodules.put(coursenames.get(0), CS1_MODULES);
        coursesandmodules.put(coursenames.get(1), CS2_MODULES);
        coursesandmodules.put(coursenames.get(2), CS3_MODULES);
        coursesandmodules.put(coursenames.get(3), CS4_MODULES);

    }


    public ArrayList<String> getCoursenames() {
        return this.coursenames;
    }

    public HashMap<String, ArrayList<Module>> getCoursesAndModules(){
        return this.coursesandmodules;
    }

    public ArrayList<Module> getModules(String course){
         return coursesandmodules.get(course);
    }





}
