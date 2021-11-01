package com.epam.pashkova.episodatelistener.episodate_entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "season",
        "episode",
        "name",
        "air_date"
})
@Generated("jsonschema2pojo")
public class Countdown {

    @JsonProperty("season")
    private Integer season;
    @JsonProperty("episode")
    private Integer episode;
    @JsonProperty("name")
    private String name;
    @JsonProperty("air_date")
    private String airDate;

    @JsonProperty("season")
    public Integer getSeason() {
        return season;
    }

    @JsonProperty("season")
    public void setSeason(Integer season) {
        this.season = season;
    }

    @JsonProperty("episode")
    public Integer getEpisode() {
        return episode;
    }

    @JsonProperty("episode")
    public void setEpisode(Integer episode) {
        this.episode = episode;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("air_date")
    public String getAirDate() {
        return airDate;
    }

    @JsonProperty("air_date")
    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

}
