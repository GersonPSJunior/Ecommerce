package br.com.duosdevelop.ecommerce.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.duosdevelop.ecommerce.domain.Customer;
import br.com.duosdevelop.ecommerce.dto.CustomerDTO;
import br.com.duosdevelop.ecommerce.dto.NewCustomerDTO;
import br.com.duosdevelop.ecommerce.services.CustomerService;

@RestController
@RequestMapping(value = "/customers")
public class CustomerResource {

	@Autowired
	private CustomerService service;
	
	@Transactional
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody NewCustomerDTO newCustomerDTO){
		Customer customer = service.fromDTO(newCustomerDTO);
		customer = service.insert(customer);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(customer.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CustomerDTO>> findAll(){
		List<Customer> customers = service.findAll();
		List<CustomerDTO> list = customers.stream().map(x -> new CustomerDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<Customer> find(@PathVariable Long id){
		Customer customer = service.find(id);
		return ResponseEntity.ok().body(customer);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody CustomerDTO customerDTO, @PathVariable Long id){
		customerDTO.setId(id);
		Customer customer = service.fromDTO(customerDTO);
		customer.setId(id);
		customer = service.update(customer);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<CustomerDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		
		Page<Customer> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<CustomerDTO> listDto = list.map(obj -> new CustomerDTO(obj));
		return ResponseEntity.ok().body(listDto);
	}
}
