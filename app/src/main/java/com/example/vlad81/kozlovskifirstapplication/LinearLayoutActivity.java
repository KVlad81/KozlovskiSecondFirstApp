package com.example.vlad81.kozlovskifirstapplication;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    public LinearLayoutActivity() {}

    private List<Integer> mData;
    static final Random rand = new Random();
    private LauncherAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_navigation_view);
        //isPlot = getIntent().getIntExtra("isPlot", 1);

        ActionBar bar = getSupportActionBar();

        if (bar != null) {
            bar.hide();
        }

        final FloatingActionButton addNewItemButton = (FloatingActionButton) findViewById(R.id.fab);
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


    @Override
    public void onBackPressed() {
        Intent toMyPage = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(toMyPage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.launcher_activity) {
            Intent toListActivity = new Intent(getApplicationContext(), LauncherViewActivity.class);
            startActivity(toListActivity);
        } else if (id == R.id.list_activity) {

        } else if (id == R.id.settings) {

        } else if (id == R.id.imageView) {
            Intent toMyPage = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(toMyPage);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
