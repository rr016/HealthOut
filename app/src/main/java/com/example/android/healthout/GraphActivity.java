package com.example.android.healthout;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.healthout.dataEntities.AppLog;
import com.example.android.healthout.dataEntities.User;
import com.example.android.healthout.database.DatabaseHelper;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GraphActivity extends AppCompatActivity {
    DatabaseHelper db;

    User user;
    long app_id;
    long type_id;
    long period_id;
    String target_value;

    public List<AppLog> appLogList = new ArrayList<AppLog>();
    public List<AppLog> appLogListToBeGraphed = new ArrayList<AppLog>();

    LineGraphSeries<DataPoint> logDataLine;
    LineGraphSeries<DataPoint> targetValueLine;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        user = (User)extras.getSerializable("user");
        app_id = extras.getLong("app_id");
        type_id = extras.getLong("type_id");
        period_id = extras.getLong("period_id");
        target_value = extras.getString("target_value");

        appLogList = db.getAppLogListFromLogTable(user.getUser_id(), app_id);

        switch ((int)type_id){
            case 1:
                try {
                    appLogListToBeGraphed = createDailyAppLogList(appLogList);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                //dataDouble = appLogList.get(i).getMiles_walked();
                break;
            case 3:
                //dataLong = appLogList.get(i).getCalories_burned();
                break;
            case 4:
                //dataLong = appLogList.get(i).getCalories_consumed();
                break;
        }

        long dataLong = -1;
        double dataDouble = -1.0;
        String dataString = null;

        GraphView graph = (GraphView) findViewById(R.id.graph);
        logDataLine = new LineGraphSeries<DataPoint>();
        targetValueLine = new LineGraphSeries<DataPoint>();

        int entries = appLogListToBeGraphed.size();
        for(int i = 0; i < entries; i++){
            switch ((int)type_id){
                case 1:
                    dataLong = appLogListToBeGraphed.get(i).getSteps_walked();
                    break;
                case 2:
                    dataDouble = appLogListToBeGraphed.get(i).getMiles_walked();
                    break;
                case 3:
                    dataLong = appLogListToBeGraphed.get(i).getCalories_burned();
                    break;
                case 4:
                    dataLong = appLogListToBeGraphed.get(i).getCalories_consumed();
                    break;
                case 5:
                    dataLong = appLogListToBeGraphed.get(i).getPulse();
                    break;
                case 6:
                    dataString = appLogListToBeGraphed.get(i).getBlood_pressure();
                    break;
            }

            if (type_id == 1 || (type_id >=3 && type_id <=5))
                logDataLine.appendData(new DataPoint(i, dataLong), true, entries);
            else if (type_id == 2)
                logDataLine.appendData(new DataPoint(i, dataDouble), true, entries);
            else if (type_id == 6)
                logDataLine.appendData(new DataPoint(i, Long.valueOf(dataString)), true, entries);

            targetValueLine.appendData(new DataPoint(i, Integer.valueOf(target_value)), true, entries); // striaght line at target value
        }

        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        if (type_id == 1){
            gridLabel.setHorizontalAxisTitle("Days");
            gridLabel.setVerticalAxisTitle("Steps Walked");
        }
        if (type_id == 2){
            gridLabel.setHorizontalAxisTitle("Days");
            gridLabel.setVerticalAxisTitle("Miles Walked");
        }
        if (type_id == 3){
            gridLabel.setHorizontalAxisTitle("Days");
            gridLabel.setVerticalAxisTitle("Calories Burned");
        }
        if (type_id == 4){
            gridLabel.setHorizontalAxisTitle("Days");
            gridLabel.setVerticalAxisTitle("Calories Consumed");
        }
        if (type_id == 5){
            gridLabel.setHorizontalAxisTitle("Days");
            gridLabel.setVerticalAxisTitle("Pulse");
        }

        targetValueLine.setColor(Color.rgb(255, 0, 0));
        graph.addSeries(logDataLine);
        graph.addSeries(targetValueLine);
    }

    /************************ MENU BAR ************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Home - Main Menu
            case R.id.home:
                Intent moveToMainMenu = new Intent(GraphActivity.this, MainMenuActivity.class).putExtra("user", user);
                startActivity(moveToMainMenu);
                return true;

            // Logout
            case R.id.one:
                new AlertDialog.Builder(this)
                        .setTitle("Logout")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent moveToLogin = new Intent(GraphActivity.this, LoginActivity.class);
                        // Prevent user from returning to this page
                        moveToLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(moveToLogin);
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert).show();

                return true;

            // Edit Account
            case R.id.two:
                Intent moveToEditAccount = new Intent(GraphActivity.this, EditAccountActivity.class).putExtra("user", user);
                startActivity(moveToEditAccount);
                return true;

            // Delete Account
            case R.id.three:
                new AlertDialog.Builder(this)
                        .setTitle("Confirm Delete")
                        .setMessage("Are you sure you want to delete this account?")
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "USER_ID = "+user.getUser_id(), Toast.LENGTH_LONG).show();
                        db.deleteAccountFromUserTable(user.getUser_id());

                        Intent moveToLogin = new Intent(GraphActivity.this, LoginActivity.class);
                        // Prevent user from returning to this page
                        moveToLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(moveToLogin);
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert).show();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public List<AppLog> createDailyAppLogList(List<AppLog> appLogList) throws ParseException {
        List<AppLog> dailyAppLogList = new ArrayList<AppLog>();

        for (int i = -7; i < 0; i++){
            dailyAppLogList.add(appLogList.get(appLogList.size() + i - 1));
        }

        /*
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date testDate = sdf.parse(user.getCalculatedDate("yyyy-MM-dd", -7));

        int startingPoint = -1;

        // Find starting appLog
        for (int i = 0; i < appLogList.size(); i++){
            Date logDate = sdf.parse(appLogList.get(i).getDate());
            if (testDate.before(logDate)){
                startingPoint = i;
                Toast.makeText(getApplicationContext(), "" + startingPoint, Toast.LENGTH_LONG).show();
                break;
            }
        }

        if (startingPoint > -1){
            for (int i = -7; i < 0; i++){
                Date dayOfWeek = sdf.parse(user.getCalculatedDate("yyyy-MM-dd", i));
                Date logDate = sdf.parse(appLogList.get(appLogList.size() + i).getDate());
                Toast.makeText(getApplicationContext(), "" + ((new Date().getTime() - dayOfWeek.getTime())/(24*60*60*1000)), Toast.LENGTH_LONG).show();
                if(dayOfWeek.compareTo(logDate) == 0){
                       dailyAppLogList.add(appLogList.get(appLogList.size() + i));
                        Toast.makeText(getApplicationContext(), "" + logDate, Toast.LENGTH_LONG).show();
               }
               else{
                    dailyAppLogList.add(new AppLog());
               }
            }
        }
        */

        return dailyAppLogList;
    }
}
