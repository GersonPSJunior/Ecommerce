package br.com.duosdevelop.ecommerce.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.duosdevelop.ecommerce.domain.Category;
import br.com.duosdevelop.ecommerce.domain.Customer;
import br.com.duosdevelop.ecommerce.repositories.CustomerRepository;
import br.com.duosdevelop.ecommerce.services.exceptions.ObjectNotFoundException;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository repository;
	
	public Customer find(Long id) {
		Optional<Customer> customer = repository.findById(id);
		
		return customer.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: "+ id +
				", Tipo:"+ Category.class.getName()));
	}
}
