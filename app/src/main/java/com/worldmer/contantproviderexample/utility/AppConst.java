package com.worldmer.contantproviderexample.utility;

import android.net.Uri;

/**
 * Created by Yagnik on 04-Jul-18.
 */

public class AppConst
{
    public static final String TABLE_CONTACTMASTER = "contactMaster";
    public static final String KEY_ID = "contact_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE = "phone";
    public static final String PROVIDER_NAME = "com.worldmer.contantproviderexample.ContactProvider";
    public static final String URL = "content://" + PROVIDER_NAME + "/"+ TABLE_CONTACTMASTER;
    public static final Uri CONTENT_URI = Uri.parse(URL);
}
