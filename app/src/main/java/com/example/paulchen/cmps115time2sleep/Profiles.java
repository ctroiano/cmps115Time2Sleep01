package com.example.paulchen.cmps115time2sleep;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;



public class Profiles extends Activity {

    //Keep track of the numberOfProfiles
    private int numberOfProfiles = 0;

    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listItems=new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;


    final int check = 1;
    final Context context = this;

    private String result = "";



    //Don't change this. This is the profileSettings.xml that will hold the key-value pairs for
    //reloading the profiles.
    public static final String profileSettings = "com.sleepTime.profileSettings";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        final SharedPreferences pref = getSharedPreferences(profileSettings, Context.MODE_PRIVATE);

        final SharedPreferences.Editor editor = pref.edit();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        ListView lv = (ListView) findViewById(R.id.profileList);

        lv.setAdapter(adapter);
        numberOfProfiles = pref.getInt("numberOfProfiles", 0);

        if(numberOfProfiles > -1)
        {

            populateProfileList(lv);
        }
        //OnClickListener for the individual profiles
        //Allows for each profile to have its own setting
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Profiles.this, SettingActivity.class);
                intent.putExtra("profileName", listItems.get(position).toString());
                startActivity(intent);
            }
        });
        //OnClickListener for each of the profiles
        //Prompts the user with a delete option
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Profiles.this, "Profile has been removed", Toast.LENGTH_SHORT);

                final int listPosition = position;

                adapter.notifyDataSetChanged();
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.prompt_delete, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setView(promptsView);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Delete",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String toRemove = listItems.get(listPosition);
                                        listItems.remove(listPosition);
                                        numberOfProfiles--;
                                        adapter.notifyDataSetChanged();
                                        for(int i = 0; i < 10; i++)
                                        {
                                            String profileName = pref.getString("profile" + i,"");
                                            if(profileName == "")
                                            {
                                                //do nothing
                                            }
                                            else if (toRemove == profileName)
                                            {
                                                editor.remove("profile" + i);
                                                int temp = pref.getInt("numberOfProfiles", numberOfProfiles);
                                                temp--;
                                                editor.putInt("numberOfProfiles", numberOfProfiles);
                                                editor.commit();
                                                break;
                                            }
                                        }
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                adapter.notifyDataSetChanged();
                return false;
            }
        });

    }
    public void populateProfileList(View v) {
        final SharedPreferences pref = getSharedPreferences(profileSettings, Context.MODE_PRIVATE);

        final SharedPreferences.Editor editor = pref.edit();

        LayoutInflater li = LayoutInflater.from(context);

        View promptsView = li.inflate(R.layout.prompt, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setView(promptsView);

        //This for loop will check if numberOfProfiles is bigger than 1.
        //If true then it will load all the saved profiles
        //Might need to tweak "profile" + 1 depending on how we want the key to be saved
        for( int i = 0; i <= 10; i++)
        {

            String profileName = pref.getString("profile" + i, "");
            System.out.println(profileName);
            if(profileName == "")
            {
                //Do Nothing
            }
            else {
                listItems.add(profileName);
                adapter.notifyDataSetChanged();
            }
        }
    }
    /*
        Below method is called from activity_profiles.xml. It will add a profile to the
        ListView and then proceed into the settings for that profile. The user is allowed
        to create a personal tag for each of the profiles
     */
    public void addItems(View v) {

        final SharedPreferences pref = getSharedPreferences(profileSettings, Context.MODE_PRIVATE);

        final SharedPreferences.Editor editor = pref.edit();

        LayoutInflater li = LayoutInflater.from(context);

        View promptsView = li.inflate(R.layout.prompt, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView.findViewById(
                R.id.editTextDialogUserInput);
        //set Dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Enter Profile Name",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //get user input and set it to result
                                //edit text
                                result = userInput.getText().toString();
                                listItems.add(result);
                                adapter.notifyDataSetChanged();

                                numberOfProfiles++;



                                editor.putInt("numberOfProfiles", numberOfProfiles);
                                editor.putString("profile" + numberOfProfiles, result);
                                editor.commit();



                                //Here I am passing the newly added profile name to the settings activity
                                Intent intent = new Intent(Profiles.this, SettingActivity.class);
                                intent.putExtra("check",check);
                                intent.putExtra("profileName", userInput.getText().toString());
                                startActivity(intent);
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
        adapter.notifyDataSetChanged();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profiles, menu);
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
