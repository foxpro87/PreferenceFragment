package com.example.mypreferencefragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // 設定のアクティビティに飛ばす
        Intent intent = new Intent(this, PrefActivity.class);
        this.startActivity(intent);

//        // Display the fragment as the main content.
//        FragmentManager mFragmentManager = getFragmentManager();
//        FragmentTransaction mFragmentTransaction = mFragmentManager
//                .beginTransaction();
//        PrefsFragment mPrefsFragment = new PrefsFragment();
//        mFragmentTransaction.replace(android.R.id.content, mPrefsFragment);
//        mFragmentTransaction.commit();
//
////        getFragmentManager().beginTransaction()
////                .replace(android.R.id.content, new PrefsFragment()).commit();
    }

//    public static class PrefsFragment extends PreferenceFragment {
//
//        @Override
//        public void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//
//            // Load the preferences from an XML resource
//            addPreferencesFromResource(R.xml.preference);
//        }
//    }
}
