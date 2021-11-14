package com.epam.pashkova.episodatelistener.episodate_entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name",
        "permalink",
        "url",
        "description",
        "description_source",
        "start_date",
        "end_date",
        "country",
        "status",
        "runtime",
        "network",
        "youtube_link",
        "image_path",
        "image_thumbnail_path",
        "rating",
        "rating_count",
        "countdown",
        "genres",
        "pictures",
        "episodes"
})
@Generated("jsonschema2pojo")
public class TvShow {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("permalink")
    private String permalink;
    @JsonProperty("url")
    private String url;
    @JsonProperty("description")
    private String description;
    @JsonProperty("description_source")
    private Object descriptionSource;
    @JsonProperty("start_date")
    private String startDate;
    @JsonProperty("end_date")
    private Object endDate;
    @JsonProperty("country")
    private String country;
    @JsonProperty("status")
    private String status;
    @JsonProperty("runtime")
    private Integer runtime;
    @JsonProperty("network")
    private String network;
    @JsonProperty("youtube_link")
    private Object youtubeLink;
    @JsonProperty("image_path")
    private String imagePath;
    @JsonProperty("image_thumbnail_path")
    private String imageThumbnailPath;
    @JsonProperty("rating")
    private String rating;
    @JsonProperty("rating_count")
    private String ratingCount;
    @JsonProperty("countdown")
    private Countdown countdown;
    @JsonProperty("genres")
    private List<String> genres = null;
    @JsonProperty("pictures")
    private List<String> pictures = null;
    @JsonProperty("episodes")
    private List<Episode> episodes = null;

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("permalink")
    public String getPermalink() {
        return permalink;
    }

    @JsonProperty("permalink")
    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("description_source")
    public Object getDescriptionSource() {
        return descriptionSource;
    }

    @JsonProperty("description_source")
    public void setDescriptionSource(Object descriptionSource) {
        this.descriptionSource = descriptionSource;
    }

    @JsonProperty("start_date")
    public String getStartDate() {
        return startDate;
    }

    @JsonProperty("start_date")
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @JsonProperty("end_date")
    public Object getEndDate() {
        return endDate;
    }

    @JsonProperty("end_date")
    public void setEndDate(Object endDate) {
        this.endDate = endDate;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("runtime")
    public Integer getRuntime() {
        return runtime;
    }

    @JsonProperty("runtime")
    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    @JsonProperty("network")
    public String getNetwork() {
        return network;
    }

    @JsonProperty("network")
    public void setNetwork(String network) {
        this.network = network;
    }

    @JsonProperty("youtube_link")
    public Object getYoutubeLink() {
        return youtubeLink;
    }

    @JsonProperty("youtube_link")
    public void setYoutubeLink(Object youtubeLink) {
        this.youtubeLink = youtubeLink;
    }

    @JsonProperty("image_path")
    public String getImagePath() {
        return imagePath;
    }

    @JsonProperty("image_path")
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @JsonProperty("image_thumbnail_path")
    public String getImageThumbnailPath() {
        return imageThumbnailPath;
    }

    @JsonProperty("image_thumbnail_path")
    public void setImageThumbnailPath(String imageThumbnailPath) {
        this.imageThumbnailPath = imageThumbnailPath;
    }

    @JsonProperty("rating")
    public String getRating() {
        return rating;
    }

    @JsonProperty("rating")
    public void setRating(String rating) {
        this.rating = rating;
    }

    @JsonProperty("rating_count")
    public String getRatingCount() {
        return ratingCount;
    }

    @JsonProperty("rating_count")
    public void setRatingCount(String ratingCount) {
        this.ratingCount = ratingCount;
    }

    @JsonProperty("countdown")
    public Countdown getCountdown() {
        return countdown;
    }

    @JsonProperty("countdown")
    public void setCountdown(Countdown countdown) {
        this.countdown = countdown;
    }

    @JsonProperty("genres")
    public List<String> getGenres() {
        return genres;
    }

    @JsonProperty("genres")
    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    @JsonProperty("pictures")
    public List<String> getPictures() {
        return pictures;
    }

    @JsonProperty("pictures")
    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    @JsonProperty("episodes")
    public List<Episode> getEpisodes() {
        return episodes;
    }

    @JsonProperty("episodes")
    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }

}
