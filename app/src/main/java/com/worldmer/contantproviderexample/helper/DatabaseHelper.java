package com.worldmer.contantproviderexample.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.worldmer.contantproviderexample.modal.Contacts;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contactDB";
    private static final String TABLE_CONTACTMASTER = "contactMaster";
    private static final String KEY_ID = "contact_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE = "phone";
    private String TAG = "DatabaseHelper";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_CONTACTMASTER + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT NOT NULL,"
                + KEY_PHONE + " TEXT NOT NULL" + ")";
        Log.d(TAG, "QUERY : " + createTableQuery);
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String updateQuery = "DROP TABLE IF EXISTS " + TABLE_CONTACTMASTER;
        Log.d(TAG, "QUERY : " + updateQuery);
        db.execSQL(updateQuery);
        onCreate(db);
    }

    public void insertContact(Contacts contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        String insertQuery = "INSERT INTO " + TABLE_CONTACTMASTER
                + " (" + KEY_NAME + "," + KEY_PHONE + ") " + "VALUES ('"
                + contact.getName() + "','" + contact.getPhone() + "')";

        Log.d(TAG, "QUERY : " + insertQuery);
        db.execSQL(insertQuery);
        db.close();
    }

    public void updateContact(Contacts contact, int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String updateQuery = "UPDATE " + TABLE_CONTACTMASTER + " SET "
                + KEY_NAME + "='" + contact.getName() + "',"
                + KEY_PHONE + "='" + contact.getPhone()
                + "' WHERE " + KEY_ID + "=" + id;

        Log.d(TAG, "QUERY : " + updateQuery);
        db.execSQL(updateQuery);
        db.close();
    }

    public void deleteContact(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String deleteQuery = "DELETE FROM " + TABLE_CONTACTMASTER
                + " WHERE " + KEY_ID + "=" + id;

        Log.d(TAG, "QUERY : " + deleteQuery);
        db.execSQL(deleteQuery);
        db.close();
    }

    public List<Contacts> getAllContact() {
        List<Contacts> contactList = new ArrayList<Contacts>();
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTMASTER;
        Log.d(TAG, "QUERY : " + selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Contacts contact = new Contacts();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhone(cursor.getString(2));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<Contacts> getContact(String search) {
        List<Contacts> contactList = new ArrayList<Contacts>();

        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTMASTER
                + " WHERE " + KEY_NAME + " LIKE '" + search + "%'";

        Log.d(TAG, "QUERY : " + selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Contacts contact = new Contacts();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhone(cursor.getString(2));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public Contacts getContact(int index) {
        Contacts contact = new Contacts();
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTMASTER
                + " WHERE " + KEY_ID + "=" + index;
        Log.d(TAG, "QUERY : " + selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            contact.setId(Integer.parseInt(cursor.getString(0)));
            contact.setName(cursor.getString(1));
            contact.setPhone(cursor.getString(2));
        }
        return contact;
    }
}