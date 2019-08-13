package br.com.duosdevelop.ecommerce.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.duosdevelop.ecommerce.domain.Category;
import br.com.duosdevelop.ecommerce.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;
	
	public Category find(Long id) {
		Optional<Category> category = repository.findById(id);
		return category.orElse(null);
	}
}
