package br.com.duosdevelop.ecommerce.services;

import java.util.Optional;

import br.com.duosdevelop.ecommerce.services.exceptions.DataIntegrityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.duosdevelop.ecommerce.domain.Category;
import br.com.duosdevelop.ecommerce.repositories.CategoryRepository;
import br.com.duosdevelop.ecommerce.services.exceptions.ObjectNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;
	
	public Category find(Long id) {
		Optional<Category> category = repository.findById(id);
		
		return category.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: "+ id +
				", Tipo:"+ Category.class.getName()));
	}

	public Category insert(Category category){
		category.setId(null);
		return repository.save(category);
	}

	public Category update(Category category) {
		find(category.getId());
		return repository.save(category);
	}

	public void delete(Long id) {
		find(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e){
			throw new DataIntegrityException("Não é possível excluir uma categoria com produtos");
		}
	}
}
