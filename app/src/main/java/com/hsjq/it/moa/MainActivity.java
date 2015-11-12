package com.hsjq.it.moa;

import android.app.Activity;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.ContentFrameLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hsjq.it.moa.until.NorMomalLoadPicture;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * 成员变量Fragment
     */
    Fragment mfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
               */
                mfragment = new MainFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, mfragment).commit();
            }
        });
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //设置当前登录人的信息
                TextView textViewUserName = (TextView) drawer.findViewById(R.id.textViewUserName);
                TextView textViewUserEmail = (TextView) drawer.findViewById(R.id.textViewUserEmail);
                textViewUserName.setText(getSharedPreferences("logininfo", Activity.MODE_PRIVATE).getString("userName", null));
                textViewUserEmail.setText(getSharedPreferences("logininfo", Activity.MODE_PRIVATE).getString("USER_EMAIL", "test@hsjq.com"));
                //设置当前登录人的头像
                ImageView imageViewHeader = (ImageView) drawer.findViewById(R.id.imageViewHeader);
                //imageViewHeader.setImageResource("http://192.168.90.43:8080/Login/log_header_beauty.png");
                new NorMomalLoadPicture().getPicture("http://test.hsjq.com:8080/Login/log_header_beauty.png", imageViewHeader);
                imageViewHeader.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                    }
                });
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setFragment();
    }

    /**
     * 设置Fragment的显示
     */
    private void setFragment() {
        mfragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, mfragment).commit();
    }

    private long exitTime = 0;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), R.string.tips_exist, Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            //设置菜单按钮
            return true;
        }
        if (id == R.id.action_settings_exits) {
            //安全退出功能
            SharedPreferences.Editor editor = getSharedPreferences("logininfo", Activity.MODE_PRIVATE).edit();
            editor.clear();
            editor.commit();
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        item.setChecked(false);
        if (id == R.id.nav_applied_process) {
            // Handle the camera action
            getSupportFragmentManager().beginTransaction().remove(mfragment);
            mfragment = new AppliedFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, mfragment).commit();
        } else if (id == R.id.nav_dealed_process) {
            getSupportFragmentManager().beginTransaction().remove(mfragment);
            mfragment = new DealedFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, mfragment).commit();
        } else if (id == R.id.nav_strart_process) {
            getSupportFragmentManager().beginTransaction().remove(mfragment);
            mfragment = new StartProcessFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, mfragment).commit();
        } else if (id == R.id.nav_todo_list) {
            getSupportFragmentManager().beginTransaction().remove(mfragment);
            mfragment = new TodoListFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, mfragment).commit();
        } else if (id == R.id.nav_address_book) {
            getSupportFragmentManager().beginTransaction().remove(mfragment);
            mfragment = new TodoListFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, mfragment).commit();
        } else if (id == R.id.nav_aboult) {

        } else if (id == R.id.nav_feedback) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}