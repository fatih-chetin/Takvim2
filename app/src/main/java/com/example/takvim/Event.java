package com.example.takvim;

public class Event {
    String event_name;
    String date;
    String time_interval;

    public Event(String event_name, String date, String time_interval) {
        this.event_name = event_name;
        this.date = date;
        this.time_interval = time_interval;
    }

    public Event() {

    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime_interval() {
        return time_interval;
    }

    public void setTime_interval(String time_interval) {
        this.time_interval = time_interval;
    }
}
