package com.worldmer.contantproviderexample.helper;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.worldmer.contantproviderexample.utility.AppConst;

import java.util.HashMap;


/**
 * Created by Yagnik on 04-Jul-18.
 */

public class ContactProvider extends ContentProvider {

    private static HashMap<String, String> CONTACT_PROJECTION_MAP;
    private static final int CONTACTS = 1;
    private static final int CONTACT_ID = 2;
    private SQLiteDatabase db;
    private static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AppConst.PROVIDER_NAME,AppConst.TABLE_CONTACTMASTER, CONTACTS);
        uriMatcher.addURI(AppConst.PROVIDER_NAME,AppConst.TABLE_CONTACTMASTER+"/#", CONTACT_ID);
    }

    @Override
    public boolean onCreate() {
        DatabaseHelper databaseHelper= new DatabaseHelper(getContext());
        db = databaseHelper.getWritableDatabase();
        return (db == null)? false:true;
    }


    @Override
    public Cursor query(Uri uri, String[] projection,
                        String selection,String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(AppConst.TABLE_CONTACTMASTER);
        switch (uriMatcher.match(uri)) {
            case    CONTACTS:
                qb.setProjectionMap(CONTACT_PROJECTION_MAP);
                break;

            case CONTACT_ID:
                qb.appendWhere( AppConst.KEY_ID + "=" + uri.getPathSegments().get(1));
                break;
            default:
        }

        if (sortOrder == null || sortOrder == ""){
            sortOrder = AppConst.KEY_NAME;
        }
        Cursor cursur = qb.query(db,	projection,	selection,
                selectionArgs,null, null, sortOrder);
        cursur.setNotificationUri(getContext().getContentResolver(), uri);
        return cursur;
    }


    @Override
    public String getType(Uri uri) {
        return null;
    }


    public Uri insert(Uri uri, ContentValues values) {
        Uri content_uri=null;
        long rowID = db.insert(AppConst.TABLE_CONTACTMASTER, "", values);
        if (rowID > 0) {
         content_uri = ContentUris.withAppendedId(AppConst.CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(content_uri, null);
        }
        return content_uri;
    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)){
            case CONTACTS:
                count = db.delete(AppConst.TABLE_CONTACTMASTER, selection, selectionArgs);
                break;

            case CONTACT_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete( AppConst.TABLE_CONTACTMASTER, AppConst.KEY_ID  +  " = " + id +
                                (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""),
                        selectionArgs);
                break;
            default:
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
    @Override
    public int update(Uri uri, ContentValues values,
                      String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case CONTACTS:
                count = db.update(AppConst.TABLE_CONTACTMASTER, values, selection, selectionArgs);
                break;

            case CONTACT_ID:
                count = db.update(AppConst.TABLE_CONTACTMASTER, values,
                        AppConst.KEY_ID + " = " + uri.getPathSegments().get(1) +
                                (!TextUtils.isEmpty(selection) ? " AND (" +selection + ')' : ""),
                        selectionArgs);
                break;
            default:
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}