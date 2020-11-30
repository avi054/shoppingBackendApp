package com.shopping.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.dto.Item;
import com.shopping.service.ItemJpaService;

@RunWith(SpringRunner.class)
@WebMvcTest(ItemResource.class)
@TestPropertySource("/application-test.properties")
public class ItemResourceTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ItemJpaService itemJpaService;

	@Test
	public void getItem_test_validId() throws Exception {
		String expected = "{\"id\":1,\"name\":\"Let Us C++\" ,\"price\":299.0,\"description\":\"Basics of C++ programming\"}";
		
		Item item = new Item(1, "Let Us C++", "Basics of C++ programming", 299d);
		
		Mockito.when(itemJpaService.getItem(Mockito.anyInt())).thenReturn(item);
		
		RequestBuilder requestBuilder = get("/items/{id}",1)
										.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);		
		//System.out.println("RES:" + result.getResponse().getContentAsString());
		verify(itemJpaService, times(1)).getItem(Mockito.anyInt());		
	}
	
	@Test
	public void getItem_test_inValidId() throws Exception {
		
		Mockito.when(itemJpaService.getItem(Mockito.anyInt())).thenReturn(null);
		
		mockMvc.perform(get("/items/{id}",88).accept(MediaType.APPLICATION_JSON))
						.andExpect(status().isNotFound()).andReturn();
		
		verify(itemJpaService, times(1)).getItem(Mockito.anyInt());		
	}
	
	@Test
	public void getAllItems_test() throws Exception {
		List<Item> items = new ArrayList<>();
		items.add(new Item(1, "Let Us C++", "Basics of C++ programming", 299d));
		items.add(new Item(3, "Java Black book", "Advanced concepts in java", 599.9));

		ObjectMapper ob = new ObjectMapper();
		String expected = ob.writeValueAsString(items);
		
		Mockito.when(itemJpaService.getAllItems()).thenReturn(items);
		MvcResult result = mockMvc.perform(get("/items")).andExpect(status().isOk()).andReturn();
		
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
		verify(itemJpaService, times(1)).getAllItems();
	}
	
	@Test
	public void addItem_test() throws Exception {
		
		Item item = new Item(1, "Let Us C++", "Basics of C++ programming", 299d); 
		ObjectMapper mapper = new ObjectMapper();
		String itemJson = mapper.writeValueAsString(item);
		
		doNothing().when(itemJpaService).addItem(Mockito.any(Item.class));
		
		mockMvc.perform(post("/items").content(itemJson)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
		
		verify(itemJpaService, times(1)).addItem((Mockito.any(Item.class)));
	}
	
	@Test
	public void deleteItem_test() throws Exception {

		when(itemJpaService.deleteItem(Mockito.anyInt())).thenReturn(true);
		
		mockMvc.perform(delete("/items/{id}",1)).andExpect(status().isNoContent());
		
		verify(itemJpaService).deleteItem(Mockito.anyInt());
	}
	
	@Test
	public void deleteItem_test_inValidId() throws Exception {

		when(itemJpaService.deleteItem(Mockito.anyInt())).thenReturn(false);
		
		mockMvc.perform(delete("/items/{id}",55)).andExpect(status().isNotFound());
		
		verify(itemJpaService).deleteItem(Mockito.anyInt());
	}
	
}
