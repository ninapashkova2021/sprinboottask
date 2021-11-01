package com.epam.pashkova.episodatelistener;

import com.epam.pashkova.episodatelistener.db.SubscriberRepository;
import com.epam.pashkova.episodatelistener.db.table.SubscriberUser;
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
class SubscriberIntegrationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private SubscriberRepository subscriberRepository;

	@AfterEach
	public void tearDown() {
		subscriberRepository.deleteAll();
	}

	@Test
	void verifyThatListOfSubscribersWasReturned() throws Exception {
		String login = "testLogin";
		String password = "testPassword";
		SubscriberUser subscriberUser = new SubscriberUser();
		subscriberUser.setLogin(login);
		subscriberUser.setPassword(password);
		subscriberRepository.save(subscriberUser);

		mockMvc.perform(get("/subscribers")).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].login", Matchers.is(login)))
				.andExpect(jsonPath("$[0].password", Matchers.is(password)));
	}

	@Test
	void verifyThatSubscriberWasAdded() throws Exception {
		String login = "testLogin";
		String password = "testPassword";
		SubscriberUser subscriberUser = new SubscriberUser();
		subscriberUser.setLogin(login);
		subscriberUser.setPassword(password);

		mockMvc.perform(post("/addSubscriber")
						.header("subscriberName", login)
						.header("password", password))
				.andExpect(status().isCreated())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.login", Matchers.is(login)))
				.andExpect(jsonPath("$.password", Matchers.is(password)));
	}

	@Test
	void verifyThatSubscriberWasRemoved() throws Exception {
		String login = "testLogin";
		String password = "testPassword";
		SubscriberUser subscriberUser = new SubscriberUser();
		subscriberUser.setLogin(login);
		subscriberUser.setPassword(password);
		subscriberRepository.save(subscriberUser);

		mockMvc.perform(delete("/removeSubscriber")
				.header("subscriberName", login))
				.andExpect(status().isOk());

	}

}
