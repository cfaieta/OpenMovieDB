package ca.faieta.openmoviedb.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by cfaieta on 01/04/15.
 */
public class SearchForResponse {

    @SerializedName("Search")
    public List<SearchInfo> search;

    @SerializedName("Response")
    public String response;

    @SerializedName("Error")
    public String error;

    public class SearchInfo {

        @SerializedName("Title")
        public String title;

        @SerializedName("Year")
        public String year;

        @SerializedName("imdbID")
        public String imdbId;

        @SerializedName("Type")
        public String type;
    }
}
