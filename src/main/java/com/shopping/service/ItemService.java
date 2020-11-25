//package com.shopping.service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.stereotype.Service;
//
//import com.shopping.dto.Item;
//
//@Service
//public class ItemService {
//
//	private static List<Item> items = new ArrayList<>();
//	private static int idCounter = 0;
//	
//	static {
//		items.add(new Item(++idCounter, "Let Us C++", "Basics of C++ programming", 299d));
//		items.add(new Item(++idCounter, "Java OCJP", "Java Concepts", 799d));
//		items.add(new Item(++idCounter , "Java Black book", "Advanced concepts in java", 599.9));
//		items.add(new Item(++idCounter, "Let Us C++", "Basics of C++ programming", 299d));
//		items.add(new Item(++idCounter, "Java OCJP", "Java Concepts", 799d));
//		items.add(new Item(++idCounter , "Java Black book", "Advanced concepts in java", 599.9));
//	
//	}
//	
//	public List<Item> getAllItems(){
//		return items;
//	}
//
//	public Item deleteItemById(Integer id) {
//		Item item = getItem(id);
//		if(item!=null && items.remove(item)) {
//			return item;
//		}
//		return null;
//	}
//	
//	public Item getItem(Integer id) {
//		Optional<Item> item = items.stream().filter(i->i.getId()==id).findAny();
//		if(item.isPresent()) {
//			return item.get();
//		}
//		return null;
//	}
//
//	public Item addItem(Item item) {
//		return save(item);
//	}
//	
//	public Item save(Item item) {
//		if(item.getId()==-1 || item.getId()==0) {
//			item.setId(++idCounter);
//			items.add(item);
//		}else {
//			deleteItemById(item.getId());
//			items.add(item);
//		}
//		return item;
//	}
//}
