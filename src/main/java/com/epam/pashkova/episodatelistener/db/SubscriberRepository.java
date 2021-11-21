package com.epam.pashkova.episodatelistener.db;

import com.epam.pashkova.episodatelistener.db.table.SubscriberUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriberRepository extends JpaRepository<SubscriberUser, Long> {
    SubscriberUser findByLogin(String login);
}
