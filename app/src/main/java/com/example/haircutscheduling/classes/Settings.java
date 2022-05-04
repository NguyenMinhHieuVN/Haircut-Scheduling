package com.example.haircutscheduling.classes;

import java.util.HashMap;

public class Settings {
    public HashMap<String,String> DayOffList;
    public HashMap<String,Day> OperationTime;

    public Settings(){
        DayOffList = new HashMap();
        OperationTime = new HashMap();
    }

    /*public void AddDayOff(String date){ DayOffList.add(date); }
    public void RemoveDayOff(String date){ if(DayOffList.contains(date)) DayOffList.remove(date); }

    public void AddDay(Day day){ OperationTime.add(day); }
    public void RemoveDay(Day day){ if(OperationTime.contains(day)) OperationTime.remove(day); }*/
}
