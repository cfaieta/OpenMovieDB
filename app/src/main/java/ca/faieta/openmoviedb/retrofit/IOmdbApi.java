package ca.faieta.openmoviedb.retrofit;

import ca.faieta.openmoviedb.model.FullMovieDetails;
import ca.faieta.openmoviedb.model.SearchForResponse;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by cfaieta on 01/04/15.
 */
public interface IOmdbApi {

    @GET("/?&type=movie&r=json")
    public SearchForResponse searchMoviesFor(@Query("s") String searchFor);


    @GET("/?y=&plot=full&r=json")
    public Observable<FullMovieDetails> getFullMovieDetails(@Query("i") String imdbId);
}
