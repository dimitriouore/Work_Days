package com.dimitriou.workdays;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_name);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MainFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.app_bar, menu);
        MenuItem back = menu.findItem(R.id.app_bar_back);
        back.setVisible(false);
        MenuItem calendar = menu.findItem(R.id.app_bar_calendar);
        calendar.setOnMenuItemClickListener(item -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DaysFragment()).addToBackStack(null).commit();
            return true;
        });
        MenuItem settings = menu.findItem(R.id.app_bar_settings);
        settings.setOnMenuItemClickListener(item -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).addToBackStack(null).commit();
            return true;
        });
        return true;
    }
}