package com.example.school;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.example.school.Helper.DbHelper;
import com.example.school.ModelClasses.ProfileModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class LobbyActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private static FragmentManager fragmentManager;
    String email = "";
    public static final String MY_PREFS_NAME = "MyPrefs";
    private DbHelper dbHelper;
    private ArrayList<ProfileModel> ProfileArrayList;
    MediaPlayer menuClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        menuClick = MediaPlayer.create(this, R.raw.jump);


        navigationView = (NavigationView) findViewById(R.id.navigation);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        Intent intent = getIntent();
        email = intent.getStringExtra("Email");
        dbHelper = new DbHelper(getApplicationContext());
        ProfileArrayList = new ArrayList<>();
        ProfileArrayList = dbHelper.getUsers(email);
        ProfileModel modal = ProfileArrayList.get(0);


        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                drawerLayout.closeDrawer(GravityCompat.START);

                switch (item.getItemId()) {
                    //navlobby will route to be timetable
                    case R.id.nav_lobby:
                        toolbar.setTitle("TimeTable");
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new TimetableFragment())
                                .commit();
                        menuClick.start();
                        break;
                    case R.id.nav_notes:
                        toolbar.setTitle("Notes");
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new TweetsFragment())
                                .commit();
                        menuClick.start();
                        break;
                   /* case R.id.nav_classroom:
                        toolbar.setTitle("Classroom");
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new ClassroomFragment())
                                .commit();
                        menuClick.start();
                        break;
                    case R.id.nav_followers:
                        toolbar.setTitle("Followers");
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new FollowersFragment())
                                .commit();
                        menuClick.start();
                        break;*/
                    case R.id.nav_quiz:
                        toolbar.setTitle("Quiz");
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new QuizFragment())
                                .commit();
                        menuClick.start();
                        break;
                    case R.id.nav_achievement:
                        toolbar.setTitle("Achievements");
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new AchievementFragment())
                                .commit();
                        menuClick.start();
                        break;
                    case R.id.nav_fees:
                        toolbar.setTitle("School Fees");
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new SchoolFeesFragment())
                                .commit();
                        menuClick.start();
                        break;
                    case R.id.nav_Profile:
                        toolbar.setTitle("Profile");
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new ProfileFragment())
                                .commit();
                        menuClick.start();
                        break;
                    case R.id.nav_Share:
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        String shareBody = "Your Body Here";
                        String shareSub = "Your Subject Here";
                        intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                        intent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                        startActivity(Intent.createChooser(intent, "ShareVia"));
                        break;
                    case R.id.nav_logout:
                        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.clear();
                        editor.apply();
                        Intent login = new Intent(LobbyActivity.this, LoginActivity.class);
                        startActivity(login);
                        finish();
                        break;
                }
                return true;
            }
        });

        View headerview = navigationView.getHeaderView(0);
        TextView tvemail = headerview.findViewById(R.id.header_email);
        TextView tvphone = headerview.findViewById(R.id.header_phone);
        TextView tvname = headerview.findViewById(R.id.header_name);


        tvemail.setText(modal.getEmail());
        tvname.setText(modal.getName());
        tvphone.setText(modal.getPhone());


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new TimetableFragment())
                .commit();
        navigationView.setCheckedItem(R.id.nav_lobby);
        toolbar.setTitle("TimeTable");
        Log.d("TAG", "onCreate: upon loading it is NULL");


    }


}