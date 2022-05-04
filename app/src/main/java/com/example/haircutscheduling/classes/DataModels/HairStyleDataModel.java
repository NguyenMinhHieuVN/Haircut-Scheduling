package com.example.haircutscheduling.classes.DataModels;

import com.example.haircutscheduling.classes.Day;

public class HairStyleDataModel {

    String hairStyle;
    String userId;
    String price;
    String date;
    String hour;
    Day day;
    int image;

    public HairStyleDataModel() {}

    public HairStyleDataModel(String hairStyle, String price, int image)
    {
        this.hairStyle = hairStyle;
        this.price = price;
        this.image = image;
    }

    public HairStyleDataModel(String hairStyle, String date, String hour)
    {
        this.hairStyle = hairStyle;
        this.date = date;
        this.hour = hour;
    }

    public HairStyleDataModel(String hairStyle, String price, String date, String hour, int image)
    {
        this.hairStyle = hairStyle;
        this.price = price;
        this.date = date;
        this.hour = hour;
        this.image = image;
    }

    public void setHairStyle(String hairStyle) {
        this.hairStyle = hairStyle;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setHour(String hour) {
        this.hour = hour;
    }
    public void setImage(int image) {
        this.image = image;
    }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setDay(Day day) {
        this.day = day;
    }
    public Day getDay() { return day; }
    public String getDate() { return date; }
    public String getHour() { return hour; }
    public String getHairStyle() { return hairStyle; }
    public String getPrice() { return price; }
    public int getImage() { return image; }
}
