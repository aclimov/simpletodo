package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.EditItemActivity;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static android.R.attr.data;
import static android.R.attr.name;
import static android.media.CamcorderProfile.get;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    EditText etNewItem;
    int REQUEST_CODE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems=(ListView) this.findViewById(R.id.lvitems);
        ReadItems();
        itemsAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        lvItems.setAdapter(itemsAdapter);
        etNewItem=(EditText)this.findViewById(R.id.etNewItem);
        setupListViewListener();
    }

    private void setupListViewListener(){
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        items.remove(position);
                        itemsAdapter.notifyDataSetChanged();
                        WriteItems();
                        return true;
                    }
                }
        );
        lvItems.setOnItemClickListener( new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String currentItemValue=items.get(position);
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                i.putExtra("value",currentItemValue);
                i.putExtra("position",position);
                startActivityForResult(i,REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK ) {
            // Extract position and value from result extras
            String editedValue = data.getExtras().getString("value");
            Integer position = data.getExtras().getInt("position");

            items.set(position,editedValue);
            itemsAdapter.notifyDataSetChanged();
            WriteItems();

        }
    }



    public void onAddItem(View view) {
        String etValue=etNewItem.getText().toString();
        if(!etValue.isEmpty()) {
            itemsAdapter.add(etValue);
            etNewItem.setText("");
            WriteItems();
        }else
        {
            Toast.makeText(this,"Can not add empty item",Toast.LENGTH_SHORT).show();
        }

    }

    private void ReadItems()
    {
        File filesDir = getFilesDir();
        File todoFile=new File(filesDir,"todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        }catch(IOException e) {
            items = new ArrayList<String>();
        }

    }

    private void  WriteItems()
    {
        File filesDir = getFilesDir();
        File todoFile=new File(filesDir,"todo.txt");
        try {
           FileUtils.writeLines(todoFile,items);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}

