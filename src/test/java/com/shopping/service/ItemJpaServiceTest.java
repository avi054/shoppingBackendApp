package com.shopping.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.shopping.dbo.Item;
import com.shopping.repository.ItemRepository;

//The  Runwith annotation is JUnit 4 specific annotation. This annotation provides the test run engine. 
//SpringJUnit4ClassRunner  is used to run the test.
//There are many runners available for JUnit one of them is  SpringJUnit4ClassRunner .
//@RunWith(SpringJUnit4ClassRunner.class)
	
//@SpringBootTest
//@ExtendWith(SpringExtension.class)	//Junit 5
@ExtendWith(MockitoExtension.class)
@DisplayName("Service Layer Testcases")
class ItemJpaServiceTest {

	@Mock
	private ItemRepository itemRepository;
	
	@InjectMocks
	private ItemJpaService itemJpaService;
	
	@Test
	@DisplayName("Testcase for adding an item in Repository")
	public void addItem() {
		com.shopping.dto.Item item 
			= new com.shopping.dto.Item(null, "Let Us C++", "Basics of C++ programming", 299d);

		when(itemRepository.save(Mockito.any(Item.class))).thenReturn(Mockito.any());
		
		itemJpaService.addItem(item);//cannot use Mockito.any() here, as it is the actual call
		
		verify(itemRepository).save(Mockito.any(Item.class));
	}
	
	@Test
	@DisplayName("Testcase for getting all items from Repository")
	public void getAllItems() {
		Item i1 = new Item(101, "Let Us C++", "Basics of C++ programming", 299d);
		Item i2 = new Item(102, "Java", "Oracle Certified Java Programmer", 299d);
		List<Item> list = Arrays.asList(i1,i2);
		when(itemRepository.findAll()).thenReturn(list);	//order of when() call matters

		List<com.shopping.dto.Item> allItems = itemJpaService.getAllItems();
		
		assertTrue(allItems.size()==2);
	}
	
	@Test
	@DisplayName("Deleting an item from the Repository valid id")
	public void deleteItem() {
		doNothing().when(itemRepository).deleteById(Mockito.anyInt());
		Boolean bool = itemJpaService.deleteItem(Mockito.anyInt());
		assertTrue(bool);
	}
	
	@Test
	@DisplayName("Exception occurs while deleting item from Repository")
	public void deleteItem_false() {
		doThrow(RuntimeException.class).when(itemRepository).deleteById(Mockito.anyInt());
		Boolean bool = itemJpaService.deleteItem(Mockito.anyInt());
		assertFalse(bool);
	}
	
	@Test
	@DisplayName("Get an item from the repository")
	public void getItem() {
		
		Optional<Item> item = 
				Optional.of(new Item(101, "Let Us C++", "Basics of C++ programming", 299d));

		Mockito.when(itemRepository.findById(Mockito.anyInt())).thenReturn(item);
		com.shopping.dto.Item result = itemJpaService.getItem(Mockito.anyInt());
		
		assertEquals(item.get().getId(), result.getId());
	}
}