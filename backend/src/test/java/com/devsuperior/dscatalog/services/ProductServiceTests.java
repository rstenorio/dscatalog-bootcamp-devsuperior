package com.devsuperior.dscatalog.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dscatalog.DTO.ProductDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.factory.Factory;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.DBException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

	@InjectMocks
	private ProductService service;

	@Mock
	private ProductRepository repository;

	@Mock
	private CategoryRepository catRepository;

	private Long IdExists;
	private Long NonIdExists;
	private Long dependentId;
	private PageImpl<Product> page;
	private Product product;
	private Category category;
	private ProductDTO productDTO;

	@BeforeEach
	void setUp() throws Exception {
		IdExists = 1L;
		NonIdExists = 100L;
		dependentId = 4L;
		product = Factory.createProduct();
		category = Factory.createCategory();
		productDTO = Factory.createProductDTO();
		page = new PageImpl<>(List.of(product));		

		// SIMULACAO DE COMPORTAMENTO MOCKADOS
		Mockito.when(repository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);
		Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(product);
		Mockito.when(repository.findById(IdExists)).thenReturn(Optional.of(product));
		Mockito.when(repository.findById(NonIdExists)).thenReturn(Optional.empty());
		Mockito.doNothing().when(repository).deleteById(IdExists);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(NonIdExists);

		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);

		Mockito.when(repository.getOne(IdExists)).thenReturn(product);
		Mockito.when(repository.getOne(NonIdExists)).thenThrow(EntityNotFoundException.class);

		Mockito.when(catRepository.getOne(IdExists)).thenReturn(category);
		Mockito.when(catRepository.getOne(NonIdExists)).thenThrow(EntityNotFoundException.class);
	}

	@Test
	public void updateShouldReturnProductDTOWhenIdExists() {

		ProductDTO res = service.update(IdExists, productDTO);
		Assertions.assertNotNull(res);
	}
	
	@Test
	public void updateShouldThrowEntityNotFoundExceptionWhenNonIdExists() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.update(NonIdExists, productDTO);
		});
	}
	
	@Test
	public void findByIdShouldReturnProductDTOOWhenIdExists() {
		ProductDTO res = service.findById(IdExists);
		Assertions.assertNotNull(res);
	}

	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenNonIdExists() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(NonIdExists);
		});
	}


	@Test
	public void findAllPagedShouldReturnPage() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<ProductDTO> res = service.findAllPaged(null, null, pageable);

		Assertions.assertNotNull(res);

		Mockito.verify(repository).findAll(pageable);
	}

	@Test
	public void deleteShouldThrowDBExceptionWhenNonIdExists() {
		Assertions.assertThrows(DBException.class, () -> {
			service.delete(dependentId);
		});

		Mockito.verify(repository, Mockito.times(1)).deleteById(dependentId);
	}

	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenNonIdExists() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(NonIdExists);
		});

		Mockito.verify(repository, Mockito.times(1)).deleteById(NonIdExists);
	}

	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		Assertions.assertDoesNotThrow(() -> {
			service.delete(IdExists);
		});
		Mockito.verify(repository, Mockito.times(1)).deleteById(IdExists);
	}

}
