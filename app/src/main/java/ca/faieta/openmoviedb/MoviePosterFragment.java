package ca.faieta.openmoviedb;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by cfaieta on 01/04/15.
 */
public class MoviePosterFragment extends Fragment {

    private static final String ARGS_MOVIE_POSTER_URL = "moviePosterUrl";

    @InjectView(R.id.movie_poster)
    ImageView moviePoster;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_poster, container, false);
        ButterKnife.inject(this, view);

        Bundle args = getArguments();
        String posterUrl = args.getString(ARGS_MOVIE_POSTER_URL);

        ImageLoader.getInstance().displayImage(posterUrl, moviePoster);

        return view;
    }

    public static Bundle createBundleForFragment(String moviePosterUrl) {
        Bundle args = new Bundle();
        args.putString(ARGS_MOVIE_POSTER_URL, moviePosterUrl);
        return args;
    }
}
