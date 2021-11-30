package com.epam.pashkova.episodatelistener.controller;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.epam.pashkova.episodatelistener.db.table.TvSeries;
import com.epam.pashkova.episodatelistener.service.TvSeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.List;

@RestController
public class TvSeriesController {

    @Autowired
    private TvSeriesService tvSeriesService;

    @Value("${s3.object.name}")
    private String fileName;

    @GetMapping("/favourites")
    public List<TvSeries> getFavouriteTvSeries() {
        return tvSeriesService.getFavouriteTvSeries();
    }

    @GetMapping(value = "/favouritesReport", produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<StreamingResponseBody> getReportContent() {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", fileName))
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(tvSeriesService.getReportContent());
    }

    @GetMapping(value = "/generateReportOnS3")
    public ResponseEntity<PutObjectResult> generateReportOnS3() {
        return new ResponseEntity<>(tvSeriesService.generateReportOnS3(), HttpStatus.CREATED);
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
