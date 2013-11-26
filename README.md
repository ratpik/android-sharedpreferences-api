android-sharedpreferences-api
=============================

API for sharing data stored in SharedPreferences between android apps/processes using a ContentProvider


Consider you have two apps and App X wants to share some data that it stores in its SharedPreferences with App Y

**App X will save Preferences the normal way.**


        //Confused? Check http://stackoverflow.com/questions/3783848/android-possible-to-have-multiple-distinct-shared-preferences-per-app
        
        SharedPreferences configurationPrefs = getApplicationContext().getSharedPreferences(SharedPreferenceAPI.CONFIGURATION_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor =  configurationPrefs.edit();

        editor.putInt(INT_KEY, INT_VALUE);
        editor.putFloat(FLOAT_KEY, FLOAT_VALUE);
        editor.putLong(LONG_KEY, LONG_VALUE);
        editor.putString(STRING_KEY, STRING_VALUE);
        editor.putBoolean(BOOLEAN_KEY, BOOL_VALUE);

        Log.d(TAG, "Wrote configuration prefs");

        editor.commit();
        
App X will include the API backend i.e `SharedPreferenceAPI.java` and add the below to its `AndroidManifest.xml`

          <provider
            android:name="com.pm.sharedpreferenceapi.SharedPreferenceAPI"
            android:authorities="@string/api_authority"
            android:exported="true"
            />
            
Note that if you know that all apps that might consume data from your App X will be created by you and you would like to keep things private, change `exported=false`.

In that case all your other apps would need to have the same `android:sharedUserId` as used by this app in its manifest. Also the other apps would need to be exported as APKs with the same cerificate as the App X.


 **Now App Y wants to access App X's data.**

Include `SharedPreferenceAPIClient.java` within App Y.

App Y will need to know the authority which can be considered as the root api url which App X will share with it.

Now wherever you need the data do,

    SharedPreferenceAPIClient apiClient = new SharedPreferenceAPIClient(getApplicationContext(), getApplicationContext().getString(R.string.api_authority));


And use the client to fetch the data you want

    apiClient.getString("one_key_that_rules_them_all_key_name", "a_default_if_not_found");
    apiClient.getFloat("float_key", 0.0f);
    
    
App Y can now access App X data. 

The default implementation is only for Read Only Access but this can easily be extended to Inserting, Modifying or Deleting values.    

That's it! 
