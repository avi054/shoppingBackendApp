package com.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopping.dbo.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer>{

}
