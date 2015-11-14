package com.walkernation.rest.controllers;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import com.walkernation.rest.controllers.fixture.RestEventFixtures;
import com.walkernation.rest.core.events.RequestStoryDataReadEvent;
import com.walkernation.rest.core.events.StoryDataCreateEvent;
import com.walkernation.rest.core.services.StoryDataServiceInterface;

//@WebAppConfiguration
public class OrderStatusIntegrationTest {

//	MockMvc mockMvc;
//
//	@InjectMocks
//	StoryDataControllerV1 controller;
//
//	@Mock
//	StoryDataServiceInterface storyService;
//
//	UUID key = UUID.fromString("f3512d26-72f6-4290-2365-63ad69eccc13");
//
//	@Before
//	public void setup() {
//
//		MockitoAnnotations.initMocks(this);
//		controller.setStoryService(storyService);
//		this.mockMvc = standaloneSetup(controller).setMessageConverters(
//				new Jaxb2RootElementHttpMessageConverter()).build();
//
//		// when(orderService.createOrder(any(CreateOrderEvent.class))).thenReturn(
//		// orderCreated(UUID.fromString("f3512d26-72f6-4290-9265-63ad69eccc13")));
//
//	}
//
//	@Test
//	public void thatViewOrderStatusUsesHttpNotFound() throws Exception {
//
//		when(
//				storyService
//						.requestStoryData(any(RequestStoryDataReadEvent.class)))
//				.thenReturn(RestEventFixtures.storyDataNotFound(key));
//
//		if (this.mockMvc == null) {
//			fail("mockMVC was null");
//		}
//
//		// this.mockMvc.perform(get("/v1/stories/{id}", key.toString()));
//		this.mockMvc
//				.perform(
//						get("/v1/stories/{id}", key.toString()).accept(
//								MediaType.APPLICATION_JSON)).andDo(print())
//				.andExpect(status().isNotFound());
//	}
//
//	@Test
//	public void thatViewOrderStatusUsesHttpFound() throws Exception {
//		when(
//				storyService
//						.requestStoryData(any(RequestStoryDataReadEvent.class)))
//				.thenReturn(RestEventFixtures.storyDataFound(key));
//
//		if (this.mockMvc == null) {
//			fail("mockMVC was null");
//		}
//
//		// this.mockMvc.perform(get("/v1/stories/{id}", key.toString()));
//		this.mockMvc
//				.perform(
//						get("/v1/stories/{id}", key.toString()).accept(
//								MediaType.ALL)).andDo(print())
//				.andExpect(status().isNotAcceptable());
//	}
//
//	@Test
//	public void creationOfNewStory() throws Exception {
//		when(storyService.createStoryData(any(StoryDataCreateEvent.class)))
//				.thenReturn(
//						RestEventFixtures.orderCreated(UUID
//								.fromString("f3512d26-72f6-4290-9265-63ad69eccc13")));
//
//		this.mockMvc
//				.perform(
//						post("/v1/stories/").content(RestEventFixtures.getJSON())
//								.contentType(MediaType.APPLICATION_JSON)
//								.accept(MediaType.APPLICATION_JSON))
//				.andDo(print()).andExpect(status().isCreated());
//
//	}

	// @Test
	// public void thatViewOrderUsesHttpOK() throws Exception {
	//
	// when(orderService.requestOrderStatus(any(RequestOrderStatusEvent.class))).thenReturn(
	// orderStatus(key, "Cooking"));
	//
	// this.mockMvc.perform(
	// get("/aggregators/orders/{id}/status", key.toString())
	// .accept(MediaType.APPLICATION_JSON))
	// .andDo(print())
	// .andExpect(status().isOk());
	// }

	// @Test
	// public void thatViewOrderRendersJSONCorrectly() throws Exception {
	//
	// when(orderService.requestOrderStatus(any(RequestOrderStatusEvent.class))).thenReturn(
	// orderStatus(key, "Cooking"));
	//
	// this.mockMvc.perform(
	// get("/aggregators/orders/{id}/status", key.toString())
	// .accept(MediaType.APPLICATION_JSON))
	// .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	// .andExpect(jsonPath("$.orderId").value(key.toString()))
	// .andExpect(jsonPath("$.status").value("Cooking"));
	// }
	//
	// @Test
	// public void thatViewOrderRendersXMLCorrectly() throws Exception {
	//
	// when(orderService.requestOrderStatus(any(RequestOrderStatusEvent.class))).thenReturn(
	// orderStatus(key, "Cooking"));
	//
	// this.mockMvc.perform(
	// get("/aggregators/orders/{id}/status", key.toString())
	// .accept(MediaType.TEXT_XML))
	// .andDo(print())
	// .andExpect(content().contentType(MediaType.TEXT_XML))
	// .andExpect(xpath("/orderStatus/orderId").string(key.toString()))
	// .andExpect(xpath("/orderStatus/status").string("Cooking"));
	// }
}
