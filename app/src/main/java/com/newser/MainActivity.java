package com.newser;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.newser.Data.Constants;
import com.newser.Fragments.NewsFragment;
import com.newser.Fragments.ViewPagerTab;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NewsAdapter.ItemClickCallback {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private FrameLayout fragmentHolder;
    private NewsFragment newsFragment;

    private Toolbar mainToolbar;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String current_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mainToolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),this);

        mViewPager = findViewById(R.id.container);
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        fragmentHolder = findViewById(R.id.fragment_holder);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        if(mUser!=null) {
            current_user_id = mUser.getUid();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mainToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(fragmentHolder.getVisibility()== View.VISIBLE){
            getSupportFragmentManager().beginTransaction().remove(newsFragment).commitAllowingStateLoss();
            newsFragment = null;
            fragmentHolder.setVisibility(View.GONE);
            mViewPager.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
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
     /*   if (id == R.id.action_settings) {
            return true;
        }
     */

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profile) {
            if(mUser==null)
                sendToLogin();
            else
                Toast.makeText(this,"Logged In as "+mUser.getEmail(),Toast.LENGTH_SHORT).show();
        } else if (id == R.id.about) {

        } else if (id == R.id.settings) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.faq) {

        }else if (id == R.id.logout) {
            logOut();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logOut() {
        if(mUser!=null)
            mAuth.signOut();
        sendToLogin();
    }

    private void sendToLogin() {

        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();

    }

    @Override
    public void openFragment(String newsId) {
        fragmentHolder.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.GONE);
        tabLayout.setVisibility(View.GONE);

        newsFragment = new NewsFragment(newsId);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_holder,newsFragment).commitAllowingStateLoss();
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private NewsAdapter.ItemClickCallback callback;
        public SectionsPagerAdapter(FragmentManager fm, NewsAdapter.ItemClickCallback callback) {
            super(fm);
            this.callback = callback;
        }

        @Override
        public Fragment getItem(int position) {
            ViewPagerTab viewPagerTab;
            switch (position) {
                case 0:
                    viewPagerTab = new ViewPagerTab(Constants.WORLD,callback);
                    break;
                case 1:
                    viewPagerTab = new ViewPagerTab(Constants.SPORTS,callback);
                    break;
                case 2:
                    viewPagerTab = new ViewPagerTab(Constants.TECH,callback);
                    break;
                case 3:
                    viewPagerTab = new ViewPagerTab(Constants.BOLLYWOOD,callback);
                    break;
                case 4:
                    viewPagerTab = new ViewPagerTab(Constants.MONEY,callback);
                    break;
                default:
                    viewPagerTab = null;
            }
            return viewPagerTab;
        }

        @Override
        public int getCount() {
            // Show 5 total pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {

                case 0:
                    return "World";
                case 1:
                    return "Sports";
                case 2:
                    return "Tech";
                case 3:
                    return "Bollywood";
                case 4:
                    return "Money";
            }
            return null;
        }
    }

}
