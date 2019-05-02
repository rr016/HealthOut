package com.example.android.healthout;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/*****************************************************************************************************************************/
/********************************** Custom Adapter to display goals on Main Menu Activity ************************************/
/*****************************************************************************************************************************/

public class CustomListMainMenu extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] web;
    private final String[] web2;
    private final String[] web3;
    private final String[] web4;
    private final String[] web5;
    private final Integer[] imageId;

    public CustomListMainMenu(Activity context, String[] web, String[] web2, String[] web3, String[] web4, String[] web5, Integer[] imageId) {
        super(context, R.layout.goal_item, web);
        this.context = context;
        this.web = web;
        this.web2 = web2;
        this.web3 = web3;
        this.web4 = web4;
        this.web5 = web5;
        this.imageId = imageId;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.main_menu_item, null, true);

        TextView textViewType = rowView.findViewById(R.id.textView_goaltype_main_menu);
        TextView textViewApp = rowView.findViewById(R.id.textView_appname_main_menu);
        TextView textViewPeriod = rowView.findViewById(R.id.textView_period_main_menu);
        TextView textViewTarget = rowView.findViewById(R.id.textView_target_main_menu);
        TextView textViewProgress = rowView.findViewById(R.id.textView_progress_main_menu);
        ImageView imageView = rowView.findViewById(R.id.image_main_menu_icon);

        textViewType.setText(web[position]);
        textViewApp.setText(web2[position]);
        textViewPeriod.setText(web3[position]);
        textViewTarget.setText(web4[position]);
        textViewProgress.setText(web5[position]);
        imageView.setImageResource(imageId[position]);

        return rowView;
    }
}
