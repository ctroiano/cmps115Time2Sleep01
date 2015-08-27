package com.example.paulchen.cmps115time2sleep;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;


public class SettingActivity extends Activity {

    int hourSetting;
    int minuteSetting;
    int sleepTime;
    int AMorPM;

    TextView when;
    TextView min;
    TextView how;
    TextView am;
    boolean touch = false;
    boolean existingProfile = false;

    String profileName = null;

    public static final String timeSettings = "com.sleepTime.timeSettings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final SharedPreferences pref = getSharedPreferences(timeSettings, Context.MODE_PRIVATE);

        final SharedPreferences.Editor editor = pref.edit();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting);

        if( getIntent().getExtras() == null)
        {
            //Do nothing
        }
        else
        {
            Bundle extras = getIntent().getExtras();
            profileName = extras.getString("profileName");
            hourSetting = pref.getInt(profileName + "_" + "Hour_Setting", 0);
            minuteSetting = pref.getInt(profileName + "_" + "Minute_Setting", 0);
            sleepTime = pref.getInt(profileName + "_" + "Sleep_Time", 0);
            AMorPM = pref.getInt(profileName + "_" + "AMorPM", 0);
            existingProfile = true;
        }

        Bundle bundle = getIntent().getExtras();
        final int check = bundle.getInt("check");
        String[] digits = new String[60];
        for(int i = 0; i <= 59; i++){
            if(i < 10)  digits[i] = "0" + i;
            else digits[i] = "" + i;
        }

        String[] hrs = new String[25];
        for(int i = 0; i <= 24; i++){
            if (i < 10) hrs[i] = "0" + i;
            else hrs[i] = "" + i;
        }

        String[] ampm = new String[2];
        ampm[0] = "AM";
        ampm[1] = "PM";

        // dynamic text
        when = (TextView)findViewById(R.id.when);
        min = (TextView)findViewById(R.id.min);
        how = (TextView)findViewById(R.id.how);
        am = (TextView)findViewById(R.id.am);

        // hour
        final NumberPicker n1 = (NumberPicker)findViewById(R.id.numberPicker1);
        n1.setMaxValue(12);
        n1.setMinValue(1);
        if(existingProfile) {
            n1.setValue(hourSetting);

        }
        n1.setWrapSelectorWheel(true);
        n1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (!touch) min.setText(" : 00");
                when.setText("I want to wake up at " + newVal);
                touch = true;
            }
        });



        // minute
        final NumberPicker n2 = (NumberPicker)findViewById(R.id.numberPicker2);
        n2.setMinValue(0);
        n2.setMaxValue(59);
        if(existingProfile) {
            n2.setValue(minuteSetting);

        }
        n2.setDisplayedValues(digits);
        n2.setWrapSelectorWheel(true);
        n2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (!touch) when.setText("I want to wake up at 1");
                touch = true;
                if (newVal < 10) {
                    min.setText(" : 0" + newVal);
                }
                if (newVal >= 10) {
                    min.setText(" : " + newVal);
                }
            }
        });

        // I want to sleep for...
        final NumberPicker n3 = (NumberPicker)findViewById(R.id.numberPicker3);
        n3.setMinValue(0);
        n3.setMaxValue(24);
        if(existingProfile) {
            n3.setValue(sleepTime);

        }
        n3.setDisplayedValues(hrs);
        n3.setValue(8);
        n3.setWrapSelectorWheel(true);
        n3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (newVal == 0){
                    how.setText("I don't want to sleep");
                }else if (newVal == 1){
                    how.setText("I want to sleep for " + newVal + " hour");
                }else if(newVal > 1){
                    how.setText("I want to sleep for " + newVal + " hours");
                }
            }
        });

        final NumberPicker n4 = (NumberPicker)findViewById(R.id.numberPickerAM);
        n4.setMinValue(0);
        n4.setMaxValue(1);
        if(existingProfile) {
            n4.setValue(AMorPM);

        }
        n4.setDisplayedValues(ampm);
        n4.setWrapSelectorWheel(true);
        n4.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (newVal == 0){
                    am.setText("AM");
                }else {
                    am.setText("PM");
                }
            }
        });

        final Button buttonContinue = (Button) findViewById(R.id.buttonContinue);
        buttonContinue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                // Change to the Alarm Setup Activity

                hourSetting = n1.getValue();
                minuteSetting = n2.getValue();
                sleepTime = n3.getValue();
                AMorPM = n4.getValue();


                if(n4.getValue()==1){
                    hourSetting = hourSetting + 12;
                    if(hourSetting >= 24){
                        hourSetting = 0;
                    }
                }



                Intent intent = new Intent(SettingActivity.this, Setting2Activity.class);
                intent.putExtra("hoursSetting", hourSetting);
                intent.putExtra("minutesSetting", minuteSetting);
                intent.putExtra("sleepTime", sleepTime);
                intent.putExtra("check", check);
                intent.putExtra("profileName", profileName);

                editor.putInt(profileName + "_" + "Hour_Setting", hourSetting);
                editor.putInt(profileName + "_" + "Minute_Setting", minuteSetting);
                editor.putInt(profileName + "_" + "Sleep_Time", sleepTime);
                editor.putInt(profileName + "_" + "AMorPM", AMorPM);
                editor.commit();

                startActivity(intent);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setting, menu);
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