package com.epam.pashkova.episodatelistener.episodate_entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "tvShow"
})
@Generated("jsonschema2pojo")
public class SearchResult {

    @JsonProperty("tvShow")
    private TvShow tvShow;

    @JsonProperty("tvShow")
    public TvShow getTvShow() {
        return tvShow;
    }

    @JsonProperty("tvShow")
    public void setTvShow(TvShow tvShow) {
        this.tvShow = tvShow;
    }

}
