package com.datespot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.datespot.auth.RegisterRequest;
import com.datespot.reviews.PostRequest;
import com.datespot.user.ChangeProfileRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class DatespotApplicationTests {

	@Test
	void contextLoads() {
	}

	// @SpringBootTest
	// @AutoConfigureMockMvc
	// public class IntegrationTest {

	// 	@Autowired
	// 	private MockMvc mockMvc;

	// 	@Autowired
	// 	private ObjectMapper objectMapper;

	// 	@Test
	// 	public void fullFlowTest() throws Exception {
	// 		// Register user
	// 		var registerRequest = new RegisterRequest("John", "Doe", "john.doe@example.com", "password123");
	// 		mockMvc.perform(post("/api/v1/auth/register")
	// 				.contentType(MediaType.APPLICATION_JSON)
	// 				.content(objectMapper.writeValueAsString(registerRequest)))
	// 				.andExpect(status().isOk());

	// 		// Authenticate user
	// 		var authRequest = new AuthRequest("john.doe@example.com", "password123");
	// 		MvcResult authResult = mockMvc.perform(post("/api/v1/auth/authenticate")
	// 				.contentType(MediaType.APPLICATION_JSON)
	// 				.content(objectMapper.writeValueAsString(authRequest)))
	// 				.andExpect(status().isOk())
	// 				.andReturn();

	// 		String token = JsonPath.read(authResult.getResponse().getContentAsString(), "$.accessToken");

	// 		// Create 5 posts
	// 		for (int i = 0; i < 5; i++) {
	// 			var postRequest = new PostRequest("Title " + i, "Content " + i, "Location " + i, 5, true);
	// 			mockMvc.perform(post("/api/v1/posts")
	// 					.header("Authorization", "Bearer " + token)
	// 					.contentType(MediaType.APPLICATION_JSON)
	// 					.content(objectMapper.writeValueAsString(postRequest)))
	// 					.andExpect(status().isOk());
	// 		}

	// 		// Change profile to private
	// 		var profileRequest = new ChangeProfileRequest(false, null, null);
	// 		mockMvc.perform(patch("/api/v1/users/profile")
	// 				.header("Authorization", "Bearer " + token)
	// 				.contentType(MediaType.APPLICATION_JSON)
	// 				.content(objectMapper.writeValueAsString(profileRequest)))
	// 				.andExpect(status().isNoContent());

	// 		// Get user's posts - should be empty
	// 		mockMvc.perform(get("/api/v1/posts")
	// 				.header("Authorization", "Bearer " + token))
	// 				.andExpect(status().isOk())
	// 				.andExpect(jsonPath("$.content.length()").value(0));
	// 	}
	// }

}
