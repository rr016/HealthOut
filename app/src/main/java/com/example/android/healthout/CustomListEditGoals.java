package com.example.android.healthout;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/*****************************************************************************************************************************/
/********************************** Custom Adapter to display goals on Edit Goals Activity ***********************************/
/*****************************************************************************************************************************/

public class CustomListEditGoals extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] web;
    private final String[] web2;
    private final String[] web3;
    private final Integer[] imageId;

    public CustomListEditGoals(Activity context, String[] web, String[] web2, String[] web3, Integer[] imageId) {
        super(context, R.layout.goal_item, web);
        this.context = context;
        this.web = web;
        this.web2 = web2;
        this.web3 = web3;
        this.imageId = imageId;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.goal_item, null, true);

        TextView textViewType = rowView.findViewById(R.id.textview_goaltype_goal);
        TextView textViewPeriod = rowView.findViewById(R.id.textview_period_goal);
        TextView textViewApp = rowView.findViewById(R.id.textview_appname_goal);
        ImageView imageView = rowView.findViewById(R.id.image_goal_icon);

        textViewType.setText(web[position]);
        textViewPeriod.setText(web2[position]);
        textViewApp.setText(web3[position]);
        imageView.setImageResource(imageId[position]);

        return rowView;
    }
}
