package ca.faieta.openmoviedb;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ca.faieta.openmoviedb.model.FullMovieDetails;
import ca.faieta.openmoviedb.model.SearchForResponse;
import ca.faieta.openmoviedb.retrofit.OmdbClient;
import ca.faieta.openmoviedb.widget.FullMovieDetailsAdapter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by cfaieta on 03/04/15.
 */
public class MovieSearchResultsFragment extends Fragment {

    @InjectView(R.id.results_recycler_view)
    RecyclerView resultsRecyclerView;

    private List<FullMovieDetails> results = new ArrayList<>();
    private FullMovieDetailsAdapter adapter;

    private static final String ARGS_QUERY = "query";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_search_results, container, false);

        ButterKnife.inject(this, view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        resultsRecyclerView.setLayoutManager(layoutManager);
        resultsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        resultsRecyclerView.setHasFixedSize(true);

        doMovieSearch();

        return view;
    }

    private void doMovieSearch() {
        Bundle args = getArguments();
        String query = args.getString(ARGS_QUERY);

        adapter = new FullMovieDetailsAdapter(results, R.layout.card_movie_results_row);
        resultsRecyclerView.setAdapter(adapter);

        //
        // the OMDbAPI search results yeilds very limited amount of data, therefore we:
        // 1. for each result
        //   2. make an async call to get all details about that movie (needed for movie poster url)
        //
        OmdbClient.get().searchMoviesFor(query)
                .flatMap(new Func1<SearchForResponse, Observable<SearchForResponse.SearchInfo>>() {
                    @Override
                    public Observable<SearchForResponse.SearchInfo> call(SearchForResponse searchForResponse) {
                        // transform each SearchInfo into an Observable object
                        return Observable.from(searchForResponse.search);
                    }
                })
                .flatMap(new Func1<SearchForResponse.SearchInfo, Observable<FullMovieDetails>>() {
                    @Override
                    public Observable<FullMovieDetails> call(SearchForResponse.SearchInfo searchInfo) {
                        // this is the async call to get additional movie details
                        return OmdbClient.get().getFullMovieDetails(searchInfo.imdbId)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<FullMovieDetails>() {
                    @Override
                    public void call(FullMovieDetails fullMovieDetails) {
                        // each time we get async details about a movie:
                        // 1. add it to the master list that the adapter knows about
                        // 2. ensure we notify the adapter that it has changed to update the UI.
                        results.add(fullMovieDetails);
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    public static Bundle createBundleForFragment(String query) {
        Bundle args = new Bundle();
        args.putString(ARGS_QUERY, query);
        return args;
    }
}
