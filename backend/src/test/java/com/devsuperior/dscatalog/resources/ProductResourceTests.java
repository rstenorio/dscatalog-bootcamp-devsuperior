package com.devsuperior.dscatalog.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.devsuperior.dscatalog.DTO.ProductDTO;
import com.devsuperior.dscatalog.factory.Factory;
import com.devsuperior.dscatalog.services.ProductService;
import com.devsuperior.dscatalog.services.exceptions.DBException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProductResource.class)
public class ProductResourceTests {

	@Autowired
	private MockMvc mockMcv;
	
	@MockBean
	private ProductService service;
	
	@Autowired
	private ObjectMapper objMapper;
	
	private ProductDTO dto;
	private PageImpl<ProductDTO> page;
	
	private Long idExisting;
	private Long idNonExisting;
	private Long dependentId;;

	@BeforeEach
	void setUp() throws Exception{
		idExisting =1L;
		idNonExisting = 2L;
		dependentId = 3L;
		
		dto = Factory.createProductDTO();
		page = new PageImpl<>(List.of(dto));
		
		when(service.findAllPaged(any())).thenReturn(page);
		
		when(service.findById(idExisting)).thenReturn(dto);
		when(service.findById(idNonExisting)).thenThrow(ResourceNotFoundException.class);
		

		when(service.update(eq(idExisting), any())).thenReturn(dto);
		when(service.update(eq(idNonExisting), any())).thenThrow(ResourceNotFoundException.class);


		doNothing().when(service).delete(idExisting);
		doThrow(ResourceNotFoundException.class).when(service).delete(idNonExisting);
		doThrow(DBException.class).when(service).delete(dependentId);
		
		when(service.insert(any())).thenReturn(dto);
		
	}
	
	@Test
	public void deleteShouldDoNothingWhenIdExists()throws Exception{

		ResultActions res = mockMcv.perform(delete("/products/{id}",idExisting));
		res.andExpect(status().isNoContent());
	}
	
	@Test
	public void deleteShouldResourceNotFoundExceptionWhenIdNonExists()throws Exception{
		ResultActions res = mockMcv.perform(delete("/products/{id}",idNonExisting));
		res.andExpect(status().isNotFound());
	}
	
	@Test
	public void insertShouldReturnProduct() throws Exception{
		String jsonBody = objMapper.writeValueAsString(dto);
		
		ResultActions res = mockMcv.perform(post("/products/",dto)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		res.andExpect(status().isCreated());
		res.andExpect(jsonPath("$.id").exists());
		res.andExpect(jsonPath("$.name").exists());
	}
	

	@Test
	public void updateShouldReturnProductWhenIdexists() throws Exception {
		String jsonBody = objMapper.writeValueAsString(dto);
		
		ResultActions res = mockMcv.perform(put("/products/{id}",idExisting)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		res.andExpect(status().isOk());
		res.andExpect(jsonPath("$.id").exists());
	}

	@Test
	public void updateShouldReturnNotFoundWhenIdNonexists() throws Exception {
		String jsonBody = objMapper.writeValueAsString(dto);
		
		ResultActions res = mockMcv.perform(put("/products/{id}",idNonExisting)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		res.andExpect(status().isNotFound());
	}
	
	@Test
	public void findAllShouldReturnPage() throws Exception {
		ResultActions res = mockMcv.perform(get("/products")
				.accept(MediaType.APPLICATION_JSON));
		
		res.andExpect(status().isOk());
	}
	
	@Test
	public void findByIdShouldReturnProductWhenIdExists() throws Exception{
		ResultActions res = mockMcv.perform(get("/products/{id}", idExisting)
				.accept(MediaType.APPLICATION_JSON));
		res.andExpect(status().isOk());
		res.andExpect(jsonPath("$.id").exists());
	}
	

	@Test
	public void findByIdShouldReturnNotFoundWhenIdNonExists() throws Exception {
		ResultActions res = mockMcv.perform(get("/products/{id}",idNonExisting)
				.accept(MediaType.APPLICATION_JSON));
		
		res.andExpect(status().isNotFound());		
	}
}
