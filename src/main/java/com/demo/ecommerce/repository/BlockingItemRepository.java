package com.demo.ecommerce.repository;

import com.demo.ecommerce.domain.Item;
import org.springframework.data.repository.CrudRepository;

public interface BlockingItemRepository extends CrudRepository<Item, String> {
}
