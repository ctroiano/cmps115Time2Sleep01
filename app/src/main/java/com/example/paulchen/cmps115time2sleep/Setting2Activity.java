package com.example.paulchen.cmps115time2sleep;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;




public class Setting2Activity extends Activity {

    public static final String timeSettings = "com.sleepTime.timeSettings";


    boolean touch = false;
    TextView when;
    TextView helper;
    int remindTime = 30;
    String TextMessage = "It's time for you to get ready and sleep!";
    EditText customText;
    String customMessage;
    String saveMessage;
    BroadcastReceiver broadcast_reciever;
    boolean existingProfile = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final SharedPreferences pref = getSharedPreferences(timeSettings, Context.MODE_PRIVATE);

        final SharedPreferences.Editor editor = pref.edit();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting2);
        //passed through variables
        Bundle bundle = getIntent().getExtras();



        final int hoursSetting = bundle.getInt("hoursSetting");
        final int minutesSetting = bundle.getInt("minutesSetting");
        final int sleepTime = bundle.getInt("sleepTime");
        final int check = bundle.getInt("check");
        final String profileName = bundle.getString("profileName");
        saveMessage = pref.getString(profileName + "customMessage", "");
        remindTime = pref.getInt(profileName + "remindTime", 0);


        when = (TextView)findViewById(R.id.when);
        helper = (TextView)findViewById(R.id.helper);
        String[] remind = new String[60];
        for(int i = 0; i <= 59; i++){
            if(i < 10)  remind[i] = "0" + i;
            else remind[i] = "" + i;
        }

        // numberPicker that displays reminder time
        final NumberPicker n1 = (NumberPicker)findViewById(R.id.numberPickerTime);
        n1.setMinValue(0);
        n1.setMaxValue(59);
        n1.setDisplayedValues(remind);
        System.out.println(remindTime);
        n1.setValue(remindTime);


        n1.setWrapSelectorWheel(true);
        n1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                //if (!touch) when.setText("I want to be reminded 0 minute before I go to bed");
                //touch = true;
                if (newVal == 0){
                    when.setText("Remind me to go to bed");
                }
                else if (newVal == 1) {
                    when.setText("Remind me " + newVal + " minute before I sleep");
                    helper.setText("minute");
                }
                else {
                    when.setText("Remind me " + newVal + " minutes before I sleep");
                    helper.setText("minutes");
                }
            }
        });

        customText = (EditText)findViewById(R.id.editText);

        if(saveMessage =="") {
            customText.setText(TextMessage);
        }
        else
        {
            customText.setText(saveMessage);
        }
        //button that moves on to next activity
        final Button buttonFinished = (Button) findViewById(R.id.buttonFinished);
        buttonFinished.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                // Change to the Alarm Setup Activity
                remindTime = n1.getValue();

                customMessage = customText.getText().toString();
                saveMessage = customMessage;


                Intent intent = new Intent(Setting2Activity.this, AlarmActivity.class);
                intent.putExtra("hoursSetting", hoursSetting);
                intent.putExtra("minutesSetting", minutesSetting);
                intent.putExtra("sleepTime", sleepTime);
                intent.putExtra("remindTime", remindTime);
                intent.putExtra("customMessage", customMessage);
                intent.putExtra("check",check);

                editor.putInt(profileName + "remindTime", remindTime);
                editor.putString(profileName + "customMessage", saveMessage);
                editor.commit();

                // System.out.println(remindTime);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setting2, menu);
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