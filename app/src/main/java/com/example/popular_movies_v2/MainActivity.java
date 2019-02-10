package com.example.popular_movies_v2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity
        extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {

    private ProgressBar progressBar;
    private TextView showUserMessageTV;
    private RecyclerView mRecyclerView;
    private MovieAdapterRView movieAdapterRView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.pgb_loading_spinner);
        showUserMessageTV = findViewById(R.id.txv_details_user_message);
        mRecyclerView = findViewById(R.id.rcv_posters_list);

        movieAdapterRView = new MovieAdapterRView(this, new ArrayList<Movie>());
        mRecyclerView.setAdapter(movieAdapterRView);

        checkDisplayOrientation();

        mRecyclerView.setHasFixedSize(true);

        if (checkConnectivity() != null && checkConnectivity().isConnected()) {
            android.support.v4.app.LoaderManager.getInstance(this)
                    .initLoader(0, null, this);
        } else
            showUserMessage(R.string.no_connection_message);

    }

    private void showUserMessage(int message) {
        showUserMessageTV.setText(message);
        progressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);

    }

    private void checkDisplayOrientation() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            mRecyclerView.setLayoutManager(
                    new GridLayoutManager(
                            this,
                            getResources().getInteger(R.integer.column_number_portrait)));
        } else {
            mRecyclerView.setLayoutManager(
                    new GridLayoutManager(
                            this,
                            getResources().getInteger(R.integer.column_number_landscape)));
        }
    }

    private NetworkInfo checkConnectivity() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        return connectivityManager.getActiveNetworkInfo();
    }

    @NonNull
    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int i, @Nullable Bundle bundle) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String sortOptionSelected = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );
        return new MovieLoader(this, QueryUtils.buildStringUrl(sortOptionSelected));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Movie>> loader, ArrayList<Movie> movies) {
        if (movies != null) {
            movieAdapterRView.setData(movies);
            progressBar.setVisibility(View.GONE);
        } else {
            showUserMessage(R.string.nothing_found_message);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Movie>> loader) {
        movieAdapterRView.setData(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
