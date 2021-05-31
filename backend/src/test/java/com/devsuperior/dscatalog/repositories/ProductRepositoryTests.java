package com.devsuperior.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.factory.Factory;

@DataJpaTest
public class ProductRepositoryTests {
	@Autowired
	ProductRepository repository;

	private long existId; 
	private long DoesnottexistId; 
	
	@BeforeEach
	void setUp() throws Exception{
		existId = 1L; 
		DoesnottexistId = 100L; 
	}
	

	@Test
	public void saveShouldPersistWithAutoIncrementWhenIsIdNull() {
		Product product = Factory.createProduct();
		product.setId(null);
		product = repository.save(product);
		
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(26, product.getId());
	}
	
	
	@Test
	public void deleteShouldDeleteWhenIdExists() {
		
		repository.deleteById(existId);
		Optional<Product> result = repository.findById(existId);
		Assertions.assertFalse(result.isPresent());
	}

	@Test
	public void deleteShouldThrowExceptionWhenIdDoesntExist() {
	
		Assertions.assertThrows(EmptyResultDataAccessException.class, ()->{
		repository.deleteById(DoesnottexistId);
		});
	}
		
	@Test
	public void findByIdShouldLoadObjectWhenIdExists() {
		Optional<Product> res = repository.findById(existId);
		Assertions.assertFalse(res.isEmpty());
	}
	
	@Test
	public void findByIdShouldEmptyObjectWhenIdDoesntExists() {
		//Optional<Product> res = repository.findById(DoesnottexistId).isEmpty();
		Assertions.assertTrue(repository.findById(DoesnottexistId).isEmpty());
	}
}
