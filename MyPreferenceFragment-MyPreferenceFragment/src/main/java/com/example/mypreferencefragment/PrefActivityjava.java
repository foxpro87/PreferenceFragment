package com.example.mypreferencefragment;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class PrefActivityjava extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.PrefActivity);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pref_activityjava, menu);
        return true;
    }
    
}
