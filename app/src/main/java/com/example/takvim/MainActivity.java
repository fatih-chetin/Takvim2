package com.example.takvim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements EventAdapter.ItemClicked {
    CalendarView calendarView;
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Event> events;
    ArrayList<Event> DailyEvents;
    ImageView btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DailyEvents=new ArrayList<Event>();

        calendarView=findViewById(R.id.calendarView);
        recyclerView=findViewById(R.id.recyc);

        layoutManager= new GridLayoutManager(this,1, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        events=new ArrayList<Event>();

        btnAdd=findViewById(R.id.btnAdd);

        events.add(new Event("study","9/4/2021","00.00-23.59"));
        events.add(new Event("study","9/4/2021","00.00-23.59"));
        events.add(new Event("study","8/4/2021","00.00-23.59"));
        events.add(new Event("study","8/4/2021","00.00-23.59"));
        events.add(new Event("study","8/4/2021","00.00-23.59"));
        events.add(new Event("study","8/4/2021","00.00-23.59"));
        events.add(new Event("study","8/4/2021","00.00-23.59"));
        events.add(new Event("study","8/4/2021","00.00-23.59"));
        events.add(new Event("study","8/4/2021","00.00-23.59"));
        events.add(new Event("study","8/4/2021","00.00-23.59"));
        events.add(new Event("study","8/4/2021","00.00-23.59"));
        events.add(new Event("study","8/4/2021","00.00-23.59"));
        events.add(new Event("study","8/4/2021","00.00-23.59"));
        events.add(new Event("study","1/4/2021","00.00-23.59"));
        events.add(new Event("study","2/4/2021","00.00-23.59"));
        events.add(new Event("study","16/4/2021","00.00-23.59"));
        events.add(new Event("study","17/4/2021","00.00-23.59"));


        myAdapter=new EventAdapter(MainActivity.this,DailyEvents);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date= Integer.toString(dayOfMonth)+"/"+Integer.toString(month+1)+"/"+Integer.toString(year);
                int count=0;
                DailyEvents.clear();
                for(int a=0;a<events.size();a++){

                    if(date.equals(events.get(a).getDate())){
                        DailyEvents.add(events.get(a));
                    }

                    myAdapter.notifyDataSetChanged();

                }

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        recyclerView.setAdapter(myAdapter);
    }

    @Override
    public void onItemClicked(int index) {

    }
}