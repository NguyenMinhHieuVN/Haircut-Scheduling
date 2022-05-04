package com.example.haircutscheduling.classes;

public class Day {

    private String Name;
    private String StartHour;
    private String EndHour;
    private Boolean DayOff;

    public Day() {
    }

    public Day(String name, String startHour, String endHour, Boolean dayOff) {
        setName(name);
        setStartHour(startHour);
        setEndHour(endHour);
        setDayOff(dayOff);
    }
    public String getName() { return Name; }
    public void setName(String name) { Name = name; }
    public String getStartHour() { return StartHour; }
    public void setStartHour(String startHour) { StartHour = startHour; }
    public String getEndHour() { return EndHour; }
    public void setEndHour(String endHour) { EndHour = endHour; }
    public Boolean getDayOff() { return DayOff; }
    public void setDayOff(Boolean dayOff) { DayOff = dayOff; }
}
