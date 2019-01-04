package qenawi.panda.movie_search_list.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import qenawi.panda.a_predator.network_Handeler.CService_DBase;


import java.util.ArrayList;


public class Movies_Response extends CService_DBase {
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("results")
    @Expose
    private ArrayList<Movies_data> results = null;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public ArrayList<Movies_data> getResults() {
        return results;
    }

    public void setResults(ArrayList<Movies_data> results)
    {
        this.results = results;
    }

    @Override
    public boolean Is_Data_Good() {
        return this.results!=null;
    }
}
