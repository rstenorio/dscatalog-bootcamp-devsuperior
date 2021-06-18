package com.devsuperior.dscatalog.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.DTO.ProductDTO;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

@SpringBootTest
@Transactional
public class ProductServiceIT {

	@Autowired
	private ProductService service;

	@Autowired
	private ProductRepository repository;

	private Long idExisting;
	private Long idNonExisting;
	private Long countTotalProdcts;
	
	private Pageable pageable;

	@BeforeEach
	void setUp() throws Exception {
		idExisting = 1L;
		idNonExisting = 100L;
		countTotalProdcts = 25L;
	}

	@Test
	public void deleteShouldDeleteResourceWhenIdExists() {
		service.delete(idExisting);
		Assertions.assertEquals(countTotalProdcts - 1, repository.count());
	}

	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdNonExists() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(idNonExisting);
		});
		
	}
	
	@Test
	public void findAllPagedShouldReturnPageWhenPage0Size10() {
		//PageRequest PR = PageRequest.of(0, 10);
		
		Page<ProductDTO> res = service.findAllPaged(null, null, PageRequest.of(0, 10));

		Assertions.assertFalse(res.isEmpty());
		Assertions.assertEquals(0, res.getNumber());
		Assertions.assertEquals(10, res.getSize());
		Assertions.assertEquals(countTotalProdcts, res.getTotalElements());
	}

	 
	@Test
	public void findAllPagedShouldReturnPageWhenPageDoesntExist() {
		//PageRequest PR = PageRequest.of(0, 10);
		
		Page<ProductDTO> res = service.findAllPaged(null, null, PageRequest.of(50, 10));

		Assertions.assertTrue(res.isEmpty());
	}
	
	@Test
	public void findAllPagedShouldReturnSortPagedWhenSortByName() {
		PageRequest PR = PageRequest.of(0, 10,Sort.by("name"));
		Page<ProductDTO> res = service.findAllPaged(null, null, PR);
		
		Assertions.assertFalse(res.isEmpty());
		Assertions.assertEquals("Macbook Pro", res.getContent().get(0).getName()); 
		Assertions.assertEquals("PC Gamer", res.getContent().get(1).getName()); 
		Assertions.assertEquals("PC Gamer Alfa", res.getContent().get(2).getName()); 
	}
}


