package com.example.android.healthout;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/*****************************************************************************************************************************/
/********************************** Custom Adapter to display goals on Main Menu Activity ************************************/
/*****************************************************************************************************************************/

public class CustomListMainMenu extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] web;
    private final String[] web2;
    private final Integer[] imageId;

    public CustomListMainMenu(Activity context, String[] web, String[] web2, Integer[] imageId) {
        super(context, R.layout.goal_item, web);
        this.context = context;
        this.web = web;
        this.web2 = web2;
        this.imageId = imageId;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.main_menu_item, null, true);

        TextView textViewProgress = rowView.findViewById(R.id.textview_progress_main_menu);
        TextView textViewType = rowView.findViewById(R.id.textview_goaltype_main_menu);
        ImageView imageView = rowView.findViewById(R.id.image_main_menu_icon);

        textViewProgress.setText(web[position]);
        textViewType.setText(web2[position]);
        imageView.setImageResource(imageId[position]);

        return rowView;
    }
}
