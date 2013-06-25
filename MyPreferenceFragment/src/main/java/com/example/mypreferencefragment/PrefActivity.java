package com.example.mypreferencefragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by sbwoo on 13. 6. 25.
 */
public class PrefActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

                // Display the fragment as the main content.
        FragmentManager mFragmentManager = getFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager
                .beginTransaction();
        PrefsFragment mPrefsFragment = new PrefsFragment();
        mFragmentTransaction.replace(android.R.id.content, mPrefsFragment);
        mFragmentTransaction.commit();

//        getFragmentManager().beginTransaction()
//                .replace(android.R.id.content, new PrefsFragment()).commit();
    }

    public static class PrefsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

        private static int PREFS = R.xml.preference;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.preference);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

            if (this.getString(R.string.pref_key_color).equals(key) == true) {


                String value = sharedPreferences.getString(key, "0");


                ColorSelector pref = (ColorSelector)this.findPreference(this.getString(R.string.pref_key_color));


                SpannableString summary = new SpannableString("Selection(#" + String.format("%x", Integer.parseInt(value)) + ")");
                pref.setSummary(summary);
                //pref.setI
            }
        }
    }


//______________________________________________________________________________

    class ColorSelector extends ListPreference {

        private static final String TAG = "ColorSelector#";


        public ColorSelector(Context context, AttributeSet attrs) {
            super(context, attrs);

        }


        @Override
        protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {


            String value = this.getSharedPreferences().getString(this.getKey(), "0x00ffffff");


            //int index = this.findIndexOfValue(value);

            CharSequence[] values =  this.getEntryValues();
            int selItem = -1;


            for (int i = 0; i < values.length; i++) {


                if (ColorSelectorAdapter.getColor((String)values[i]) == Integer.parseInt(value)) {


                    selItem = i;
                    break;
                }
            }

            Log.i(TAG + "onPrepareDialogBuilder", "index: " + selItem + " val: " + value);


            ListAdapter la = (ListAdapter)new ColorSelectorAdapter(this.getContext(),
                    R.layout.custom_row, this.getEntryValues(), selItem, this);


            builder.setAdapter(la, this);

            super.onPrepareDialogBuilder(builder);
        }


        public void setResult(int selectedItemValue) {


            if (this.callChangeListener("" + selectedItemValue)) {


                this.saveToPreference(selectedItemValue);

            }


            this.getDialog().dismiss();
        }


        private void saveToPreference(int color) {


            String prefName = this.getPreferenceManager().getSharedPreferencesName();


            SharedPreferences.Editor edit = this.getContext().getSharedPreferences(prefName, 0).edit();
            edit.putString(this.getKey(), "" + color);
            edit.commit();

            Log.i(TAG + "setResult", "color: " + color);
        }


        @Override
        protected void onBindView(View view) {
            super.onBindView(view);


            int color = Integer.parseInt(this.getSharedPreferences().getString(this.getKey(), "0x00ffffff"));


            View v = view.findViewById(R.id.color_sample);
            v.setBackgroundColor(color);

            Log.i(TAG + "onBindView", "color: " + color);
        }
    }



    static class ColorSelectorAdapter extends ArrayAdapter<CharSequence> implements View.OnClickListener {

        private static final String TAG = "ColorSelectorAdapter#";

        private int mSelectedItem;
        private ColorSelector mSelector;

        public ColorSelectorAdapter(Context context,
                                    int rowResourceId, CharSequence[] objects,
                                    int selectedItem, ColorSelector cs) {

            super(context, rowResourceId, objects);

            this.mSelectedItem = selectedItem;
            this.mSelector = cs;
        }

        public static int getColor(String col) {

            int res = 0;


            String tmp = col.toLowerCase();


            if (tmp.charAt(0) == '0' && tmp.charAt(1) == 'x') {
                tmp = tmp.substring(2);
            }


            if (tmp.length() == 8) {

                res = StrToHex(tmp.substring(0, 2)) << 24
                        | StrToHex(tmp.substring(2, 4)) << 16
                        | StrToHex(tmp.substring(4, 6)) << 8
                        | StrToHex(tmp.substring(6, 8));

                // rrggbb形式
            } else if (tmp.length() == 6) {
                res = 0xff000000
                        | StrToHex(tmp.substring(0, 2)) << 16
                        | StrToHex(tmp.substring(2, 4)) << 8
                        | StrToHex(tmp.substring(4, 6));
            }
            return res;
        }

        public static int StrToHex(String xx) {

            int res = 0;

            try {


                res = Integer.parseInt(xx, 16);

            } catch (Exception e) {
                e.printStackTrace();
                res = 0;
            }

            return res;
        }

        // ______________________________________________________________________________
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            CharSequence colorId = this.getItem(position);


            LayoutInflater inflater = ((Activity)this.getContext()).getLayoutInflater();
            View row = inflater.inflate(R.layout.custom_row, parent, false);


            int color = ColorSelectorAdapter.getColor(colorId.toString());
            row.setId(color);


            row.setOnClickListener(this);


            TextView tv = (TextView)row.findViewById(R.id.row_color_title);
            tv.setText(colorId);
            tv.setTextColor(color);


            RadioButton rb = (RadioButton)row.findViewById(R.id.row_check);


            if (position == this.mSelectedItem) {
                rb.setChecked(true);
            }


            rb.setClickable(false);


            View view = row.findViewById(R.id.row_color_sample);
            view.setBackgroundColor(color);

            return row;
        }


        @Override
        public void onClick(View view) {

            this.mSelector.setResult(view.getId());
        }
    }

}
