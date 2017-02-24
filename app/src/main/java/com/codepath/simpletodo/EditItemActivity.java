package com.codepath.simpletodo;

import android.content.Intent;
import java.text.DateFormat;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.raizlabs.android.dbflow.sql.language.Select;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import layout.Dialog_SelectDateTime;
import layout.Dialog_SelectTime;

import static android.view.View.Y;
import static com.codepath.simpletodo.MyModel_Table.duedate;
import static com.codepath.simpletodo.R.id.btnSave;

public class EditItemActivity extends AppCompatActivity implements Dialog_SelectDateTime.SelectDateDialogListener, Dialog_SelectTime.SelectTimeDialogListener {

    EditText etItemName;
    EditText etItemDescription;
    TextView tvDueDate;

    TextView tvDueTime;
    Spinner spPriority;

    MyModel model ;

    int dueHours=0;
    int dueMinutes=0;
    Date dueDate=null;
    int position=-1;

    ArrayAdapter<CharSequence> priorityAdapter;
    int dbId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        dbId = getIntent().getIntExtra("id",-1);
        position = getIntent().getIntExtra("position",-1);

        etItemName= (EditText)this.findViewById(R.id.etItemName);
        etItemDescription= (EditText)this.findViewById(R.id.etItemDescription);
        tvDueDate= (TextView)this.findViewById(R.id.tvDueDate);
        tvDueTime= (TextView)this.findViewById(R.id.tvDueTime);
        spPriority=(Spinner)this.findViewById(R.id.spPriority);

        priorityAdapter=ArrayAdapter.createFromResource(this,R.array.priorities,android.R.layout.simple_spinner_item);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPriority.setAdapter(priorityAdapter);
        // Load data from DB if id=-1

        if(dbId>0) {
            MyModel dbmodel = new Select().from(MyModel.class)
                    .where(MyModel_Table.id.eq(dbId))
                    .querySingle();

            if(dbmodel!=null) {
                if(dbmodel.name!=null)
                {
                    etItemName.setText(dbmodel.name);
                }
                if(dbmodel.description!=null)
                {
                    etItemDescription.setText(dbmodel.description);
                }
                if(dbmodel.priority!=null)
                {
                    spPriority.setSelection(priorityAdapter.getPosition(dbmodel.priority));
                }
                if(dbmodel.duedate!=null)
                {
                    android.text.format.DateFormat df = new android.text.format.DateFormat();
                    String dt= df.format("yyyy-MM-dd", dbmodel.duedate).toString();
                    dueDate = dbmodel.duedate;
                    tvDueDate.setText(dt);

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(dueDate);

                    String dts= Integer.toString(cal.get(Calendar.HOUR_OF_DAY))+":"+Integer.toString(cal.get(Calendar.MINUTE));
                    dueHours=cal.get(Calendar.HOUR_OF_DAY);
                    dueMinutes=cal.get(Calendar.MINUTE);
                    tvDueTime.setText(dts);

                }
            }
        }



        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveItem();
            }
        });
        Button btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCancel();
            }
        });

        tvDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDueDateClicked();
            }
        });
        tvDueTime.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {                                              onDueTimeClicked();
                                          }
                                      });

    }

    @Override
    public void onFinishDateDialog(java.util.Date date)
    {
        android.text.format.DateFormat df = new android.text.format.DateFormat();
        String dt= df.format("yyyy-MM-dd", date).toString();
        dueDate = date;
        tvDueDate.setText(dt);
    }

    @Override
    public void onFinishTimeDialog(int hours, int minutes)
    {
        String dt= Integer.toString(hours)+":"+Integer.toString(minutes);
        dueHours=hours;
        dueMinutes=minutes;
        tvDueTime.setText(dt);
    }

    private void onDueDateClicked()
    {
        FragmentManager fm = getSupportFragmentManager();
        int year=0;
        int month=0;
        int day=0;
        if(dueDate!=null)
        {
            Calendar newDate = Calendar.getInstance();
            newDate.setTime(dueDate);
            year=newDate.get(Calendar.YEAR);
            month=newDate.get(Calendar.MONTH);
            day=newDate.get(Calendar.DAY_OF_MONTH);
        }
        Dialog_SelectDateTime editDialog = Dialog_SelectDateTime.newInstance(year,month,day);
        editDialog.show(fm, "fragment_dialog_datetime");
    }
    private void onDueTimeClicked()
    {
        FragmentManager fm = getSupportFragmentManager();
        int hours=dueHours;
        int minutes=dueMinutes;


        Dialog_SelectTime editDialog = Dialog_SelectTime.newInstance(hours,minutes);
        editDialog.show(fm, "fragment_dialog_time");
    }

    public void onSaveItem() {
      //write to DB
        MyModel model;
       if(dbId==-1)
       {
            model = new MyModel();

       }else
       {
            model = new Select().from(MyModel.class)
                   .where(MyModel_Table.id.eq(dbId))
                   .querySingle();

       }
        model.name=etItemName.getText().toString();
        model.createdate=new Date();
        model.description=etItemDescription.getText().toString();

        if(dueDate!=null)
        {
            Calendar cal = Calendar.getInstance();
            cal.setTime(dueDate);

            if(dueHours!=0)
            {
                cal.set(Calendar.HOUR_OF_DAY, dueHours);
            }
            if(dueMinutes!=0)
            {
                cal.set(Calendar.MINUTE, dueMinutes);
            }
            model.duedate=cal.getTime();
        }else
        {
            model.duedate=null;
        }

        if(spPriority.getSelectedItem()!=null)
        {
            model.priority=spPriority.getSelectedItem().toString();
        }

        model.save();

        //return to main screen
        Intent data = getIntent();
        data.putExtra("id",model.id);
        data.putExtra("position",position);
        setResult(RESULT_OK,data);
        finish();
    }

    public void onCancel()
    {
        finish();
    }



}
