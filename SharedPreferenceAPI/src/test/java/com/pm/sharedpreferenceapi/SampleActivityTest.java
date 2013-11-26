package com.pm.sharedpreferenceapi;


import android.content.Intent;

import android.test.ActivityUnitTestCase;

import android.test.UiThreadTest;
import android.widget.TextView;

/**
 * Created by pmandrek on 26/11/13.
 */
public class SampleActivityTest extends ActivityUnitTestCase<SampleActivity> {

    private SampleActivity activity;

    public SampleActivityTest(){
        super(SampleActivity.class);
    }

    protected void setUp() throws Exception{
        super.setUp();

        Intent intent = new Intent(getInstrumentation().getTargetContext(),
                SampleActivity.class);
        startActivity(intent, null, null);
        activity = getActivity();
    }

    @UiThreadTest
    public void testStringSet() throws Exception {

        SampleActivity activity = getActivity();

        // search for the textView
        final TextView textView = (TextView) activity
                .findViewById(R.id.sample_string);

        assertEquals(SampleActivity.STRING_VALUE, textView.getText().toString());

    }

    @UiThreadTest
    public void testFloatSet() throws Exception {

        SampleActivity activity = getActivity();

        // search for the textView
        final TextView textView = (TextView) activity
                .findViewById(R.id.sample_float);

        assertEquals(SampleActivity.FLOAT_VALUE, Float.parseFloat(textView.getText().toString()));

    }

}
