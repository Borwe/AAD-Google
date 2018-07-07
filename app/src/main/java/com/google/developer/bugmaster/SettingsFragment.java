package com.google.developer.bugmaster;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.widget.Toast;

import com.google.developer.bugmaster.reminders.AlarmReceiver;

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
                if(quizReminder.isChecked()){
                    //off
                    AlarmReceiver.scheduleAlarm(quizReminder.getContext());
                }else{
                    //on
                    AlarmReceiver.scheduleAlarm(quizReminder.getContext());
                }
                return true;
            }
        });

        quizTimes.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                AlarmReceiver.scheduleAlarm(quizReminder.getContext());
                return true;
            }
        });
    }
}
