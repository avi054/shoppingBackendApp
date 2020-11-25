package com.shopping.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopping.dto.Item;
import com.shopping.repository.ItemRepository;

@Service
public class ItemJpaService {

	@Autowired
	private ItemRepository itemRepository;
	
	public void addItem(Item item) {
		com.shopping.dbo.Item newItem = new com.shopping.dbo.Item();
		newItem.setName(item.getName());
		newItem.setDescription(item.getDescription());
		newItem.setPrice(item.getPrice());
		itemRepository.save(newItem);
	}

	public List<Item> getAllItems() {
		List<com.shopping.dbo.Item> itemList = itemRepository.findAll();
		
		return itemList.stream().map(i->{
			Item item = new Item();
			item.setId(i.getId());
			item.setName(i.getName());
			item.setDescription(i.getDescription());
			item.setPrice(i.getPrice());
			return item;
		}).collect(Collectors.toList());
	}

	public boolean deleteItem(Integer id) {
		try {
			itemRepository.deleteById(id);
		return true;
		}catch (Exception e) {
			return false;
		}
	}

	public Item getItem(Integer id) {
		Optional<com.shopping.dbo.Item> i = itemRepository.findById(id);
		Item item = new Item();
		
		if(i.isPresent()) {
			item.setId(i.get().getId());
			item.setName(i.get().getName());
			item.setDescription(i.get().getDescription());
			item.setPrice(i.get().getPrice());
		}
		return item;
	}
}
