package com.worldmer.contantproviderexample.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.worldmer.contantproviderexample.R;

/**
 * Created by yagnik on 1/1/18.
 */
public class MainActivity extends AppCompatActivity {
    Button btnInsert, btnDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
        listeners();
    }
    private void bindView() {
        btnInsert = (Button) findViewById(R.id.btninsert);
        btnDisplay = (Button) findViewById(R.id.btndisplay);
    }
    private void listeners() {
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent insertIntent = new Intent(MainActivity.this, InsertActivity.class);
                startActivity(insertIntent);
            }
        });
        btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent displayIntent = new Intent(MainActivity.this, DisplayActivity.class);
                startActivity(displayIntent);
            }
        });
    }
}