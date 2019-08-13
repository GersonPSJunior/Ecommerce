package br.com.duosdevelop.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.duosdevelop.ecommerce.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
