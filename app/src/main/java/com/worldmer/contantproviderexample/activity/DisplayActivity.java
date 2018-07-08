package com.worldmer.contantproviderexample.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;
import com.worldmer.contantproviderexample.R;
import com.worldmer.contantproviderexample.helper.ContactAdapter;
import com.worldmer.contantproviderexample.helper.DatabaseHelper;
import com.worldmer.contantproviderexample.modal.Contacts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yagnik on 03-Jan-18.
 */

public class DisplayActivity extends AppCompatActivity {
    ListView lvContacts;
    List<Contacts> contactList;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        bindView();
        initialize();
    }

    private void bindView() {
        lvContacts = (ListView) findViewById(R.id.lvcontacts);
    }

    private void initialize() {
        dbHelper = new DatabaseHelper(DisplayActivity.this);
        contactList = new ArrayList<>();
        contactList = dbHelper.getAllContact();
        if (contactList.size() > 0) {
            ContactAdapter customAdapter =
                    new ContactAdapter(DisplayActivity.this, contactList);
            lvContacts.setAdapter(customAdapter);
        } else {
            Toast.makeText(DisplayActivity.this, "Contact Not Found.", Toast.LENGTH_SHORT).show();
        }
    }
}