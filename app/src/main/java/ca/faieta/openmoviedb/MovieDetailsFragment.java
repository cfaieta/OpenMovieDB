package ca.faieta.openmoviedb;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import ca.faieta.openmoviedb.event.LoadMoviePosterFragmentEvent;
import ca.faieta.openmoviedb.model.FullMovieDetails;

/**
 * Created by cfaieta on 01/04/15.
 */
public class MovieDetailsFragment extends Fragment {

    private static final String ARGS_FULL_MOVIE_DETAILS = "fullMovieDetails";

    @InjectView(R.id.title)
    TextView title;

    @InjectView(R.id.released)
    TextView released;

    @InjectView(R.id.genre)
    TextView genre;

    @InjectView(R.id.awards)
    TextView awards;

    @InjectView(R.id.plot)
    TextView plot;

    @InjectView(R.id.movie_poster)
    ImageView moviePoster;

    private FullMovieDetails fullMovieDetails;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        ButterKnife.inject(this, view);

        Bundle args = getArguments();
        fullMovieDetails = (FullMovieDetails) args.getSerializable(ARGS_FULL_MOVIE_DETAILS);

        title.setText(fullMovieDetails.title);
        released.setText(fullMovieDetails.released);
        genre.setText(fullMovieDetails.genre);
        awards.setText(fullMovieDetails.awards);
        plot.setText(fullMovieDetails.plot);

        ImageLoader.getInstance().displayImage(fullMovieDetails.poster, moviePoster);

        return view;
    }

    @OnClick(R.id.movie_poster)
    public void loadMoviePoster() {
        OpenMovieDBApplication.publishToEventBus(new LoadMoviePosterFragmentEvent(fullMovieDetails.poster));
    }


    public static Bundle createBundleForFragment(FullMovieDetails fullMovieDetails) {
        Bundle args = new Bundle();
        args.putSerializable(ARGS_FULL_MOVIE_DETAILS, fullMovieDetails);
        return args;
    }
}
