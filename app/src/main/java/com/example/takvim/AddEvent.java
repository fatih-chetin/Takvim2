package com.example.takvim;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Locale;

public class AddEvent extends AppCompatActivity {
    Button btnAddEvent;
    EditText etName;
    TextView tvTimeStart,tvTimeEnd;
    int hour,minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        btnAddEvent=findViewById(R.id.btnAddEvent);
        etName=findViewById(R.id.etName);
        tvTimeStart=findViewById(R.id.tvTimeStart);
        tvTimeEnd=findViewById(R.id.tvTimeEnd);



        if(getIntent().getExtras().getString("CallingReason").equals("Edit")){
            etName.setText(getIntent().getExtras().getString("EventName"));
            tvTimeStart.setText(getIntent().getExtras().getString("TimeStart"));
            tvTimeEnd.setText(getIntent().getExtras().getString("TimeEnd"));
        }



        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etName.getText().toString().isEmpty()||tvTimeStart.getText().toString().isEmpty()){
                    Toast.makeText(AddEvent.this,"Please enter all fields",Toast.LENGTH_SHORT).show();
                }
                else{
                    String EventName=etName.getText().toString().trim();
                    String TimeStart=tvTimeStart.getText().toString().trim();
                    String TimeEnd=tvTimeEnd.getText().toString().trim();
                    Intent i= new Intent();
                    i.putExtra("EventName",EventName);
                    i.putExtra("TimeStart",TimeStart);
                    i.putExtra("TimeEnd",TimeEnd);
                    setResult(RESULT_OK,i);
                    AddEvent.this.finish();




                }
            }
        });




    }

    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourSelected, int minuteSelected) {
                hour=hourSelected;
                minute=minuteSelected;

                tvTimeStart.setText(String.format(Locale.getDefault(),"%02d:%02d", hourSelected, minuteSelected));




            }
        };
        TimePickerDialog timePickerDialog= new TimePickerDialog(this,onTimeSetListener,hour,minute,true);
        timePickerDialog.show();
    }
    public void popTimePicker2(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourSelected, int minuteSelected) {
                hour=hourSelected;
                minute=minuteSelected;

                tvTimeEnd.setText(String.format(Locale.getDefault(),"%02d:%02d", hourSelected, minuteSelected));




            }
        };
        TimePickerDialog timePickerDialog= new TimePickerDialog(this,onTimeSetListener,hour,minute,true);
        timePickerDialog.show();
    }
}