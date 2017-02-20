package com.codepath;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.simpletodo.R;

import static com.codepath.simpletodo.R.id.etNewItem;

public class EditItemActivity extends AppCompatActivity {

    EditText etEditItem;
    int currentPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);


        String editValue = getIntent().getStringExtra("value");
        currentPosition=getIntent().getIntExtra("position",0);
        etEditItem= (EditText)this.findViewById(R.id.etEditItem);
        etEditItem.setText(editValue);
        etEditItem.setSelection(editValue.length());

    }

    public void onSaveItem(View view) {
        String etValue=etEditItem.getText().toString();
        Intent data = getIntent();
        data.putExtra("value",  etValue);
        data.putExtra("position",  currentPosition);

        setResult(RESULT_OK,data);
        finish();
    }
}
