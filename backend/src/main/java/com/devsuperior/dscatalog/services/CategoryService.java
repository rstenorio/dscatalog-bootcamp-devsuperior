package com.devsuperior.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.DTO.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.EntityNotFoundException;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository repository;
	
	@Transactional(readOnly = true )
	public List<CategoryDTO> findAll(){
		
		List<Category> list = repository.findAll();
		
		//Expressao LAMBDA
		//(x -> new CategoryDTO)

		return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
		
		
		/* foreach
		List<CategoryDTO> listDTO = new ArrayList<>();
		for(Category cat : list) {
			listDTO.add(new CategoryDTO(cat));
		}
		*/		
		
		
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = repository.findById(id);
		Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Category not found!!"));
		return new CategoryDTO(entity);
	}
}
