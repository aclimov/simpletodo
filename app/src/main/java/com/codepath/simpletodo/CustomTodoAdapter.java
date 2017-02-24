package com.codepath.simpletodo;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.ArrayList;

import static com.codepath.simpletodo.R.id.tvName;

/**
 * Created by alex_ on 2/22/2017.
 */

public class CustomTodoAdapter extends ArrayAdapter<MyModel> {
    public CustomTodoAdapter(Context context, ArrayList<MyModel> items) {
        super(context, 0, items);
        modelItems=items;
    }

    ArrayList<MyModel> modelItems;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this id
        MyModel mmodel=modelItems.get(position);
        MyModel model = new Select().from(MyModel.class)
                .where(MyModel_Table.id.eq(mmodel.id))
                .querySingle();

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_item, parent, false);
        }
        // Lookup view for data population

        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvPriority = (TextView) convertView.findViewById(R.id.tvPriority);
        TextView tvDueDate = (TextView) convertView.findViewById(R.id.tvDueDate);
        // Populate the data into the template view using the data object
        if(model!=null) {
            if(model.name!=null){
            tvName.setText(model.name);}
            if(model.priority!=null){

            tvPriority.setText(model.priority);
            switch(model.priority) {
                case "Low":tvPriority.setTextColor(Color.rgb(255,231,6));
                    break;
                case "Normal":tvPriority.setTextColor(Color.GREEN);
                    break;
                case "High":tvPriority.setTextColor(Color.RED);
                    break;
            }
            }
            if(model.duedate!=null){
                android.text.format.DateFormat df = new android.text.format.DateFormat();
                String dt= df.format("MMM-dd \n HH:mm", model.duedate).toString();
                tvDueDate.setText(dt);
            }
            else {
                tvDueDate.setText("");
            }
        }

        // Return the completed view to render on screen
        return convertView;
    }


}
