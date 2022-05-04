package com.example.haircutscheduling.classes.Data;

import com.example.haircutscheduling.classes.DataModels.HairStyleDataModel;

import java.util.HashMap;

public class AppointmentsData {

    public AppointmentsData() {
        this.appointmentsList = new HashMap();
    }

    public HashMap<String, HairStyleDataModel> appointmentsList;

    public AppointmentsData(HashMap value) {
        this.appointmentsList = value;
    }
}
