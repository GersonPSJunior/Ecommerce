package br.com.duosdevelop.ecommerce;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.duosdevelop.ecommerce.domain.Category;
import br.com.duosdevelop.ecommerce.domain.City;
import br.com.duosdevelop.ecommerce.domain.Product;
import br.com.duosdevelop.ecommerce.domain.State;
import br.com.duosdevelop.ecommerce.repositories.CategoryRepository;
import br.com.duosdevelop.ecommerce.repositories.CityRepository;
import br.com.duosdevelop.ecommerce.repositories.ProductRepository;
import br.com.duosdevelop.ecommerce.repositories.StateRepository;

@SpringBootApplication
public class EcommerceApplication implements CommandLineRunner{

	@Autowired
	private CategoryRepository repositoryCategory;
	
	@Autowired
	private ProductRepository repositoryProduct;
	
	@Autowired
	private StateRepository repositoryState;
	
	@Autowired
	private CityRepository repositoryCity;
	
	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Category cat1 = new Category(null, "Informática");
		Category cat2 = new Category(null, "Escritório");
		Product prod1 = new Product(null, "Computador", 2000.00);
		Product prod2 = new Product(null, "Impressora", 800.00);
		Product prod3 = new Product(null, "Mouse", 80.00);
		State mg = new State(null, "Minas Gerais");
		State sp = new State(null, "São Paulo");
		City ubCity = new City(null, "Uberlândia", mg);
		City spCity = new City(null, "São Paulo", sp);
		City cmCity = new City(null, "Campinas", sp);
		
		cat1.getProducts().addAll(Arrays.asList(prod1, prod2, prod3));
		cat2.getProducts().addAll(Arrays.asList(prod2));
		prod1.getCategories().addAll(Arrays.asList(cat1));
		prod2.getCategories().addAll(Arrays.asList(cat1, cat2));
		prod3.getCategories().addAll(Arrays.asList(cat1));
		
		mg.getCities().addAll(Arrays.asList(ubCity));
		sp.getCities().addAll(Arrays.asList(spCity, cmCity));
		
		repositoryCategory.saveAll(Arrays.asList(cat1, cat2));
		repositoryProduct.saveAll(Arrays.asList(prod1, prod2, prod3));
		repositoryState.saveAll(Arrays.asList(mg, sp));
		repositoryCity.saveAll(Arrays.asList(ubCity, spCity, cmCity));
	}

}
