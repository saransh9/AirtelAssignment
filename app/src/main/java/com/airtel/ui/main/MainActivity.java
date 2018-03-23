package com.airtel.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.airtel.R;
import com.airtel.data.pojo.ApiData;
import com.airtel.databinding.ActivityMainBinding;
import com.airtel.service.WallPaperChangeService;
import com.airtel.ui.base.BaseActivity;
import com.airtel.ui.fullscreenimage.FullScreenImageView;
import com.airtel.utils.ViewClickListener;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainActivityContract, ViewClickListener, AdapterView.OnItemSelectedListener {

    private static String TAG = MainActivity.class.getSimpleName();

    @Inject
    MainPresenter presenter;
    private static int PAGE_NUMBER = 1;
    ActivityMainBinding binding;
    MainAdapter adapter;
    String[] spinnerData = {"Newest", "Highest Rated"};
    ApiData apiData;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        getActivityComponent().inject(this);
        presenter.setView(this);

        ArrayAdapter arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerData);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        binding.spinner1.setAdapter(arrayAdapter);
        binding.spinner1.setOnItemSelectedListener(this);
        preferences = getApplicationContext().getSharedPreferences("Wallpapers", Context.MODE_PRIVATE);




    }

    @Override
    public void showLoader() {

        binding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoader() {
        binding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void dataFetched(Boolean isErrorFound, ApiData data) {
        if (!isErrorFound) {
            this.apiData = data;
            adapter = new MainAdapter(data, this, this);
            binding.recycler.setAdapter(adapter);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            binding.recycler.setLayoutManager(mLayoutManager);
            binding.recycler.setItemAnimator(new DefaultItemAnimator());
            adapter.notifyDataSetChanged();
            // Log.v("json", data.getWallpapers().toString());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < data.getWallpapers().length; i++) {
                sb.append(data.getWallpapers()[i].getUrl_thumb()).append(",");
            }
            preferences.edit().putString("api", sb.toString()).apply();
            preferences.edit().putString("service", "enable").apply();

            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayShowHomeEnabled(true);
            invalidateOptionsMenu();
            startService(new Intent(this, WallPaperChangeService.class));
        }
    }

    @Override
    public void onClick(View v) {
        int itemPosition = binding.recycler.getChildLayoutPosition(v);
        if (apiData != null) {
            Intent i = new Intent(MainActivity.this, FullScreenImageView.class);
            i.putExtra("image_url", apiData.getWallpapers()[itemPosition].getUrl_thumb());
            startActivity(i);
        }
    }

    @Override
    public void loadMoreData() {

        PAGE_NUMBER++;
        Log.v(TAG + "loadmore", String.valueOf(PAGE_NUMBER));
        presenter.fetchData(String.valueOf(PAGE_NUMBER));

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.v("position", String.valueOf(position));
        if (position == 0) {
            PAGE_NUMBER = 1;
            presenter.fetchData(String.valueOf(PAGE_NUMBER));
        } else if (position == 1) {
            PAGE_NUMBER = 1;
            presenter.fetchHighestRatedData(String.valueOf(PAGE_NUMBER));
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        if (preferences.getString("service", "").equalsIgnoreCase("enable")) {


            menu.findItem(R.id.settings).setTitle(R.string.disable_service);
            invalidateOptionsMenu();
        } else {
            menu.findItem(R.id.settings).setTitle(R.string.enable_service);
            invalidateOptionsMenu();
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case R.id.settings:
                if (preferences.getString("service", "").equalsIgnoreCase("enable")) {

                    Toast.makeText(MainActivity.this, "Service Diabled", Toast.LENGTH_SHORT).show();
                    preferences.edit().putString("service", "disable").apply();
                    Intent myService = new Intent(MainActivity.this, WallPaperChangeService.class);
                    stopService(myService);
                } else {
                    Toast.makeText(MainActivity.this, "Service Enabled", Toast.LENGTH_SHORT).show();
                    preferences.edit().putString("service", "enable").apply();
                    Intent myService = new Intent(MainActivity.this, WallPaperChangeService.class);
                    startService(myService);
                }
                return true;
        }
        return true;
    }
}
