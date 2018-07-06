package com.google.developer.bugmaster;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.widget.Toast;

public class SettingsFragment extends PreferenceFragment {

    SwitchPreference quizReminder;
    ListPreference quizTimes;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        quizReminder= (SwitchPreference) findPreference(getString(R.string.pref_key_reminder));
        quizTimes= (ListPreference) findPreference(getString(R.string.pref_key_alarm));

        quizReminder.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                if(preference.isEnabled()){
                    Toast.makeText(preference.getContext(),"yeah",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(preference.getContext(),"fuck",Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }
}
