package io.github.teamcharisma.farmsaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import java.util.Locale;
import java.util.PropertyPermission;

public class LanguageActivity extends AppCompatActivity {

    static String TAG = LanguageActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        String language = prefs.getString(getStr(R.string.language_key), "");
        if (!TextUtils.isEmpty(language)) {
            setLanguage(this, language);
            startActivity(new  Intent(this, LoginActivity.class));
        }

        setContentView(R.layout.activity_language);
    }

    public String getStr(int id) {
        return getResources().getString(id);
    }

    public void onLanguageSelect(View view) {
        //
        String text = ((TextView) view).getText().toString();
        String language = "en";
        if (text.equals(getStr(R.string.language_en))) {
            language = "en";
        } else if (text.equals(getStr(R.string.language_ta))) {
            language = "ta";
        } else if (text.equals(getStr(R.string.language_hi))) {
            language = "hi";
        }

        setLanguage(this, language);
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.language_key), language);
        editor.apply();
        startActivity(new Intent(this, LoginActivity.class));
    }

    public static void setLanguage(Context context, String languageToLoad) {
        Log.d(TAG, "setting language");
        Locale locale = new Locale(languageToLoad); //e.g "sv"
        Locale systemLocale = Locale.getDefault();
        if (systemLocale != null && systemLocale.equals(locale)) {
            Log.d(TAG, "Already correct language set");
            return;
        }
        SecurityManager securityManager = System.getSecurityManager();
        try {
            if (securityManager != null)
                securityManager.checkPermission(new PropertyPermission("user.language", "write"));
            Locale.setDefault(locale);
            android.content.res.Configuration config = new android.content.res.Configuration();
            config.locale = locale;
            context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
            Log.d(TAG, "Language set");

        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
}
