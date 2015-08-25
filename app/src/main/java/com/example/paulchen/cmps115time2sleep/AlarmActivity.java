package com.example.paulchen.cmps115time2sleep;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Calendar;


public class AlarmActivity extends Activity {
    String ampmtime = "am";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        int hourDisplayed;
        String minuteDisplayed;

        Bundle bundle = getIntent().getExtras();
        int hoursSetting = bundle.getInt("hoursSetting");
        int minutesSetting = bundle.getInt("minutesSetting");
        final int sleepTime = bundle.getInt("sleepTime");
        final int remindTime = bundle.getInt("remindTime");
        final String customMessage = bundle.getString("customMessage");


        hoursSetting = hoursSetting - sleepTime;
        if(hoursSetting <0){
            hoursSetting = 24 + hoursSetting;
        }
        minutesSetting = minutesSetting - remindTime;
        if(minutesSetting<0){
            minutesSetting = 60+minutesSetting;
            hoursSetting--;
        }

        //displayed value for toast message


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        System.out.println(hoursSetting+":"+minutesSetting);
        calendar.set(Calendar.HOUR_OF_DAY, hoursSetting);
        calendar.set(Calendar.MINUTE, minutesSetting);
        System.out.println(hoursSetting+":"+minutesSetting);

        Intent intentAlarm = new Intent(this, AlarmRecieve.class).putExtra("customMessage", customMessage);
        AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        long time = calendar.getTimeInMillis();
        System.out.println(time);


        Calendar currentTime = Calendar.getInstance();
        currentTime.setTimeInMillis(System.currentTimeMillis());




        long currTime = currentTime.getTimeInMillis();
        //print4
        System.out.println(currTime);
        if(time<currTime){
            time =   1000*60*60*24+time;
        }

        am.set(AlarmManager.RTC_WAKEUP,time, PendingIntent.getBroadcast(this, 1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));

        //clean up the displayed time for Toast message
        if(hoursSetting>12){
            hourDisplayed = hoursSetting-12;
            ampmtime = "pm";

        }else{
            hourDisplayed = hoursSetting;
        }


        if(minutesSetting<10){
            minuteDisplayed = "0"+minutesSetting;
        }else{
            minuteDisplayed = ""+minutesSetting;
        }

        String message = "Alarm Scheduled for: "+hourDisplayed+":"+minuteDisplayed + ampmtime;
        System.out.println(message);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        //clear all previous activites and return to home page
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alarm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
