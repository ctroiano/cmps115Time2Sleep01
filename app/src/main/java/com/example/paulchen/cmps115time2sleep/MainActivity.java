package com.example.paulchen.cmps115time2sleep;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final int check = 0;
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.MainActivity);
        layout.setBackgroundResource(R.drawable.time2sleep);

        final Button setTime = (Button) findViewById(R.id.buttonTest);
        setTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                // Change to the Alarm Setup Activity
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                intent.putExtra("check",check);
                startActivity(intent);
            }
        });

        final Button setProfile = (Button) findViewById(R.id.buttonProfiles);
        setProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent profileIntent = new Intent(MainActivity.this, Profiles.class);
                startActivity(profileIntent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
