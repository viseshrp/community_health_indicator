package com.ssdifall2016.communityhealthindicator.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ssdifall2016.communityhealthindicator.R;
import com.ssdifall2016.communityhealthindicator.ui.fragments.CountyFragment;
import com.ssdifall2016.communityhealthindicator.ui.fragments.DiseaseFragment;
import com.ssdifall2016.communityhealthindicator.utils.AppConstants;
import com.ssdifall2016.communityhealthindicator.utils.MsgUtils;
import com.ssdifall2016.communityhealthindicator.utils.PreferencesUtils;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            //// TODO: 11/21/16 check default values later. 
            if (PreferencesUtils.getBoolean(MainActivity.this, AppConstants.IS_LOGGED_IN, true))
                logout();
            else
                MsgUtils.displayToast(this, "Not logged in!");
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        PreferencesUtils.resetAll(MainActivity.this);
        startActivity(new Intent(MainActivity.this, SplashActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return CountyFragment.newInstance();
                case 1:
                    return DiseaseFragment.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "COUNTY";
                case 1:
                    return "DISEASE";

            }
            return null;
        }
    }

    public void showProgressDialog(String message) {
        String msg;
        if (message == null) {
            msg = getString(R.string.progress_dialog_loading_text);
        } else {
            msg = message;
        }

        progressDialog = ProgressDialog.show(this, null, msg, true);
    }

    public void showProgressDialog(String message, boolean dismissable) {
        String msg;
        if (message == null) {
            msg = getString(R.string.progress_dialog_loading_text);
        } else {
            msg = message;
        }

        progressDialog = ProgressDialog.show(this, null, msg, true, dismissable, null);
    }

    public void dismissProgressDialog() {
        if (this.isFinishing()) return;

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Snackbar.make((CoordinatorLayout) findViewById(R.id.main_content), "Signed in as " + PreferencesUtils.getString(this, AppConstants.EMAIL, ""),
                Snackbar.LENGTH_LONG).show();
    }
}
