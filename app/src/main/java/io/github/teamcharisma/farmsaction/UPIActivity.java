package io.github.teamcharisma.farmsaction;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import java.util.Arrays;

public class UPIActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upi);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String messagesString = preferences.getString("SMSAPP", "");
        if (messagesString!=null && !"".equals(messagesString)) {
            String messages[] = messagesString.split("\\|~~\\|");
            Log.v("TAG", Arrays.toString(messages));
        }
    }

}
