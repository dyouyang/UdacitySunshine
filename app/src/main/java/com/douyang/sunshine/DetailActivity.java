package com.douyang.sunshine;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Looper;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);


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
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This fragment displays the weather detail of the list item that was clicked in the main activity.
     * It also provides a menu item for sharing that item through a ShareActionProvider.
     */
    public static class DetailFragment extends Fragment {

        private static final String TAG = DetailFragment.class.getSimpleName();
        private static final String FORECAST_SHARE_HASHTAG = "#SunShine";
        private String mForecastString;

        public DetailFragment() {
            setHasOptionsMenu(true);
        }

        @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.detailfragment, menu);

            MenuItem shareMenuItem = menu.findItem(R.id.action_share);
            ShareActionProvider mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareMenuItem);

            if (mShareActionProvider != null) {
                mShareActionProvider.setShareIntent(createShareForecastIntent());
            } else {
                Log.d(TAG, "Share action provider is null");
            }
        }

        private Intent createShareForecastIntent() {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, mForecastString + FORECAST_SHARE_HASHTAG);
            return shareIntent;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            Intent detailIntent = getActivity().getIntent();
            if (detailIntent != null && detailIntent.hasExtra("detail")) {
                mForecastString = detailIntent.getStringExtra("detail");
                TextView detailText = (TextView) rootView.findViewById(R.id.detail_text);
                detailText.setText(mForecastString);
            }
            return rootView;
        }
    }
}
