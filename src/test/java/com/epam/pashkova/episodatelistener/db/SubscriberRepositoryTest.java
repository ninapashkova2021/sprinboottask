package com.epam.pashkova.episodatelistener.db;

import com.epam.pashkova.episodatelistener.db.table.SubscriberUser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class SubscriberRepositoryTest {

    @Autowired
    private SubscriberRepository subscriberRepository;

    @AfterEach
    void tearDown() {
        subscriberRepository.deleteAll();
    }

    @Test
    void verifyThatCreatedUserIsInDb() {
        // given
        SubscriberUser subscriberUser = new SubscriberUser();
        subscriberUser.setLogin("testUser");
        subscriberUser.setPassword("testPassword");
        subscriberRepository.save(subscriberUser);

        // when
        SubscriberUser subscriberUserFromDb = subscriberRepository.findByLogin("testUser");

        // then
        Assertions.assertThat(subscriberUserFromDb).as("Expected user specs are different")
                .isEqualTo(subscriberUser);
    }

    @Test
    void verifyThatNotCreatedUserIsNotInDb() {
        // given
        String notCreatedUserLogin = "testUser";

        // when
        SubscriberUser subscriberUserFromDb = subscriberRepository.findByLogin(notCreatedUserLogin);

        // then
        Assertions.assertThat(subscriberUserFromDb).as("Not created user was found in DB").isEqualTo(null);
    }
}