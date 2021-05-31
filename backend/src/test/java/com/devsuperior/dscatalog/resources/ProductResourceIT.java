package com.devsuperior.dscatalog.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.DTO.ProductDTO;
import com.devsuperior.dscatalog.factory.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductResourceIT {

	@Autowired
	private MockMvc mockMVC;
	
	private Long idExisting;
	private Long idNonExisting;
	private Long countTotalProdcts;

	@Autowired
	private ObjectMapper objMapper;

	
	@BeforeEach
	void setUp() throws Exception {
		idExisting = 1L;
		idNonExisting = 100L;
		countTotalProdcts = 25L;
	}
	
	@Test
	public void updateShouldReturnNotFoundWhenIdeNonxists() throws Exception {
		ProductDTO dto = Factory.createProductDTO();
				
		String jsonBody = objMapper.writeValueAsString(dto);
		
		ResultActions res = mockMVC.perform(put("/products/{id}",idNonExisting)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		res.andExpect(status().isNotFound());
	}

	@Test
	public void updateShouldReturnProductWhenIdexists() throws Exception {
		ProductDTO dto = Factory.createProductDTO();
		
		String expectedName = dto.getName();
		Double expectedPrice = dto.getPrice();
		
		String jsonBody = objMapper.writeValueAsString(dto);
		
		ResultActions res = mockMVC.perform(put("/products/{id}",idExisting)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		res.andExpect(status().isOk());
		res.andExpect(jsonPath("$.id").exists());
		res.andExpect(jsonPath("$.name").value(expectedName));
		res.andExpect(jsonPath("$.price").value(expectedPrice));
		
	}
	
	@Test
	public void findAllShouldReturnSortedByNameWhenSortName()throws Exception{

		ResultActions res = mockMVC.perform(get("/products?page=0&size=12&sort=name,asc"));
		res.andExpect(status().isOk());
		res.andExpect(jsonPath("$.totalElements").value(countTotalProdcts));
		res.andExpect(jsonPath("$.content").exists());
		res.andExpect(jsonPath("$.content[0].name").value("Macbook Pro"));
		res.andExpect(jsonPath("$.content[1].name").value("PC Gamer"));
		res.andExpect(jsonPath("$.content[2].name").value("PC Gamer Alfa"));

	}
	
}
