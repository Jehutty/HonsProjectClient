package com.example.jehutty.client;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Module{

    private String moduleName;
    private String startsFrom;
    private String endsAt;
    private String moduleCode;
    private ArrayList<String> days;
    private ArrayList<String> availabledates;
    private int index;




    public Module(String moduleCode, String moduleName, String startsFrom, String endsAt, ArrayList<String> days, ArrayList<String> dates, int index){
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.startsFrom = startsFrom;
        this.endsAt = endsAt;
        this.days = days;
        this.availabledates = dates;
        this.index = index;
    }


    public String getCourseName() {
        return this.moduleName;
    }

    public String getStartsFrom() {
        return this.startsFrom;
    }

    public String getEndsAt() {
        return this.endsAt;
    }

    public ArrayList<String> getDays(){
        return this.days;
    }

    public ArrayList<String> getAvailabledates(){ return this.availabledates;}

    public String getModuleCode(){return this.moduleCode;}

    public int getIndex(){return this.index;}
}
