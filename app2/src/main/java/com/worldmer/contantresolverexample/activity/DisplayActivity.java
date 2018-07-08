package com.worldmer.contantresolverexample.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.worldmer.contantresolverexample.R;
import com.worldmer.contantresolverexample.helper.ContactAdapter;
import com.worldmer.contantresolverexample.helper.ContentResolveHelper;
import com.worldmer.contantresolverexample.modal.Contacts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yagnik on 03-Jan-18.
 */

public class DisplayActivity extends AppCompatActivity {
    ContentResolveHelper contentResolveHelper;
    List<Contacts> contactList;
    ListView lvContacts;

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
        contentResolveHelper = new ContentResolveHelper(this);
        contactList = new ArrayList<>();
        contactList = contentResolveHelper.getAllContacts();
        if (contactList.size() > 0) {
            ContactAdapter customAdapter =
                    new ContactAdapter(DisplayActivity.this, contactList);
            lvContacts.setAdapter(customAdapter);
        } else {
            Toast.makeText(DisplayActivity.this, "Content Not Found.", Toast.LENGTH_SHORT).show();
        }
    }
}