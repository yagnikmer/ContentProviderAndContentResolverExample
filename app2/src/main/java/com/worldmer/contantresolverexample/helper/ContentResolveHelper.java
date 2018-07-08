package com.worldmer.contantresolverexample.helper;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

import com.worldmer.contantresolverexample.utility.AppConst;
import com.worldmer.contantresolverexample.modal.Contacts;

import java.util.ArrayList;

/**
 * Created by Yagnik on 04-Jul-18.
 */

public class ContentResolveHelper
{
    Context mContext;
    ContentResolver contentResolver;
     public ContentResolveHelper(Context context)
    {
        mContext = context;
        contentResolver = mContext.getContentResolver();
    }
    public ArrayList<Contacts> getAllContacts()
    {
        ArrayList<Contacts> contactList = new ArrayList<>();
        String[]  projection = new String[]{AppConst.KEY_ID,AppConst.KEY_NAME,AppConst.KEY_PHONE};
        ContentResolver contentResolver = mContext.getContentResolver();
        Cursor cursor = contentResolver.query(AppConst.CONTENT_URI,projection,null,null,null);
        if (cursor!=null && cursor.getCount()>0)
        {
            while (cursor.moveToNext())
            {
                Contacts contacts = new Contacts();
                contacts.setId(Integer.parseInt(cursor.getString(0)));
                contacts.setName(cursor.getString(1));
                contacts.setPhone(cursor.getString(2));
                contactList.add(contacts);
            }
        }
        return contactList;
    }
    public Contacts getContact(int id)
    {
        Contacts contact = new Contacts();
        ContentResolver contentResolver = mContext.getContentResolver();

        Uri uri = AppConst.CONTENT_URI;
        String[]  projection = new String[]{AppConst.KEY_ID,AppConst.KEY_NAME,AppConst.KEY_PHONE};
        String selection=AppConst.KEY_ID+"=?";
        String[] selectionArgs = {String.valueOf(id)};
        String sortOrder=null;

        Cursor cursor = contentResolver.query(uri,projection,selection,selectionArgs,sortOrder);
        if (cursor!=null && cursor.getCount()>0)
        {
            while (cursor.moveToNext())
            {
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhone(cursor.getString(2));
            }
        }
        return contact;
    }
    public void insertContact(Contacts contact)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(AppConst.KEY_NAME,contact.getName());
        contentValues.put(AppConst.KEY_PHONE,contact.getPhone());
        Uri uri=contentResolver.insert(AppConst.CONTENT_URI,contentValues);
        if (uri !=null)
        {
            Toast.makeText(mContext,"Insert Record successfully",Toast.LENGTH_SHORT).show();
        }
    }
    public void deleteContact(int index)
    {
        String where=AppConst.KEY_ID+"=?";
        String[] selectionArgs={String.valueOf(index)};
        int noOfRec=contentResolver.delete(AppConst.CONTENT_URI,where,selectionArgs);
        Toast.makeText(mContext,noOfRec+" Record Delete successfully",Toast.LENGTH_SHORT).show();
    }
    public void updateContact(Contacts contact, int id)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(AppConst.KEY_NAME,contact.getName());
        contentValues.put(AppConst.KEY_PHONE,contact.getPhone());

        String where=AppConst.KEY_ID+"=?";
        String[] selectionArgs={String.valueOf(id)};
        int noOfRec=contentResolver.update(AppConst.CONTENT_URI,contentValues,where,selectionArgs);
        Toast.makeText(mContext,noOfRec+" Record Delete successfully",Toast.LENGTH_SHORT).show();
    }
}