package com.example.takvim;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

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
    String Daate;
    Event pickedEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        events=new ArrayList<Event>();
        loadData();
        @SuppressLint("SimpleDateFormat") String currentDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        if(Character.compare(currentDate.charAt(3), '0')==0) {
            currentDate = currentDate.substring(0, 3) + currentDate.substring(3 + 1);
        }

        setContentView(R.layout.activity_main);

        DailyEvents=new ArrayList<Event>();

        for(int a=0;a<events.size();a++){

            if(currentDate.equals(events.get(a).getDate())){
                DailyEvents.add(events.get(a));
            }


            Daate =currentDate;

        }
        myAdapter=new EventAdapter(MainActivity.this,DailyEvents);
        calendarView=findViewById(R.id.calendarView);
        recyclerView=findViewById(R.id.recyc);
        cardView=findViewById(R.id.cv);

        layoutManager= new GridLayoutManager(this,1, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        btnAdd=findViewById(R.id.btnAdd);


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
                    Daate =date;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch (item.getOrder()){
            case 0:

                for(int i=0;i<events.size();i++){
                    if(events.get(i).equals(DailyEvents.get(item.getGroupId()))){
                        Event event= events.get(i);
                        String lineToRemove=event.getEvent_name()+","+event.getDate()+","+event.getTimeStart()+","+event.getTimeEnd();

                        String f="events.txt";

                        File tmp = null;
                        try {
                            tmp = File.createTempFile("tmp", "");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        BufferedReader br = null;
                        try {
                            br = new BufferedReader(new InputStreamReader(openFileInput("events.txt")));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        BufferedWriter bw = null;
                        try {
                            bw = new BufferedWriter(new FileWriter(tmp));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        String l= null;
                        try {
                            l = br.readLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        while (l!=null){
                            if (!(l.equals(lineToRemove))) {
                                try {
                                    bw.write(String.format("%s%n", l));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            try {
                                l = br.readLine();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                        try {
                            br.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            bw.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        File oldFile =getApplicationContext().getFileStreamPath("events.txt");
                        if (oldFile.delete())
                            tmp.renameTo(oldFile);
                        events.remove(i);

                    }
                }
                DailyEvents.remove(item.getGroupId());
                myAdapter.notifyItemRemoved(item.getGroupId());
                myAdapter.notifyItemRangeChanged(item.getGroupId(),DailyEvents.size());
                return true;
            case 1:
                String EventName=DailyEvents.get(item.getGroupId()).getEvent_name();
                String TimeStart=DailyEvents.get(item.getGroupId()).getTimeStart();
                String TimeEnd=DailyEvents.get(item.getGroupId()).getTimeEnd();
                Intent i=new Intent(MainActivity.this,AddEvent.class);
                i.putExtra("CallingReason","Edit");
                i.putExtra("EventName",EventName);
                i.putExtra("TimeStart",TimeStart);
                i.putExtra("TimeEnd",TimeEnd);
                startActivityForResult(i,EditEvent);
                pickedEvent=DailyEvents.get(item.getGroupId());
                return true;
            default:
                return super.onContextItemSelected(item);

        }


    }
    public void loadData(){
        events.clear();
        File file=getApplicationContext().getFileStreamPath("events.txt");
        String lineFromFile;

        if (file.exists()){
            try{
                BufferedReader reader= new BufferedReader(new InputStreamReader(openFileInput("events.txt")));
                while ((lineFromFile=reader.readLine())!=null){
                    StringTokenizer tokens= new StringTokenizer(lineFromFile,",");
                    Event event= new Event(tokens.nextToken(),tokens.nextToken(),tokens.nextToken(),tokens.nextToken());
                    events.add(event);
                }
                reader.close();
            }
            catch(IOException e){
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    public void onItemClicked(int index) { }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==AddEvent){
            if(resultCode==RESULT_OK){
                Event newEvent = new Event();
                newEvent.setEvent_name(data.getStringExtra("EventName"));
                newEvent.setDate(Daate);
                newEvent.setTimeStart(data.getStringExtra("TimeStart"));
                newEvent.setTimeEnd(data.getStringExtra("TimeEnd"));
                events.add(newEvent);
                DailyEvents.add(newEvent);
                myAdapter.notifyDataSetChanged();

                try{
                    FileOutputStream inputFile= openFileOutput("events.txt",MODE_PRIVATE);
                    OutputStreamWriter outputFile = new OutputStreamWriter(inputFile);

                    for(int i=0;i<events.size();i++){
                        outputFile.write(events.get(i).getEvent_name()+","+events.get(i).getDate()+","+events.get(i).getTimeStart()+","+events.get(i).getTimeEnd()+"\n");
                    }
                    outputFile.flush();
                    outputFile.close();

                    Toast.makeText(MainActivity.this,"Event Successfully Saved!",Toast.LENGTH_SHORT).show();
                }
                catch(IOException e){
                    Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }


        }
    }
        if(requestCode==EditEvent){
            if(resultCode==RESULT_OK){
                pickedEvent.setEvent_name(data.getStringExtra("EventName"));
                pickedEvent.setTimeStart(data.getStringExtra("TimeStart"));
                pickedEvent.setTimeEnd(data.getStringExtra("TimeEnd"));
                myAdapter.notifyDataSetChanged();
            }
        }
} }
