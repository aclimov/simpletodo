package com.codepath.simpletodo;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.Model;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    List<MyModel> items;
    ListView lvItems;
    CustomTodoAdapter adapter;
    int REQUEST_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems=(ListView) this.findViewById(R.id.lvitems);

        items=new Select().from(MyModel.class).queryList();

        ArrayList<MyModel> arrayOfitems = new ArrayList<MyModel>();
        arrayOfitems.addAll(items);

        adapter = new CustomTodoAdapter(this, arrayOfitems);
        lvItems.setAdapter(adapter);



        setupListViewListener();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenNewEditActivity();
            }
        });
    }

    private void setupListViewListener(){
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        MyModel model= items.get(position);
                        MyModel dbmodel= new Select().from(MyModel.class)
                                .where(MyModel_Table.id.eq(model.id))
                                .querySingle();
                        if(dbmodel!=null) {
                         dbmodel.delete();
                        }
                        adapter.remove(model);
                        items.remove(position);

                        adapter.notifyDataSetChanged();
                        return true;
                    }
                }
        );
        lvItems.setOnItemClickListener( new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                MyModel model= items.get(position);

                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                i.putExtra("id",model.id);
                i.putExtra("position",position);
                startActivityForResult(i,REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK ) {
            // Extract id from result extras
            int id = data.getExtras().getInt("id", 0);
            int position = data.getExtras().getInt("position", -1);
            if(id>0) {
                MyModel dbmodel= new Select().from(MyModel.class)
                        .where(MyModel_Table.id.eq(id)).querySingle();

                if(position==-1) {
                    items.add(dbmodel);
                    adapter.add(dbmodel);
                }else {
                items.set(position,dbmodel);
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void OpenNewEditActivity() {
        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
        startActivityForResult(i,REQUEST_CODE);
    }


}

