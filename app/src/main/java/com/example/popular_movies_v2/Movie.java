package com.example.popular_movies_v2;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private final String mPosterPath;
    private final String mOriginalTitle;
    private final String mReleaseDate;
    private final String mPlotSynopsis;
    private final String mHighestRated;

    public Movie(String posterPath, String originalTitle, String releaseDate, String plotSynopsis,
                 String highestRated) {
        mPosterPath = posterPath;
        mOriginalTitle = originalTitle;
        mReleaseDate = releaseDate;
        mPlotSynopsis = plotSynopsis;
        mHighestRated = highestRated;
    }

    private Movie(Parcel in) {
        mPosterPath = in.readString();
        mOriginalTitle = in.readString();
        mReleaseDate = in.readString();
        mPlotSynopsis = in.readString();
        mHighestRated = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getPosterPath() {
        return Constants.POSTER_BASE_URL + mPosterPath;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String getPlotSynopsis() {
        return mPlotSynopsis;
    }

    public String getHighestRated() {
        return mHighestRated;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mPosterPath);
        dest.writeString(mOriginalTitle);
        dest.writeString(mReleaseDate);
        dest.writeString(mPlotSynopsis);
        dest.writeString(mHighestRated);
    }


}
