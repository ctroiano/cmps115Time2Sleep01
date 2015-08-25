package com.example.paulchen.cmps115time2sleep;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import org.w3c.dom.Text;


public class Setting2Activity extends Activity {
    boolean touch = false;
    TextView when;
    TextView min;
    int remindTime = 15;
    String TextMessage = "It's time for you to get ready and sleep!";
    EditText customText;
    String customMessage;
    BroadcastReceiver broadcast_reciever;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting2);
        //passed through variables
        Bundle bundle = getIntent().getExtras();

        final int hoursSetting = bundle.getInt("hoursSetting");
        final int minutesSetting = bundle.getInt("minutesSetting");
        final int sleepTime = bundle.getInt("sleepTime");




        when = (TextView)findViewById(R.id.when);
        min = (TextView)findViewById(R.id.min);
        String[] remind = new String[60];
        for(int i = 0; i <= 59; i++){
            if(i < 10)  remind[i] = "0" + i;
            else remind[i] = "" + i;
            //System.out.println(sleepTime[i]);
        }
        // numberPicker that displays reminder time
        final NumberPicker n2 = (NumberPicker)findViewById(R.id.numberPickerTime);
        n2.setMinValue(0);
        n2.setMaxValue(59);
        n2.setDisplayedValues(remind);
        n2.setWrapSelectorWheel(true);
        n2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (!touch) when.setText("I want to be reminded");
                touch = true;
                if (newVal < 2) {
                    min.setText(" " + newVal+" minute before I sleep");
                }
                else {
                    min.setText(" " + newVal+" minutes before I sleep");
                }
            }
        });
        customText = (EditText)findViewById(R.id.editText);
        customText.setText(TextMessage);

        //button that moves on to next activity
        final Button buttonFinished = (Button) findViewById(R.id.buttonFinished);
        buttonFinished.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                // Change to the Alarm Setup Activity
                remindTime = n2.getValue();

                customMessage = customText.getText().toString();



                Intent intent = new Intent(Setting2Activity.this, AlarmActivity.class);
                intent.putExtra("hoursSetting", hoursSetting);
                intent.putExtra("minutesSetting", minutesSetting);
                intent.putExtra("sleepTime", sleepTime);
                intent.putExtra("remindTime", remindTime);
                intent.putExtra("customMessage", customMessage);
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