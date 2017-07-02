package com.darte.vaibhav.anti_detention;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class Reminders extends AppCompatActivity implements AdapterView.OnItemSelectedListener{



    CheckBox ch1;
    Spinner sp2;
    Button b1,b2;
    DatabaseHelper mydbhelper;
    TextView tv1;
    Calendar cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mydbhelper=new DatabaseHelper(this);
        sp2= (Spinner) findViewById(R.id.reminderspinner);
        sp2.setOnItemSelectedListener(this);
        b1= (Button) findViewById(R.id.addtime);
        b2= (Button) findViewById(R.id.cancel);
        tv1= (TextView) findViewById(R.id.timetext);
        cal=Calendar.getInstance();
        ch1= (CheckBox) findViewById(R.id.repeatcheck);


        loadsp2();
        addtime();


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(Reminders.this,Mytrigger.class);
                PendingIntent p1=PendingIntent.getBroadcast(Reminders.this,0,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.cancel(p1);
                Toast.makeText(getApplicationContext(),"Alarm Cancelled",Toast.LENGTH_SHORT).show();
            }
        });


    }



    private void loadsp2()
    {
        DatabaseHelper mydbar=new DatabaseHelper(getApplicationContext());

        List<String> allSubjects =mydbar.getallSubject();
        ArrayAdapter<String> dataAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,allSubjects);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(dataAdapter);
    }



    public void addtime()
    {
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Reminders.this, "test toast", Toast.LENGTH_LONG).show();
                int min = cal.get(Calendar.MINUTE);
                int hr = cal.get(Calendar.HOUR_OF_DAY);
                new TimePickerDialog(Reminders.this, tl1, 0, 0, false).show();
            }
        });
    }


TimePickerDialog.OnTimeSetListener tl1=new TimePickerDialog.OnTimeSetListener() {
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
      /*  cal.set(Calendar.HOUR_OF_DAY,hourOfDay);
        cal.set(Calendar.MINUTE,minute);
        cal.set(Calendar.SECOND,0);
        tv1.setText(hourOfDay+" : "+ minute);*/
        String subname=sp2.getSelectedItem().toString();
      //  mydbhelper.addtime(subname,hourOfDay,minute);
        setcal(hourOfDay, minute);
    }
};


    public void setcal(int hour,int minute)
    {
       cal.set(Calendar.MINUTE,hour);
       cal.set(Calendar.MINUTE,minute);
        cal.set(Calendar.SECOND,0);
        tv1.setText(to12(hour)+" : "+ minute);
        setalarm();
    }

    public void setalarm(){
        Intent intent1=new Intent(Reminders.this,Mytrigger.class);
        PendingIntent p1=PendingIntent.getBroadcast(Reminders.this,0,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
        if(ch1.isChecked()){
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),7*(AlarmManager.INTERVAL_DAY), p1);
            Toast.makeText(this,"one minute reminder set",Toast.LENGTH_LONG).show();
        }
        else{
            alarmManager.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),p1);
            Toast.makeText(this,"One time reminder",Toast.LENGTH_LONG).show();
        }



    }

    public int to12(int hour)
    {
        if (hour>12){
            return hour-12;
        }
        else{
            return hour;
        }

    }




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
