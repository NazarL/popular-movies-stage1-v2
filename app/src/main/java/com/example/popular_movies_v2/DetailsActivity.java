package com.example.popular_movies_v2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.txv_movie_title)
    TextView originalTitleTV;
    @BindView(R.id.imv_poster_thumbnail)
    ImageView posterThumbnailIMV;
    @BindView(R.id.txv_user_rating)
    TextView userRatingTV;
    @BindView(R.id.txv_release_date)
    TextView releaseDateTV;
    @BindView(R.id.txv_overview)
    TextView movieOverviewTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        Movie movie = getIntent().getParcelableExtra(Constants.SELECTED_MOVIE);

        if (movie != null) {
            displayUI(movie);
        }
    }

    private void displayUI(Movie movie) {
        originalTitleTV.setText(movie.getOriginalTitle());

        Picasso
                .get()
                .load(movie.getPosterPath())
                .error(R.drawable.ic_android_black_24dp)
                .into(posterThumbnailIMV);

        userRatingTV.setText(movie.getHighestRated());
        releaseDateTV.setText(movie.getReleaseDate());
        movieOverviewTV.setText(movie.getPlotSynopsis());
    }
}
