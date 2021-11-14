package com.epam.pashkova.episodatelistener.service;

import com.epam.pashkova.episodatelistener.db.SubscriberRepository;
import com.epam.pashkova.episodatelistener.db.TvSeriesRepository;
import com.epam.pashkova.episodatelistener.db.table.SubscriberUser;
import com.epam.pashkova.episodatelistener.db.table.TvSeries;
import com.epam.pashkova.episodatelistener.episodate_entity.BasicSearchResult;
import com.epam.pashkova.episodatelistener.episodate_entity.TvShow;
import com.epam.pashkova.episodatelistener.exception.EpisodateRetrievingException;
import com.epam.pashkova.episodatelistener.exception.SubscriberNotFoundException;
import com.epam.pashkova.episodatelistener.exception.TvSeriesNotInFavouriteListException;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class TvSeriesServiceTest {

    @Mock
    private TvSeriesRepository tvSeriesRepository;

    @Mock
    private SubscriberRepository subscriberRepository;

    @Mock
    private RestTemplate restTemplate;

    @Value("${episodate.api.search}")
    private String episodateSearchUrl;

    @Value("${episodate.api.showdetails}")
    private String episodateShowDetailsUrl;

    @InjectMocks
    private TvSeriesService tvSeriesService;

    @Test
    void verifyThatGetTvSeriesReturnsTvSeries() {
        // when
        tvSeriesService.getFavouriteTvSeries();

        // then
        Mockito.verify(tvSeriesRepository).findAll();
    }

    @Test
    void verifyThatTvSeriesWasAdded() {
        // given
        String title = "Stranger Things";
        TvSeries tvSeries = new TvSeries();
        tvSeries.setTitle(title);
        tvSeries.setYear("2016");
        tvSeries.setSeasonsCount(4);
        tvSeries.setRating(9.3358);

        String userLogin = "testUser";
        String userPassword = "testPassword";
        SubscriberUser subscriberUser = new SubscriberUser();
        subscriberUser.setLogin(userLogin);
        subscriberUser.setPassword(userPassword);
        // Subscriber doesn't have this TV series in favourite list
        subscriberUser.setTvSeriesSet(new HashSet<>());

        // when
        BDDMockito.given(subscriberRepository.findByLogin(userLogin)).willReturn(subscriberUser);
        BDDMockito.given(tvSeriesRepository.findByTitle(title)).willReturn(tvSeries);
        tvSeriesService.addFavouriteTvSeries(title, userLogin);

        // then
        Assertions.assertThat(tvSeries.getSubscribersUserSet()).as("Subscriber isn't in list")
                .contains(subscriberUser);
        Mockito.verify(tvSeriesRepository).save(tvSeries);
    }

    @Test
    void verifyEpisodateGetTvSeriesIdWasObtained() {
        // given
        String title = "testTitle";
        Integer expectedId = 5;
        BasicSearchResult basicSearchResult = new BasicSearchResult();
        TvShow tvShow = new TvShow();
        tvShow.setName(title);
        tvShow.setRating("10");
        tvShow.setId(expectedId);
        basicSearchResult.setTvShows(Lists.list(tvShow));

        // when
        BDDMockito.given(restTemplate.getForEntity(ArgumentMatchers.any(),
                        ArgumentMatchers.eq(BasicSearchResult.class), ArgumentMatchers.eq(title)))
                .willReturn(new ResponseEntity<>(basicSearchResult, HttpStatus.OK));
        Integer actualId = tvSeriesService.getIdOfTvSeriesFromEpisodate(title);

        // then
        Assertions.assertThat(actualId).as("Episodate TV series ID in not equal to target one")
                .isEqualTo(expectedId);
    }

    @Test
    void verifyEpisodateGetTvSeriesIdWasObtainedWithIncorrectTvShow() {
        // given
        String expectedTitle = "testTitle";
        String tvShowTitle = "tvShowTitle";
        BasicSearchResult basicSearchResult = new BasicSearchResult();
        TvShow tvShow = new TvShow();
        tvShow.setName(tvShowTitle);
        tvShow.setRating("10");
        basicSearchResult.setTvShows(Lists.list(tvShow));

        // when
        BDDMockito.given(restTemplate.getForEntity(ArgumentMatchers.any(),
                        ArgumentMatchers.eq(BasicSearchResult.class), ArgumentMatchers.eq(expectedTitle)))
                .willReturn(new ResponseEntity<>(basicSearchResult, HttpStatus.OK));
        ThrowableAssert.ThrowingCallable throwingCallable = () -> tvSeriesService.getIdOfTvSeriesFromEpisodate(expectedTitle);

        // then
        assertThatThrownBy(throwingCallable)
                .as("It should cause exception")
                .isInstanceOf(EpisodateRetrievingException.class);
    }

    @Test
    void verifyEpisodateGetTvSeriesIdWasNotObtained() {
        // given
        String title = "testTitle";
        BasicSearchResult basicSearchResult = new BasicSearchResult();
        TvShow tvShow = new TvShow();
        tvShow.setName(title);
        tvShow.setRating("10");
        basicSearchResult.setTvShows(Lists.list(tvShow));

        // when
        BDDMockito.given(restTemplate.getForEntity(ArgumentMatchers.any(),
                        ArgumentMatchers.eq(BasicSearchResult.class), ArgumentMatchers.eq(title)))
                .willReturn(new ResponseEntity<>(basicSearchResult, HttpStatus.SERVICE_UNAVAILABLE));
        ThrowableAssert.ThrowingCallable throwingCallable = () -> tvSeriesService.getIdOfTvSeriesFromEpisodate(title);;

        // then
        assertThatThrownBy(throwingCallable)
                .as("It should cause exception")
                .isInstanceOf(EpisodateRetrievingException.class);
    }

    @Test
    void verifyThatTvSeriesWasNotAddedWhenTvSeriesIsAlreadyInList() {
        // given
        String title = "Stranger Things";
        TvSeries tvSeries = new TvSeries();
        tvSeries.setTitle(title);
        tvSeries.setYear("2016");
        tvSeries.setSeasonsCount(4);
        tvSeries.setRating(9.3358);

        String userLogin = "testUser";
        String userPassword = "testPassword";
        SubscriberUser subscriberUser = new SubscriberUser();
        subscriberUser.setLogin(userLogin);
        subscriberUser.setPassword(userPassword);
        // Subscriber doesn't have this TV series in favourite list
        subscriberUser.getTvSeriesSet().add(tvSeries);

        // when
        BDDMockito.given(subscriberRepository.findByLogin(userLogin)).willReturn(subscriberUser);
        BDDMockito.given(tvSeriesRepository.findByTitle(title)).willReturn(tvSeries);
        tvSeriesService.addFavouriteTvSeries(title, userLogin);

        // then
        Mockito.verify(tvSeriesRepository, Mockito.never()).save(tvSeries);
    }

    @Test
    void verifyThatTvSeriesWasNotAddedDueToSubscriberNotFound() {
        // given
        String title = "Stranger Things";
        TvSeries tvSeries = new TvSeries();
        tvSeries.setTitle(title);
        tvSeries.setYear("2016");
        tvSeries.setSeasonsCount(4);
        tvSeries.setRating(9.3358);

        String userLogin = "testUser";

        // when
        BDDMockito.given(subscriberRepository.findByLogin(userLogin)).willReturn(null);
        ThrowableAssert.ThrowingCallable throwingCallable = () -> tvSeriesService.addFavouriteTvSeries(title, userLogin);

        // then
        assertThatThrownBy(throwingCallable)
                .as("It should cause exception")
                .isInstanceOf(SubscriberNotFoundException.class);
        Mockito.verify(tvSeriesRepository, Mockito.never()).save(tvSeries);
    }

    @Test
    void verifyThatTvSeriesWasRemoved() {
        // given
        String title = "Stranger Things";
        TvSeries tvSeries = new TvSeries();
        tvSeries.setTitle(title);
        tvSeries.setYear("2016");
        tvSeries.setSeasonsCount(4);
        tvSeries.setRating(9.3358);

        String userLogin = "testUser";
        String userPassword = "testPassword";
        SubscriberUser subscriberUser = new SubscriberUser();
        subscriberUser.setLogin(userLogin);
        subscriberUser.setPassword(userPassword);
        // Subscriber doesn't have this TV series in favourite list
        subscriberUser.getTvSeriesSet().add(tvSeries);

        // when
        BDDMockito.given(subscriberRepository.findByLogin(userLogin)).willReturn(subscriberUser);
        BDDMockito.given(tvSeriesRepository.findByTitle(title)).willReturn(tvSeries);
        tvSeriesService.removeFavouriteTvSeries(title, userLogin);

        // then
        Mockito.verify(tvSeriesRepository).save(tvSeries);
    }

    @Test
    void verifyThatTvSeriesWasNotRemovedDueToNotExistingUser() {
        // given
        String title = "Stranger Things";
        TvSeries tvSeries = new TvSeries();
        tvSeries.setTitle(title);
        tvSeries.setYear("2016");
        tvSeries.setSeasonsCount(4);
        tvSeries.setRating(9.3358);

        String userLogin = "testUser";
        String userPassword = "testPassword";
        SubscriberUser subscriberUser = new SubscriberUser();
        subscriberUser.setLogin(userLogin);
        subscriberUser.setPassword(userPassword);
        subscriberUser.getTvSeriesSet().add(tvSeries);

        // when
        BDDMockito.given(subscriberRepository.findByLogin(userLogin)).willReturn(null);
        ThrowableAssert.ThrowingCallable throwingCallable = () -> tvSeriesService.removeFavouriteTvSeries(title, userLogin);

        // then
        assertThatThrownBy(throwingCallable)
                .as("It should cause exception")
                .isInstanceOf(SubscriberNotFoundException.class);
        Mockito.verify(tvSeriesRepository, Mockito.never()).save(tvSeries);
    }

    @Test
    void verifyThatTvSeriesWasNotRemovedDueToAbsenceIsFavouritesList() {
        // given
        String title = "Stranger Things";
        TvSeries tvSeries = new TvSeries();
        tvSeries.setTitle(title);
        tvSeries.setYear("2016");
        tvSeries.setSeasonsCount(4);
        tvSeries.setRating(9.3358);

        String userLogin = "testUser";
        String userPassword = "testPassword";
        SubscriberUser subscriberUser = new SubscriberUser();
        subscriberUser.setLogin(userLogin);
        subscriberUser.setPassword(userPassword);

        // when
        BDDMockito.given(subscriberRepository.findByLogin(userLogin)).willReturn(subscriberUser);
        ThrowableAssert.ThrowingCallable throwingCallable = () -> tvSeriesService.removeFavouriteTvSeries(title, userLogin);

        // then
        assertThatThrownBy(throwingCallable)
                .as("It should cause exception")
                .isInstanceOf(TvSeriesNotInFavouriteListException.class);
        Mockito.verify(tvSeriesRepository, Mockito.never()).save(tvSeries);
    }
}