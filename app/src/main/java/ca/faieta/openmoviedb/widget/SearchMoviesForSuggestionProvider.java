package ca.faieta.openmoviedb.widget;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import ca.faieta.openmoviedb.model.SearchForResponse;
import ca.faieta.openmoviedb.retrofit.OmdbClient;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by cfaieta on 01/04/15.
 */
public class SearchMoviesForSuggestionProvider extends ContentProvider {

    private static final String TAG = "SearchMoviesForSuggestionProvider";

    public final static String AUTHORITY = "ca.faieta.openmoviedb.widget.SearchMoviesForSuggestionProvider";

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(final Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String query = uri.getLastPathSegment().toLowerCase();

        if (query == null || query.length() <= 1) {
            return null;
        }

        if (SearchManager.SUGGEST_URI_PATH_QUERY.equals(query)) {
            return null;
        }

        final MatrixCursor cursor = new MatrixCursor(new String[] {
                BaseColumns._ID,
                SearchManager.SUGGEST_COLUMN_TEXT_1,
                SearchManager.SUGGEST_COLUMN_TEXT_2,
                SearchManager.SUGGEST_COLUMN_INTENT_EXTRA_DATA //uniquely id this row

        });

        // let our cursor be notify aware as the data is returned async!
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        OmdbClient.get().searchMoviesFor(query)
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<SearchForResponse>() {
                    @Override
                    public void call(SearchForResponse searchForResponse) {

                        // possibility there are no results
                        if ("False".equals(searchForResponse.response)) {
                            Log.i(TAG, "Returned an error: " + searchForResponse.error);
                            return;
                        }

                        SearchForResponse.SearchInfo info;
                        for (int i = 0; i < searchForResponse.search.size(); i++) {

                            info = searchForResponse.search.get(i);
                            cursor.addRow(new Object[]{i, info.title, info.year, info.imdbId});
                        }

                        // let the cursor know there are results
                        getContext().getContentResolver().notifyChange(uri, null);
                    }
                });

        return cursor;
    }

    @Override
    public String getType(Uri uri) { return null; }

    @Override
    public Uri insert(Uri uri, ContentValues values) { return null; }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

}
