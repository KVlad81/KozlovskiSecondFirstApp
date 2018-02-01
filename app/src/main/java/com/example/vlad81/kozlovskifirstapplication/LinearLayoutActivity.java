package com.example.vlad81.kozlovskifirstapplication;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.vlad81.kozlovskifirstapplication.linear_launcher.LauncherAdapter;
import com.example.vlad81.kozlovskifirstapplication.linear_launcher.OffsetItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class LinearLayoutActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "LinearLayoutActivity";
    private DrawerLayout mDrawerLayout;

    public LinearLayoutActivity() {}

    private List<Integer> mData;
    static final Random rand = new Random();
    private LauncherAdapter adapter;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_navigation_view);
        //isPlot = getIntent().getIntExtra("isPlot", 1);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.post(new Runnable() {
            @Override
            public void run() {
                navigationView.setCheckedItem(R.id.list_activity);
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


        final FloatingActionButton addNewItemButton = (FloatingActionButton) findViewById(R.id.linear_fab);
        addNewItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int color = Color.argb(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
                mData.add(0,color);
                adapter.notifyDataSetChanged();
                Log.i(TAG, "Inserted new element at start.");
            }
        });

        createGridLayout();
    }

    private void createGridLayout() {
        final RecyclerView recyclerView = findViewById(R.id.louncher_content);
        recyclerView.setHasFixedSize(true);
        final int offset = getResources().getDimensionPixelSize(R.dimen.item_offset);
        recyclerView.addItemDecoration(new OffsetItemDecoration(offset));

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mData = generateData();
        adapter = new LauncherAdapter(mData, getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    @NonNull
    private List<Integer> generateData() {
        final List<Integer> colors = new ArrayList<>();
        final Random rnd = new Random();
        for (int i = 0; i < 1000; i++) {
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            colors.add(color);
        }

        return colors;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.launcher_activity) {
            Intent toListActivity = new Intent(getApplicationContext(), LauncherViewActivity.class);
            navigationView.setCheckedItem(R.id.launcher_activity);
            startActivity(toListActivity);
        } else if (id == R.id.list_activity) {

        } else if (id == R.id.settings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
