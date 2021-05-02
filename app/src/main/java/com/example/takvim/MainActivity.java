package com.example.takvim;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements EventAdapter.ItemClicked {
    CalendarView calendarView;
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Event> events;
    ArrayList<Event> DailyEvents;
    ImageView btnAdd;
    CardView cardView;

    int AddEvent=1;
    int EditEvent=2;
    String Date;
    Event pickedEvent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DailyEvents=new ArrayList<Event>();

        calendarView=findViewById(R.id.calendarView);
        recyclerView=findViewById(R.id.recyc);
        cardView=findViewById(R.id.cv);

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
                    Date=date;

                }

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,AddEvent.class);
                i.putExtra("CallingReason","Add");
                startActivityForResult(i,AddEvent);

            }
        });


        recyclerView.setAdapter(myAdapter);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch (item.getOrder()){
            case 0:

                for(int i=0;i<events.size();i++){
                    if(events.get(i).equals(DailyEvents.get(item.getGroupId()))){
                        events.remove(i);
                    }
                }
                DailyEvents.remove(item.getGroupId());
                myAdapter.notifyItemRemoved(item.getGroupId());
                myAdapter.notifyItemRangeChanged(item.getGroupId(),DailyEvents.size());
                return true;
            case 1:
                String EventName=DailyEvents.get(item.getGroupId()).getEvent_name();
                String Time=DailyEvents.get(item.getGroupId()).getTime_interval();
                Intent i=new Intent(MainActivity.this,AddEvent.class);
                i.putExtra("CallingReason","Edit");
                i.putExtra("EventName",EventName);
                i.putExtra("Time",Time);
                startActivityForResult(i,EditEvent);
                pickedEvent=DailyEvents.get(item.getGroupId());
                return true;
            default:
                return super.onContextItemSelected(item);

        }


    }

    @Override
    public void onItemClicked(int index) {


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==AddEvent){
            if(resultCode==RESULT_OK){
                Event newEvent = new Event();
                newEvent.setEvent_name(data.getStringExtra("EventName"));
                newEvent.setDate(Date);
                newEvent.setTime_interval(data.getStringExtra("Time"));
                events.add(newEvent);
                DailyEvents.add(newEvent);


                myAdapter.notifyDataSetChanged();


        }
    }
        if(requestCode==EditEvent){
            if(resultCode==RESULT_OK){
                pickedEvent.setEvent_name(data.getStringExtra("EventName"));
                pickedEvent.setTime_interval(data.getStringExtra("Time"));
                myAdapter.notifyDataSetChanged();
            }
        }
} }