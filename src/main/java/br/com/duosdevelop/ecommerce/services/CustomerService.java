package br.com.duosdevelop.ecommerce.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.duosdevelop.ecommerce.domain.Address;
import br.com.duosdevelop.ecommerce.domain.Category;
import br.com.duosdevelop.ecommerce.domain.City;
import br.com.duosdevelop.ecommerce.domain.Customer;
import br.com.duosdevelop.ecommerce.domain.enums.TypeCustomer;
import br.com.duosdevelop.ecommerce.dto.CustomerDTO;
import br.com.duosdevelop.ecommerce.dto.NewCustomerDTO;
import br.com.duosdevelop.ecommerce.repositories.AddressRepository;
import br.com.duosdevelop.ecommerce.repositories.CustomerRepository;
import br.com.duosdevelop.ecommerce.services.exceptions.DataIntegrityException;
import br.com.duosdevelop.ecommerce.services.exceptions.ObjectNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

	@Autowired
	private BCryptPasswordEncoder be;

	@Autowired
	private CustomerRepository repository;
	
	@Autowired
	private AddressRepository addressRepository;

	@Transactional
	public Customer insert(Customer customer) {
		customer.setId(null);
		customer = repository.save(customer);
		addressRepository.saveAll(customer.getAddress());
		return customer;
	}
	
	public Customer find(Long id) {
		Optional<Customer> customer = repository.findById(id);
		
		return customer.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: "+ id +
				", Tipo:"+ Category.class.getName()));
	}

	public List<Customer> findAll() {
		return repository.findAll();
	}

	public Customer update(Customer customer) {
		find(customer.getId());
		return repository.save(customer);
	}

	public void delete(Long id) {
		find(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um cliente que tem pedidos");
		}
	}

	public Page<Customer> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}
	
	public Customer fromDTO(CustomerDTO customerDTO) {
		Customer customer = find(customerDTO.getId());
		customer.setName(customerDTO.getName());
		customer.setEmail(customerDTO.getEmail());
		return customer;
	}
	
	public Customer fromDTO(NewCustomerDTO customerDTO) {
		Customer customer = new Customer(null, customerDTO.getName(), customerDTO.getEmail(), 
				customerDTO.getDocument(), TypeCustomer.toEnum(customerDTO.getType()), be.encode(customerDTO.getPassword()));
		City city = new City(customerDTO.getCityId(), null, null);
		Address address = new Address(null, customerDTO.getStreet(), customerDTO.getNumber(), 
				customerDTO.getComplement(), customerDTO.getNeighborhood(), customerDTO.getCep(), customer, city);
		customer.getAddress().add(address);
		customer.getTel().add(customerDTO.getTelefone1());
		
		if(customerDTO.getTelefone2() != null)
			customer.getTel().add(customerDTO.getTelefone2());
		
		if(customerDTO.getTelefone3() != null)
			customer.getTel().add(customerDTO.getTelefone3());
		return customer;
	}
}
