package com.epam.pashkova.episodatelistener.controller;

import com.epam.pashkova.episodatelistener.db.table.TvSeries;
import com.epam.pashkova.episodatelistener.service.TvSeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TvSeriesController {

    @Autowired
    private TvSeriesService tvSeriesService;

    @GetMapping("/favourites")
    public List<TvSeries> getFavouriteTvSeries() {
        return tvSeriesService.getFavouriteTvSeries();
    }

    @PostMapping("/addFavourite")
    public ResponseEntity<TvSeries> addFavouriteTvSeries(@RequestHeader("tvSeriesTitle") String tvSeriesTitle,
                                                        @RequestHeader("login") String login) {
        return new ResponseEntity<>(tvSeriesService.addFavouriteTvSeries(tvSeriesTitle, login), HttpStatus.CREATED);
    }

    @DeleteMapping("/removeFavourite")
    public void removeFavouriteTvSeries(@RequestHeader("tvSeriesTitle") String tvSeriesTitle,
                                        @RequestHeader("login") String login) {
        tvSeriesService.removeFavouriteTvSeries(tvSeriesTitle, login);
    }
}
