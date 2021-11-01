package com.epam.pashkova.episodatelistener.controller;

import com.epam.pashkova.episodatelistener.db.table.SubscriberUser;
import com.epam.pashkova.episodatelistener.service.SubscriberService;
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
public class SubscriberController {

    @Autowired
    private SubscriberService subscriberService;

    @GetMapping("/subscribers")
    public List<SubscriberUser> getSubscribers() {
        return subscriberService.getSubscribers();
    }

    @PostMapping("/addSubscriber")
    public ResponseEntity<SubscriberUser> addSubscriber(@RequestHeader("subscriberName") String subscriberName,
                                                        @RequestHeader("password") String password) {
        return new ResponseEntity<>(subscriberService.addSubscriber(subscriberName, password), HttpStatus.CREATED);
    }

    @DeleteMapping("/removeSubscriber")
    public void removeSubscriber(@RequestHeader("subscriberName") String subscriberName) {
        subscriberService.removeSubscriber(subscriberName);
    }
}
