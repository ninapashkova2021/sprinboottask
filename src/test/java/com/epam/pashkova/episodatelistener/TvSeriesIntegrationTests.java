package com.epam.pashkova.episodatelistener;

import com.epam.pashkova.episodatelistener.db.SubscriberRepository;
import com.epam.pashkova.episodatelistener.db.TvSeriesRepository;
import com.epam.pashkova.episodatelistener.db.table.SubscriberUser;
import com.epam.pashkova.episodatelistener.db.table.TvSeries;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integration.properties")
public class TvSeriesIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TvSeriesRepository tvSeriesRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @AfterEach
    public void tearDown() {
        tvSeriesRepository.deleteAll();
        subscriberRepository.deleteAll();
    }

    @Test
    void verifyThatTvSeriesWasReturned() throws Exception {
        String title = "Stranger Things";
        TvSeries tvSeries = new TvSeries();
        tvSeries.setTitle(title);
        tvSeriesRepository.save(tvSeries);

        mockMvc.perform(get("/favourites")).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title", Matchers.is(title)));
    }

    @Test
    void verifyThatTvSeriesWasAdded() throws Exception {
        String login = "testLogin";
        String password = "testPassword";
        SubscriberUser subscriberUser = new SubscriberUser();
        subscriberUser.setLogin(login);
        subscriberUser.setPassword(password);
        subscriberRepository.save(subscriberUser);

        String title = "Stranger Things";

        mockMvc.perform(post("/addFavourite")
                        .header("tvSeriesTitle", title)
                        .header("login", login))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title", Matchers.is(title)));
    }

    @Test
    void verifyThatTvSeriesWasRemovedFromFavouriteList() throws Exception {
        String login = "testLogin";
        String password = "testPassword";
        SubscriberUser subscriberUser = new SubscriberUser();
        subscriberUser.setLogin(login);
        subscriberUser.setPassword(password);
        subscriberRepository.save(subscriberUser);

        String title = "Stranger Things";
        TvSeries tvSeries = new TvSeries();
        tvSeries.setTitle(title);
        tvSeries.setYear("2016");
        tvSeries.setSeasonsCount(4);
        tvSeries.setRating(9.3358);
        tvSeries.getSubscribersUserSet().add(subscriberUser);
        tvSeriesRepository.save(tvSeries);

        mockMvc.perform(delete("/removeFavourite")
                        .header("tvSeriesTitle", title)
                        .header("login", login))
                .andExpect(status().isOk());
    }
}
