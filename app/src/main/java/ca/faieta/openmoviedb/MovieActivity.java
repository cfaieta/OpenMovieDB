package ca.faieta.openmoviedb;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.FrameLayout;

import com.squareup.otto.Subscribe;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ca.faieta.openmoviedb.event.LoadMoviePosterFragmentEvent;
import ca.faieta.openmoviedb.model.FullMovieDetails;
import ca.faieta.openmoviedb.retrofit.OmdbClient;
import rx.android.app.AppObservable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by cfaieta on 01/04/15.
 */
public class MovieActivity extends ActionBarActivity {

    private static final String TAG = "MovieActivity";

    @InjectView(R.id.fragment_container)
    FrameLayout fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        ButterKnife.inject(this);

        Intent intent = getIntent();
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            String imdbId = intent.getStringExtra(SearchManager.EXTRA_DATA_KEY);
            getFullMovieDetails(imdbId);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        OpenMovieDBApplication.unregisterToEventBus(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        OpenMovieDBApplication.registerToEventBus(this);
    }

    private void getFullMovieDetails(final String imdbId) {
        AppObservable.bindActivity(this, OmdbClient.get().getFullMovieDetails(imdbId))
                .subscribeOn(Schedulers.io())
                .subscribe(
                        new Action1<FullMovieDetails>() {
                            @Override
                            public void call(FullMovieDetails fullMovieDetails) {
                                loadMovieDetails(fullMovieDetails);
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                Log.e(TAG, "Failed to get full movie details: " + imdbId, throwable);
                            }
                        }
                );
    }


    private void loadMovieDetails(FullMovieDetails fullMovieDetails) {
        if (fragmentContainer != null) {
            MovieDetailsFragment fragment = new MovieDetailsFragment();
            fragment.setArguments(MovieDetailsFragment.createBundleForFragment(fullMovieDetails));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack("details")
                    .commit();
        }
    }

    @Subscribe
    public void loadMoviePosterFragment(LoadMoviePosterFragmentEvent event) {
        if (fragmentContainer != null) {
            MoviePosterFragment fragment = new MoviePosterFragment();
            fragment.setArguments(MoviePosterFragment.createBundleForFragment(event.moviePosterUrl));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack("poster")
                    .commit();
        }
    }
}
