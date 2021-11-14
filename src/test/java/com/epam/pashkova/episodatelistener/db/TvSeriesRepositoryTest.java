package com.epam.pashkova.episodatelistener.db;

import com.epam.pashkova.episodatelistener.db.table.TvSeries;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

@DataJpaTest
class TvSeriesRepositoryTest {

    @Autowired
    private TvSeriesRepository tvSeriesRepository;

    @AfterEach
    public void tearDown() {
        tvSeriesRepository.deleteAll();
    }

    @Test
    void verifyThatTvSeriesIsInDb() {
        // given
        String tvSeriesTitle = "TVSeries";
        TvSeries expectedTvSeries = new TvSeries();
        expectedTvSeries.setTitle(tvSeriesTitle);
        expectedTvSeries.setRating(9.8);
        expectedTvSeries.setNextEpisodeDate(LocalDate.now());
        expectedTvSeries.setYear("2021");
        expectedTvSeries.setSeasonsCount(1);
        tvSeriesRepository.save(expectedTvSeries);

        // when
        TvSeries actualTvSeries = tvSeriesRepository.findByTitle(tvSeriesTitle);

        // then
        Assertions.assertThat(actualTvSeries).as("TV series objects are different").isEqualTo(expectedTvSeries);
    }

    @Test
    void verifyThatTvSeriesIsNotInDb() {
        // given
        String tvSeriesTitle = "TVSeries";
        TvSeries expectedTvSeries = new TvSeries();
        expectedTvSeries.setTitle(tvSeriesTitle);
        expectedTvSeries.setRating(9.8);
        expectedTvSeries.setNextEpisodeDate(LocalDate.now());
        expectedTvSeries.setYear("2021");
        expectedTvSeries.setSeasonsCount(1);

        // when
        TvSeries actualTvSeries = tvSeriesRepository.findByTitle(tvSeriesTitle);

        // then
        Assertions.assertThat(actualTvSeries).as("TV series was found although it wasn't created")
                .isEqualTo(null);
    }
}