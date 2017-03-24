package com.dushyantss.omdbapp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dushyant Singh Shekhawat
 * on 24-03-2017.
 */

public class Model {

    @SerializedName("Title")
    private String mTitle;
    @SerializedName("Genre")
    private String mGenre;
    @SerializedName("Released")
    private String mReleaseDate;
    @SerializedName("Plot")
    private String mPlotShort;
    @SerializedName("imdbRating")
    private String mRating;
    @SerializedName("Poster")
    private String mPoster;

    public Model(String title, String genre, String releaseDate, String plotShort, String rating, String poster) {
        mTitle = title;
        mGenre = genre;
        mReleaseDate = releaseDate;
        mPlotShort = plotShort;
        mRating = rating;
        mPoster = poster;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getGenre() {
        return mGenre;
    }

    public void setGenre(String genre) {
        mGenre = genre;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public String getPlotShort() {
        return mPlotShort;
    }

    public void setPlotShort(String plotShort) {
        mPlotShort = plotShort;
    }

    public String getRating() {
        return mRating;
    }

    public void setRating(String rating) {
        mRating = rating;
    }

    public String getPoster() {
        return mPoster;
    }

    public void setPoster(String poster) {
        mPoster = poster;
    }
}
