package com.example.vlad81.kozlovskifirstapplication;

import android.app.LauncherActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


import com.example.vlad81.kozlovskifirstapplication.launcher.LauncherAdapter;
import com.example.vlad81.kozlovskifirstapplication.launcher.OffsetItemDecoration;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class LauncherViewActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public LauncherViewActivity(){}

    private int isPlot;
    private List<Integer> mData;
    static final Random rand = new Random();
    private LauncherAdapter adapter;

    public static String TAG;

    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_view);
        isPlot = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("pref_densityType", "1"));

        TAG = getResources().getString(R.string.launcher_view_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.post(new Runnable() {
            @Override
            public void run() {
                navigationView.setCheckedItem(R.id.launcher_activity);
            }
        });

        final View navigationHeaderView = navigationView.getHeaderView(0);
        final View profileImage = navigationHeaderView.findViewById(R.id.imageView);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


        final FloatingActionButton addNewItemButton = (FloatingActionButton) findViewById(R.id.fab);
        addNewItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int color = Color.argb(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
                mData.add(0,color);
                adapter.notifyDataSetChanged();
                Log.i(TAG, getString(R.string.inserted_at_start));
            }
        });

        createGridLayout();
    }

    private void createGridLayout() {
        final RecyclerView recyclerView = findViewById(R.id.louncher_content);
        recyclerView.setHasFixedSize(true);
        final int offset = getResources().getDimensionPixelSize(R.dimen.item_offset);
        recyclerView.addItemDecoration(new OffsetItemDecoration(offset));

        final int spanCount;
        if (isPlot == 1) {
            spanCount = getResources().getInteger(R.integer.span_count);
        } else {
            spanCount = getResources().getInteger(R.integer.plot_span_count);
        }
        final GridLayoutManager layoutManager = new GridLayoutManager(this, spanCount);
        recyclerView.setLayoutManager(layoutManager);

        mData = generateData();
        adapter = new LauncherAdapter(mData, getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    @NonNull
    private List<Integer> generateData() {
        final List<Integer> colors = new LinkedList<>();
        for (int i = 0; i < 1000; i++) {
            int color = Color.argb(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            colors.add(color);
        }

        return colors;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.list_activity) {
            Intent toListActivity = new Intent(getApplicationContext(), LinearLayoutActivity.class);
            toListActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_NEW_TASK );
            startActivity(toListActivity);
        } else if (id == R.id.settings) {
            Intent toSettings = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(toSettings);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setTheme() {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        int thirdPageTheme = Integer.parseInt(sharedPreferences.getString("pref_Theme", "1"));

        if (thirdPageTheme == 2) {
            setTheme(R.style.DarkAppTheme_NoActionBar);
        } else {
            setTheme(R.style.AppTheme_NoActionBar);
        }
    }
}
