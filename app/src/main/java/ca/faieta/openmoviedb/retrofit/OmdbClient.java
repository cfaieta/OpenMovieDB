package ca.faieta.openmoviedb.retrofit;

import retrofit.RestAdapter;

/**
 * Created by cfaieta on 01/04/15.
 */
public class OmdbClient {

    private static IOmdbApi apiClient;

    static {
        // initialization of the client
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setEndpoint("http://www.omdbapi.com")
                .setLogLevel(RestAdapter.LogLevel.BASIC);

        RestAdapter adapter = builder.build();
        apiClient = adapter.create(IOmdbApi.class);
    }

    public static IOmdbApi get() {
        return apiClient;
    }

}
