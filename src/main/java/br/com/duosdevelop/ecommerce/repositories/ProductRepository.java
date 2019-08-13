package br.com.duosdevelop.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.duosdevelop.ecommerce.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
