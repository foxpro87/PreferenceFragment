package com.example.mypreferencefragment;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class PrefActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.PrefActivity.java);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pref, menu);
        return true;
    }
    
}
