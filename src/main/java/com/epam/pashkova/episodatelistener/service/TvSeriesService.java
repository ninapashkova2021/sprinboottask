package com.epam.pashkova.episodatelistener.service;

import com.epam.pashkova.episodatelistener.db.SubscriberRepository;
import com.epam.pashkova.episodatelistener.db.TvSeriesRepository;
import com.epam.pashkova.episodatelistener.db.table.SubscriberUser;
import com.epam.pashkova.episodatelistener.db.table.TvSeries;
import com.epam.pashkova.episodatelistener.episodate_entity.BasicSearchResult;
import com.epam.pashkova.episodatelistener.episodate_entity.Countdown;
import com.epam.pashkova.episodatelistener.episodate_entity.Episode;
import com.epam.pashkova.episodatelistener.episodate_entity.SearchResult;
import com.epam.pashkova.episodatelistener.episodate_entity.TvShow;
import com.epam.pashkova.episodatelistener.exception.EpisodateRetrievingException;
import com.epam.pashkova.episodatelistener.exception.SubscriberNotFoundException;
import com.epam.pashkova.episodatelistener.exception.TvSeriesNotInFavouriteListException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@PropertySource("classpath:episodate.properties")
public class TvSeriesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TvSeriesService.class);

    @Autowired
    private TvSeriesRepository tvSeriesRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${episodate.api.search}")
    private String episodateSearchUrl;

    @Value("${episodate.api.showdetails}")
    private String episodateShowDetailsUrl;

    public List<TvSeries> getFavouriteTvSeries() {
        return tvSeriesRepository.findAll();
    }

    public TvSeries addFavouriteTvSeries(String tvSeriesTitle, String login) {
        SubscriberUser subscriberUser = subscriberRepository.findByLogin(login);
        if (subscriberUser == null) {
            throw new SubscriberNotFoundException(String.format("Subscriber %s wasn't found", login));
        }

        TvSeries tvSeries = tvSeriesRepository.findByTitle(tvSeriesTitle);
        if (tvSeries == null) {
            tvSeries = fillInfoAboutTvSeriesUsingEpisodate(tvSeriesTitle);
        }

        if (subscriberUser.getTvSeriesSet().stream().map(TvSeries::getTitle).collect(Collectors.toSet())
                .contains(tvSeriesTitle)) {
            LOGGER.info("User {} is already subscribed", login);
            return tvSeries;
        }
        Objects.requireNonNull(tvSeries).getSubscribersUserSet().add(subscriberUser);
        return tvSeriesRepository.save(tvSeries);
    }

    public void removeFavouriteTvSeries(String tvSeriesTitle, String login) {
        SubscriberUser subscriberUser = subscriberRepository.findByLogin(login);
        if (subscriberUser == null) {
            throw new SubscriberNotFoundException(String.format("Subscriber %s wasn't found", login));
        }

        if (!subscriberUser.getTvSeriesSet().stream().map(TvSeries::getTitle).collect(Collectors.toList())
                .contains(tvSeriesTitle)) {
            throw new TvSeriesNotInFavouriteListException(String.format("User %s doesn't have %s in favourite list",
                    login, tvSeriesTitle));
        }
        TvSeries tvSeries = tvSeriesRepository.findByTitle(tvSeriesTitle);
        tvSeries.getSubscribersUserSet().remove(subscriberUser);
        tvSeriesRepository.save(tvSeries);
    }

    public TvSeries fillInfoAboutTvSeriesUsingEpisodate(String tvSeriesTitle) {
        Integer targetTvSeriesId = getIdOfTvSeriesFromEpisodate(tvSeriesTitle);
        ResponseEntity<SearchResult> searchResultResponseEntity = restTemplate.getForEntity(episodateShowDetailsUrl,
                SearchResult.class, targetTvSeriesId.toString());
        if (!searchResultResponseEntity.getStatusCode().is2xxSuccessful()) {
            throw new EpisodateRetrievingException(String.format("Couldn't get details about %s from Episodate",
                    tvSeriesTitle));
        }
        TvShow targetTvShowWithDetails = Objects.requireNonNull(searchResultResponseEntity.getBody()).getTvShow();
        Countdown countdown = targetTvShowWithDetails.getCountdown();
        LocalDate nextEpisodeDate = null;
        if (countdown != null) {
            nextEpisodeDate = LocalDate.parse(countdown.getAirDate().replace(" ", "T"),
                    DateTimeFormatter.ISO_DATE_TIME);
        }
        LOGGER.info("Next episode will be released on {}", nextEpisodeDate != null ?
                nextEpisodeDate.format(DateTimeFormatter.ISO_DATE) : LocalDate.EPOCH);
        TvSeries tvSeries = new TvSeries();
        tvSeries.setTitle(tvSeriesTitle);
        tvSeries.setRating(Double.valueOf(targetTvShowWithDetails.getRating()));
        tvSeries.setSeasonsCount(targetTvShowWithDetails.getEpisodes().stream().map(Episode::getSeason)
                .max(Integer::compareTo).get());
        tvSeries.setYear(targetTvShowWithDetails.getStartDate().split("-")[0]);
        tvSeries.setNextEpisodeDate(nextEpisodeDate);
        return tvSeries;
    }

    public Integer getIdOfTvSeriesFromEpisodate(String tvSeriesTitle) {
        ResponseEntity<BasicSearchResult> basicSearchResultResponseEntity = restTemplate.getForEntity(episodateSearchUrl,
                BasicSearchResult.class, tvSeriesTitle);
        if (!basicSearchResultResponseEntity.getStatusCode().is2xxSuccessful()) {
            throw new EpisodateRetrievingException("Couldn't get basic info from Episodate");
        }
        List<TvShow> tvShowsList = Objects.requireNonNull(basicSearchResultResponseEntity.getBody()).getTvShows();
        TvShow targetTvSeries = tvShowsList.stream().filter(tvShow -> tvSeriesTitle.equals(tvShow.getName()))
                .findFirst().orElse(null);
        if (targetTvSeries == null) {
            throw new EpisodateRetrievingException(String.format("Could not find info about %s in Episodate' basic info",
                    tvSeriesTitle));
        }
        return targetTvSeries.getId();
    }
}
