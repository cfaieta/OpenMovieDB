package ca.faieta.openmoviedb.widget;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ca.faieta.openmoviedb.R;
import ca.faieta.openmoviedb.model.FullMovieDetails;

/**
 * Created by cfaieta on 03/04/15.
 */
public class FullMovieDetailsAdapter extends RecyclerView.Adapter<FullMovieDetailsAdapter.ViewHolder> {

    private List<FullMovieDetails> results;
    private int layoutId;

    public FullMovieDetailsAdapter(List<FullMovieDetails> results, int layoutId) {
        this.results = results;
        this.layoutId = layoutId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FullMovieDetails row = results.get(position);

        holder.title.setText(row.title);
        holder.year.setText(row.year);

        if (row.isValidPosterUri()) {
            ImageLoader.getInstance().displayImage(row.poster, holder.moviePoster);
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.title)
        TextView title;

        @InjectView(R.id.year)
        TextView year;

        @InjectView(R.id.movie_poster)
        ImageView moviePoster;

        public ViewHolder(View view) {
            super(view);

            ButterKnife.inject(this, view);
        }

    }

}
