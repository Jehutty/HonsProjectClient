package com.example.jehutty.client;

public class RaspberryPIAddresses {


    private String API_IP;
    private String RPI3_UUID;
    private String studentSecret;
    //https://attendanceserver-hons.herokuapp.com/api remote heroku ip for testing

    public RaspberryPIAddresses(){
        this.API_IP = "http://192.168.1.245:8000/api/attend";
        this.RPI3_UUID = "B8:27:EB:B2:5D:55";
        this.studentSecret ="studentSecret";
    }

    public String getAPI_IP() {
        return this.API_IP;
    }

    public String getRPI3_UUID() {
        return this.RPI3_UUID;
    }

    public String getStudentSecret(){return this.studentSecret;}
}
