package com.pm.sharedpreferenceapi;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.preference.PreferenceManager;

/**
 * Created by pmandrek on 26/11/13.
 */
public class SharedPreferenceAPI extends ContentProvider {


    public static final String CONFIGURATION_PREFERENCE_FILE_NAME = "Config";

    public static String PREFFERENCE_AUTHORITY;
    public static Uri BASE_URI;

    private static final String TYPE = "type";
    private static final String KEY = "key";

    private static final String INT_TYPE = "integer";
    private static final String LONG_TYPE = "long";
    private static final String FLOAT_TYPE = "float";
    private static final String BOOLEAN_TYPE = "boolean";
    private static final String STRING_TYPE = "string";

    private static final int MATCH_DATA = 0x010000;

    private static UriMatcher matcher;

    private static void init(Context context){

        PREFFERENCE_AUTHORITY = context.getString(R.string.api_authority);

        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(PREFFERENCE_AUTHORITY, "*/*", MATCH_DATA);

        BASE_URI = Uri.parse("content://" + PREFFERENCE_AUTHORITY);
    }

    @Override
    public boolean onCreate() {
        if(matcher == null){
            init(getContext());
        }
        return true;
    }

    @Override
    public String getType(Uri uri) {
        return ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + PREFFERENCE_AUTHORITY + ".item";
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException();

    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        throw new UnsupportedOperationException();

    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        //Provide a read only access to the content provider

        MatrixCursor cursor = null;
        switch (matcher.match(uri)) {
            case MATCH_DATA:
                final String key = uri.getPathSegments().get(0);
                final String type = uri.getPathSegments().get(1);
                cursor = new MatrixCursor(new String[] { key });
                SharedPreferences sharedPreferences = getContext().getSharedPreferences(CONFIGURATION_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
                if (!sharedPreferences.contains(key))
                    return cursor;
                MatrixCursor.RowBuilder rowBuilder = cursor.newRow();
                Object object = null;
                if (STRING_TYPE.equals(type)) {
                    object = sharedPreferences.getString(key, null);
                } else if (BOOLEAN_TYPE.equals(type)) {
                    object = sharedPreferences.getBoolean(key, false) ? 1 : 0;
                } else if (LONG_TYPE.equals(type)) {
                    object = sharedPreferences.getLong(key, 0l);
                } else if (INT_TYPE.equals(type)) {
                    object = sharedPreferences.getInt(key, 0);
                } else if (FLOAT_TYPE.equals(type)) {
                    object = sharedPreferences.getFloat(key, 0f);
                } else {
                    throw new IllegalArgumentException("Unsupported type " + uri);
                }
                rowBuilder.add(object);
                break;
            default:
                throw new IllegalArgumentException("Unsupported uri " + uri);
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }


}
