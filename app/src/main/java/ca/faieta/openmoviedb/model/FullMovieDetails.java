package ca.faieta.openmoviedb.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by cfaieta on 01/04/15.
 */
public class FullMovieDetails implements Serializable {

    @SerializedName("Title")
    public String title;

    @SerializedName("Year")
    public String year;

    @SerializedName("Rated")
    public String rated;

    @SerializedName("Released")
    public String released;

    @SerializedName("Runtime")
    public String runtime;

    @SerializedName("Genre")
    public String genre;

    @SerializedName("Director")
    public String director;

    @SerializedName("Writer")
    public String writer;

    @SerializedName("Actors")
    public String actors;

    @SerializedName("Plot")
    public String plot;

    @SerializedName("Language")
    public String language;

    @SerializedName("Country")
    public String Country;

    @SerializedName("Awards")
    public String awards;

    @SerializedName("Poster")
    public String poster;

    @SerializedName("Metascore")
    public String metascore;

    @SerializedName("imdbRating")
    public String imdbRating;

    @SerializedName("imdbVotes")
    public String imdbVotes;

    @SerializedName("imdbID")
    public String imdbId;

    @SerializedName("Type")
    public String type;

    public boolean isValidPosterUri() {
        return !"N/A".equals(poster);
    }
}
