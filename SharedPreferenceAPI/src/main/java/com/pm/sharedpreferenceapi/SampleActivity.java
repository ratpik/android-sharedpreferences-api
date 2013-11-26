package com.pm.sharedpreferenceapi;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by pmandrek on 26/11/13.
 */
public class SampleActivity extends Activity {

    private static final String TAG = SampleActivity.class.getSimpleName();

    private static String INT_KEY = "random_int_key";
    private static String FLOAT_KEY = "random_float_key";
    private static String LONG_KEY = "random_long_key";
    private static String STRING_KEY = "random_string_key";
    private static String BOOLEAN_KEY = "random_boolean_key";

    public static int INT_VALUE = -20;
    public static float FLOAT_VALUE = 322.27f;
    public static long LONG_VALUE = 83789373792373982L;
    public static String STRING_VALUE = "dog";
    public static boolean BOOL_VALUE = true;

    private static int DEFAULT_INT = 0;
    private static float DEFAULT_FLOAT = 0.0f;
    private static long DEFAULT_LONG = 0L;
    private static String DEFAULT_STRING= "cat";
    private static boolean DEFAULT_BOOL = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sample_layout);

        //To store in the app use the appropriate file and store just like normal preferences

        SharedPreferences configurationPrefs = getApplicationContext().getSharedPreferences(SharedPreferenceAPI.CONFIGURATION_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor =  configurationPrefs.edit();

        editor.putInt(INT_KEY, INT_VALUE);
        editor.putFloat(FLOAT_KEY, FLOAT_VALUE);
        editor.putLong(LONG_KEY, LONG_VALUE);
        editor.putString(STRING_KEY, STRING_VALUE);
        editor.putBoolean(BOOLEAN_KEY, BOOL_VALUE);

        Log.d(TAG, "Wrote configuration prefs");

        editor.commit();

        //In your other app where you want to access these values do this
        //Note that you would need to know which authority to access i.e the api root path
        SharedPreferenceAPIClient apiClient = new SharedPreferenceAPIClient(getApplicationContext(), getApplicationContext().getString(R.string.api_authority));

        //Now just get the values you are looking for, verify they are what was stored
        assert( apiClient.getInt(INT_KEY, DEFAULT_INT) == INT_VALUE);
        assert( apiClient.getFloat(FLOAT_KEY, DEFAULT_FLOAT) == FLOAT_VALUE);
        assert( apiClient.getLong(LONG_KEY, DEFAULT_LONG) == LONG_VALUE);
        assert( apiClient.getString(STRING_KEY, DEFAULT_STRING) == STRING_VALUE);
        assert( apiClient.getBoolean(BOOLEAN_KEY, DEFAULT_BOOL) == false);

        Log.d(TAG, "Int " + apiClient.getInt(INT_KEY, DEFAULT_INT));
        Log.d(TAG, "Float " + apiClient.getFloat(FLOAT_KEY, DEFAULT_FLOAT) );
        Log.d(TAG, "Long " + apiClient.getLong(LONG_KEY, DEFAULT_LONG));
        Log.d(TAG, "String " + apiClient.getString(STRING_KEY, DEFAULT_STRING) );
        Log.d(TAG, "Boolean " + apiClient.getBoolean(BOOLEAN_KEY, DEFAULT_BOOL));


        TextView tvString = (TextView) findViewById(R.id.sample_string);
        TextView tvFloat = (TextView) findViewById(R.id.sample_float);

        tvString.setText(apiClient.getString(STRING_KEY, DEFAULT_STRING));
        tvFloat.setText(String.valueOf(apiClient.getFloat(FLOAT_KEY, DEFAULT_FLOAT)));
    }
}
