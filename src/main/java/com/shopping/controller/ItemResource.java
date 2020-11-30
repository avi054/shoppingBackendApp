package com.shopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.dto.Item;
//import com.shopping.service.ItemService;
import com.shopping.service.ItemJpaService;

@RestController
@CrossOrigin
public class ItemResource {
	
	@Autowired
	private ItemJpaService itemJpaService;

	@PostMapping("/items")
	public ResponseEntity<Void> addItem(@RequestBody Item item) {
		itemJpaService.addItem(item);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping("/items")
	public List<Item> getAllItems() {
		return itemJpaService.getAllItems();
	}
	
	@DeleteMapping("/items/{id}")
	public ResponseEntity<Void> deleteItem(@PathVariable Integer id) {
		if(itemJpaService.deleteItem(id)) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/items/{id}")
	public ResponseEntity<Item> getItem(@PathVariable Integer id) {
		Item item = itemJpaService.getItem(id);
		if(item != null) {
			return new ResponseEntity<Item>(item, HttpStatus.OK);
		}
		return ResponseEntity.notFound().build();
	}
	
}
