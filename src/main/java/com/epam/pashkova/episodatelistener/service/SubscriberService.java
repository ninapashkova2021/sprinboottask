package com.epam.pashkova.episodatelistener.service;

import com.epam.pashkova.episodatelistener.db.SubscriberRepository;
import com.epam.pashkova.episodatelistener.db.table.SubscriberUser;
import com.epam.pashkova.episodatelistener.exception.SubscriberDuplicateException;
import com.epam.pashkova.episodatelistener.exception.SubscriberNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriberService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriberService.class);

    @Autowired
    private SubscriberRepository subscriberRepository;

    public List<SubscriberUser> getSubscribers() {
        return subscriberRepository.findAll();
    }

    public SubscriberUser addSubscriber(String login, String password) {
        SubscriberUser subscriberUser = subscriberRepository.findByLogin(login);
        if (subscriberUser != null) {
            throw new SubscriberDuplicateException(String.format("Subscriber %s was already created", login));
        }
        subscriberUser = new SubscriberUser();
        subscriberUser.setLogin(login);
        subscriberUser.setPassword(password);
        return subscriberRepository.save(subscriberUser);
    }

    public void removeSubscriber(String userName) {
        SubscriberUser subscriberUser = subscriberRepository.findByLogin(userName);
        if (subscriberUser == null) {
            throw new SubscriberNotFoundException(String.format("Subscriber %s wasn't found", userName));
        }
        subscriberRepository.delete(subscriberUser);
    }
}
