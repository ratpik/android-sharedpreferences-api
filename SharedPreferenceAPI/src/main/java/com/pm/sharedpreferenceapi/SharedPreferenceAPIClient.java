package com.pm.sharedpreferenceapi;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by pmandrek on 26/11/13.
 */
public class SharedPreferenceAPIClient {

    private String authority;
    private Context context;
    private ContentResolver contentResolver;

    private static final String INT_TYPE = "integer";
    private static final String LONG_TYPE = "long";
    private static final String FLOAT_TYPE = "float";
    private static final String BOOLEAN_TYPE = "boolean";
    private static final String STRING_TYPE = "string";

    public SharedPreferenceAPIClient(Context context, String authority) {
        this.authority = authority;
        this.context = context;

        this.contentResolver = this.context.getContentResolver();
    }

    public Uri getContentUri(String key, String type) {

        String PREFERENCE_AUTHORITY = this.authority;
        Uri BASE_URI = Uri.parse("content://" + PREFERENCE_AUTHORITY);

        return BASE_URI.buildUpon().appendPath(key).appendPath(type).build();
    }

    private String getStringValue(Cursor cursor, String def) {
        if (cursor == null)
            return def;
        String value = def;
        if (cursor.moveToFirst()) {
            value = cursor.getString(0);
        }
        cursor.close();
        return value;
    }

    private boolean getBooleanValue(Cursor cursor, boolean def) {
        if (cursor == null)
            return def;
        boolean value = def;
        if (cursor.moveToFirst()) {
            value = cursor.getInt(0) > 0;
        }
        cursor.close();
        return value;
    }

    private int getIntValue(Cursor cursor, int def) {
        if (cursor == null)
            return def;
        int value = def;
        if (cursor.moveToFirst()) {
            value = cursor.getInt(0);
        }
        cursor.close();
        return value;
    }

    private long getLongValue(Cursor cursor, long def) {
        if (cursor == null)
            return def;
        long value = def;
        if (cursor.moveToFirst()) {
            value = cursor.getLong(0);
        }
        cursor.close();
        return value;
    }

    private float getFloatValue(Cursor cursor, float def) {
        if (cursor == null)
            return def;
        float value = def;
        if (cursor.moveToFirst()) {
            value = cursor.getFloat(0);
        }
        cursor.close();
        return value;
    }

    public String getString(String key, String def) {


        Uri uri = getContentUri(key, STRING_TYPE);
        Cursor cursor = this.contentResolver.query(uri, null, null, null, null);

        return getStringValue(cursor, def);
    }

    public boolean getBoolean(String key, boolean def) {


        Uri uri = getContentUri(key, BOOLEAN_TYPE);
        Cursor cursor = this.contentResolver.query(uri, null, null, null, null);

        return getBooleanValue(cursor, def);
    }

    public int getInt(String key, int def) {


        Uri uri = getContentUri(key, INT_TYPE);
        Cursor cursor = this.contentResolver.query(uri, null, null, null, null);

        return getIntValue(cursor, def);
    }

    public long getLong(String key, long def) {


        Uri uri = this.getContentUri(key, LONG_TYPE);
        Cursor cursor = contentResolver.query(uri, null, null, null, null);

        return getLongValue(cursor, def);
    }

    public float getFloat(String key, float def) {


        Uri uri = getContentUri(key, FLOAT_TYPE);
        Cursor cursor = this.contentResolver.query(uri, null, null, null, null);

        return getFloatValue(cursor, def);
    }

}
