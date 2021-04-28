package com.example.takvim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddEvent extends AppCompatActivity {
    Button btnAddEvent;
    EditText etName,etDate,etTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        btnAddEvent=findViewById(R.id.btnAddEvent);
        etName=findViewById(R.id.etName);
        etTime=findViewById(R.id.etTime);

        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etName.getText().toString().isEmpty()||etTime.getText().toString().isEmpty()){
                    Toast.makeText(AddEvent.this,"Please enter all fields",Toast.LENGTH_SHORT).show();
                }
                else{
                    String EventName=etName.getText().toString().trim();
                    String Time=etTime.getText().toString().trim();
                    Intent i= new Intent();
                    i.putExtra("EventName",EventName);
                    i.putExtra("Time",Time);
                    setResult(RESULT_OK,i);
                    AddEvent.this.finish();




                }
            }
        });




    }
}