package com.epam.pashkova.episodatelistener.service;

import com.epam.pashkova.episodatelistener.db.SubscriberRepository;
import com.epam.pashkova.episodatelistener.db.table.SubscriberUser;
import com.epam.pashkova.episodatelistener.exception.SubscriberDuplicateException;
import com.epam.pashkova.episodatelistener.exception.SubscriberNotFoundException;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class SubscriberServiceTest {

    @Mock
    private SubscriberRepository subscriberRepository;

    @InjectMocks
    private SubscriberService subscriberService;

    @Test
    void verifyThatGetSubscribersReturnsSubscribers() {
        // when
        subscriberService.getSubscribers();

        // then
        Mockito.verify(subscriberRepository).findAll();
    }

    @Test
    void verifyThatSubscriberWasAdded() {
        // given
        String login = "testSubscriber";
        String password = "testPassword";
        SubscriberUser expectedSubscriberUser = new SubscriberUser();
        expectedSubscriberUser.setLogin(login);
        expectedSubscriberUser.setPassword(password);

        // when
        subscriberService.addSubscriber(login, password);

        // then
        ArgumentCaptor<SubscriberUser> subscriberUserArgumentCaptor = ArgumentCaptor.forClass(SubscriberUser.class);
        Mockito.verify(subscriberRepository).save(subscriberUserArgumentCaptor.capture());

        SubscriberUser actualSubscriberUser = subscriberUserArgumentCaptor.getValue();
        Assertions.assertThat(actualSubscriberUser).as("Subscriber isn't the same with expected")
                .isEqualTo(expectedSubscriberUser);
    }

    @Test
    void verifyThatSubscriberWasNotAdded() {
        // given
        String login = "testSubscriber";
        String password = "testPassword";
        SubscriberUser fakeSubscriberUser = new SubscriberUser();
        fakeSubscriberUser.setLogin(login);
        fakeSubscriberUser.setPassword(password);

        // when
        BDDMockito.given(subscriberRepository.findByLogin(login)).willReturn(fakeSubscriberUser);
        ThrowableAssert.ThrowingCallable throwingCallable = () -> subscriberService.addSubscriber(login, password);

        // then
        assertThatThrownBy(throwingCallable)
                .as("It should cause exception")
                .isInstanceOf(SubscriberDuplicateException.class);
        Mockito.verify(subscriberRepository, Mockito.never()).save(ArgumentMatchers.any());
    }

    @Test
    void verifyThatSubscriberWasRemoved() {
        // given
        String login = "testSubscriber";
        String password = "testPassword";
        SubscriberUser fakeSubscriberUser = new SubscriberUser();
        fakeSubscriberUser.setLogin(login);
        fakeSubscriberUser.setPassword(password);

        // when
        BDDMockito.given(subscriberRepository.findByLogin(login)).willReturn(fakeSubscriberUser);
        subscriberService.removeSubscriber(login);

        // then
        Mockito.verify(subscriberRepository, Mockito.times(1)).delete(ArgumentMatchers.any());
    }

    @Test
    void verifyThatSubscriberWasNotRemoved() {
        // given
        String login = "testSubscriber";

        // when
        BDDMockito.given(subscriberRepository.findByLogin(login)).willReturn(null);
        ThrowableAssert.ThrowingCallable throwingCallable = () -> subscriberService.removeSubscriber(login);

        // then
        assertThatThrownBy(throwingCallable)
                .as("It should cause exception")
                .isInstanceOf(SubscriberNotFoundException.class);
        Mockito.verify(subscriberRepository, Mockito.never()).delete(ArgumentMatchers.any());
    }
}