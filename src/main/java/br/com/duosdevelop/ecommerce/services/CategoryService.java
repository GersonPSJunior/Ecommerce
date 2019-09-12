package br.com.duosdevelop.ecommerce.services;

import java.util.List;
import java.util.Optional;

import br.com.duosdevelop.ecommerce.dto.CategoryDTO;
import br.com.duosdevelop.ecommerce.services.exceptions.DataIntegrityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.duosdevelop.ecommerce.domain.Category;
import br.com.duosdevelop.ecommerce.repositories.CategoryRepository;
import br.com.duosdevelop.ecommerce.services.exceptions.ObjectNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;
	
	public List<Category> findAll(){
		return repository.findAll();
	}
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
		Category newCategory = find(category.getId());
		updateData(newCategory, category);
		return repository.save(newCategory);
	}

	private void updateData(Category newCategory, Category category) {
		newCategory.setName(category.getName());
	}
	public void delete(Long id) {
		find(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e){
			throw new DataIntegrityException("Não é possível excluir uma categoria com produtos");
		}
	}
	
	public Page<Category> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}

	public Category fromDTO(CategoryDTO obj){
		return new Category(obj.getId(), obj.getName());
	}
}
