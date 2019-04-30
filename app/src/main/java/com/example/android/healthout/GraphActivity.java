package com.example.android.healthout;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.android.healthout.dataEntities.User;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class GraphActivity extends AppCompatActivity {
    DatabaseHelper db;

    User user;
    long app_id;
    long type_id;

    LineGraphSeries<DataPoint> series;

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

        double y, x;
        x = -5.0;

        GraphView graph = (GraphView) findViewById(R.id.graph);
        series = new LineGraphSeries<DataPoint>();
        for(int i = 0; i < 500; i++){
            x = x + 0.1;
            y = Math.sin(x);
            series.appendData(new DataPoint(x, y), true, 500);
        }
        graph.addSeries(series);

        Toast.makeText(getApplicationContext(), "" + app_id + "; " + type_id, Toast.LENGTH_LONG).show();
    }
}
