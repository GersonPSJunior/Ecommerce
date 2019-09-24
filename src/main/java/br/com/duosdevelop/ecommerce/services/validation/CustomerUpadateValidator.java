package br.com.duosdevelop.ecommerce.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import br.com.duosdevelop.ecommerce.domain.Customer;
import br.com.duosdevelop.ecommerce.dto.CustomerDTO;
import br.com.duosdevelop.ecommerce.repositories.CustomerRepository;
import br.com.duosdevelop.ecommerce.resources.exceptions.FieldMessage;

public class CustomerUpadateValidator implements ConstraintValidator<CustomerUpdate, CustomerDTO> {
    
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private CustomerRepository repository;
	
	@Override
    public void initialize(CustomerUpdate constraintAnnotation) {

    }

    @Override
    public boolean isValid(CustomerDTO objDTO, ConstraintValidatorContext context) {

    	Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Long id  = Long.parseLong(map.get("id"));
    	
    	List<FieldMessage> list = new ArrayList<>();

        Customer customerAux = repository.findByEmail(objDTO.getEmail());
        
        if(customerAux != null && !customerAux.getId().equals(id))
        	list.add(new FieldMessage("Email", "Email já existente"));
        	
        
        for (FieldMessage fieldMessage : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(fieldMessage.getMessage())
                    .addPropertyNode(fieldMessage.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}
