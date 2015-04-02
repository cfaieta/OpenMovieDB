package ca.faieta.openmoviedb.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by cfaieta on 01/04/15.
 */
public class SearchForResponse {

    @SerializedName("Search")
    public List<SearchInfo> search;

    public class SearchInfo {

        @SerializedName("Title")
        public String title;

        @SerializedName("Year")
        public int year;

        @SerializedName("imdbID")
        public String imdbId;

        @SerializedName("Type")
        public String type;
    }
}
