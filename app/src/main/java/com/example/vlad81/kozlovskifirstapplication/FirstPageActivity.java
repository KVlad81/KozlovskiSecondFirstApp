package com.example.vlad81.kozlovskifirstapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ViewFlipper;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class FirstPageActivity extends AppCompatActivity {

    Button next;
    ViewFlipper welcomePages;
    static int firstPage = 0;
    static int thirdPageTheme;
    static int forthPageTheme;
    SharedPreferences.Editor editor;

    protected void onCreate(Bundle savedInstanceState) {
        setTheme();
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_first_page);
        
        welcomePages = (ViewFlipper) findViewById(R.id.viewflipper);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int[] layouts = {R.layout.first, R.layout.second, R.layout.third, R.layout.forth};

        for (int i = firstPage; i < layouts.length; ++i) {
            welcomePages.addView(inflater.inflate(layouts[i], null));
        }

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        editor = sharedPreferences.edit();

        forthPageTheme = Integer.parseInt(sharedPreferences.getString("pref_Theme", "1"));
        thirdPageTheme = Integer.parseInt(sharedPreferences.getString("pref_densityType", "1"));

        if (firstPage == 2) {
            addThirdPageRadioButtonsListeners();
            if (thirdPageTheme == 2) {
                ((RadioButton)findViewById(R.id.radioButton6)).setChecked(false);
                ((RadioButton)findViewById(R.id.radioButton7)).setChecked(true);
                ((LinearLayout)findViewById(R.id.darkthemelayout)).setBackground(getDrawable(R.color.gray));
            }
        }

        if (firstPage == 3) {
            addForthPageRadioButtonListeners();
            if (forthPageTheme == 2) {
                ((RadioButton)findViewById(R.id.radioButton)).setChecked(false);
                ((RadioButton)findViewById(R.id.radioButton1)).setChecked(true);
            }
        }

        next = (Button) findViewById(R.id.button);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (welcomePages.getChildCount() != 1){
                    ++FirstPageActivity.firstPage;
                    welcomePages.removeViewAt(0);
                    if (firstPage == 2) {
                        thirdPageTheme = 1;
                        addThirdPageRadioButtonsListeners();
                    }
                    if (firstPage == 3) {
                        forthPageTheme = 1;
                        addForthPageRadioButtonListeners();
                    }
                } else {
                    Intent nextScreen = new Intent(getApplicationContext(), LauncherViewActivity.class);
                    editor.putString("pref_Theme", String.valueOf(forthPageTheme));
                    editor.putString("pref_densityType", String.valueOf(thirdPageTheme));
                    editor.apply();
                    firstPage = 0;
                    nextScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                            | Intent.FLAG_ACTIVITY_NEW_TASK );
                    startActivity(nextScreen);
                }
            }
        });
    }

    private void setTheme() {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        forthPageTheme = Integer.parseInt(sharedPreferences.getString("pref_Theme", "1"));

        if (forthPageTheme == 2) {
            setTheme(R.style.DarkAppTheme_NoActionBar);
        } else {
            setTheme(R.style.AppTheme_NoActionBar);
        }
    }

    private void addForthPageRadioButtonListeners() {
        final RadioButton standardTheme = (RadioButton) findViewById(R.id.radioButton);
        final RadioButton plotTheme = (RadioButton) findViewById(R.id.radioButton1);
        standardTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plotTheme.setChecked(false);
                forthPageTheme = 1;
                editor.putString("pref_Theme", "1");
                editor.apply();
            }
        });
        plotTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forthPageTheme = 2;
                standardTheme.setChecked(false);
                editor.putString("pref_Theme", "2");
                editor.apply();
            }
        });
    }

    void addThirdPageRadioButtonsListeners() {
        final RadioButton lightTheme = (RadioButton) findViewById(R.id.radioButton6);
        final RadioButton darkTheme = (RadioButton) findViewById(R.id.radioButton7);
        lightTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                darkTheme.setChecked(false);
                thirdPageTheme = 1;
                ((LinearLayout)findViewById(R.id.darkthemelayout)).setBackground(getDrawable(R.drawable.customborder));
                editor.putString("pref_densityType", "1");
                editor.apply();
            }
        });
        darkTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thirdPageTheme = 2;
                lightTheme.setChecked(false);
                ((LinearLayout)findViewById(R.id.darkthemelayout)).setBackgroundColor(Color.rgb(0xC0, 0xC0, 0xC0));
                editor.putString("pref_densityType", "2");
                editor.apply();
            }
        });
    }

}
