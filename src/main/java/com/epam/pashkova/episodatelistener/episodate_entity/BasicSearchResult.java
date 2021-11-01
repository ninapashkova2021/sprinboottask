package com.epam.pashkova.episodatelistener.episodate_entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "total",
        "page",
        "pages",
        "tv_shows"
})
@Generated("jsonschema2pojo")
public class BasicSearchResult {

    @JsonProperty("total")
    private String total;
    @JsonProperty("page")
    private Integer page;
    @JsonProperty("pages")
    private Integer pages;
    @JsonProperty("tv_shows")
    private List<TvShow> tvShows = null;

    @JsonProperty("total")
    public String getTotal() {
        return total;
    }

    @JsonProperty("total")
    public void setTotal(String total) {
        this.total = total;
    }

    @JsonProperty("page")
    public Integer getPage() {
        return page;
    }

    @JsonProperty("page")
    public void setPage(Integer page) {
        this.page = page;
    }

    @JsonProperty("pages")
    public Integer getPages() {
        return pages;
    }

    @JsonProperty("pages")
    public void setPages(Integer pages) {
        this.pages = pages;
    }

    @JsonProperty("tv_shows")
    public List<TvShow> getTvShows() {
        return tvShows;
    }

    @JsonProperty("tv_shows")
    public void setTvShows(List<TvShow> tvShows) {
        this.tvShows = tvShows;
    }

}
