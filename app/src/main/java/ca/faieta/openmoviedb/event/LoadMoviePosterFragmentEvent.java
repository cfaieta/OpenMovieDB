package ca.faieta.openmoviedb.event;

/**
 * Created by cfaieta on 01/04/15.
 */
public class LoadMoviePosterFragmentEvent {
    public String moviePosterUrl;

    public LoadMoviePosterFragmentEvent(String moviePosterUrl) {
        this.moviePosterUrl = moviePosterUrl;
    }
}
