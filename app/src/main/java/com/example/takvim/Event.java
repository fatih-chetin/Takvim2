package com.example.takvim;

public class Event {
    String event_name;
    String date;
    String timeStart,timeEnd;


    public Event(String event_name, String date, String timeStart, String timeEnd) {
        this.event_name = event_name;
        this.date = date;
        this.timeStart = timeStart;
        this.timeEnd=timeEnd;
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

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

}
