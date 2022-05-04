package com.example.haircutscheduling.classes.DataModels;

public class UpdateDataModel {

    String update;
    String date;

    public UpdateDataModel() {}

    public UpdateDataModel(String update, String date)
    {
        this.update = update;
        this.date = date;
    }

    public String getUpdate() {
        return update;
    }

    public String getDate() {
        return date;
    }
}
