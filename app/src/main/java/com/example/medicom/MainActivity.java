package com.example.medicom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.mainToolbar);
        bottomNavigationView = findViewById(R.id.bottomNavigation);

        setSupportActionBar(toolbar);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        loadScreen(new NewsFeedPage(bottomNavigationView));

    }

    void loadScreen(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.screenContainer, fragment);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.feedPage:
                loadScreen(new NewsFeedPage(bottomNavigationView));
                bottomNavigationView.getMenu().getItem(0).setChecked(true);
                break;


            case R.id.fakeNewsPage:
                loadScreen(new FakeNewsFragment());
                bottomNavigationView.getMenu().getItem(1).setChecked(true);
                //Toast.makeText(MainActivity.this,"Works",Toast.LENGTH_SHORT).show();
                break;

            case R.id.appointmentsPage:
                loadScreen(new AppointmentsFragment());
                bottomNavigationView.getMenu().getItem(2).setChecked(true);
                break;

            case R.id.messagesPage:
                loadScreen(new MessagesFragment());
                bottomNavigationView.getMenu().getItem(3).setChecked(true);
                break;
        }

        return false;
    }
}