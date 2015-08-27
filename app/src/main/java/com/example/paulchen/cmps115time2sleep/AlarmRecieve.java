package com.example.paulchen.cmps115time2sleep;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

/**
 * Created by Paul Chen on 8/19/2015.
 */
public class AlarmRecieve extends BroadcastReceiver {


    /**
     * Created by Paul Chen on 8/19/2015.
     */

    @Override
    public void onReceive(Context context, Intent intent)
    {
        // TODO Auto-generated method stub



        // for ex you can start an activity to vibrate phone or to ring the phone
        TelephonyManager tMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tMgr.getLine1Number();


        String message= intent.getExtras().getString("customMessage");// message to send
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(mPhoneNumber, null, message, null, null);
        // Show the toast  like in above screen shot
        //Toast.makeText(context, "Alarm Triggered and SMS Sent", Toast.LENGTH_LONG).show();


    }
}


