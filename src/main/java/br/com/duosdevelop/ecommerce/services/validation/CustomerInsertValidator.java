package br.com.duosdevelop.ecommerce.services.validation;

import br.com.duosdevelop.ecommerce.domain.Customer;
import br.com.duosdevelop.ecommerce.domain.enums.TypeCustomer;
import br.com.duosdevelop.ecommerce.dto.NewCustomerDTO;
import br.com.duosdevelop.ecommerce.repositories.CustomerRepository;
import br.com.duosdevelop.ecommerce.resources.exceptions.FieldMessage;
import br.com.duosdevelop.ecommerce.services.validation.utils.BR;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class CustomerInsertValidator implements ConstraintValidator<CustomerInsert, NewCustomerDTO> {
    
	@Autowired
	private CustomerRepository repository;
	
	@Override
    public void initialize(CustomerInsert constraintAnnotation) {

    }

    @Override
    public boolean isValid(NewCustomerDTO objDTO, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        if(objDTO.getType().equals(TypeCustomer.PESSOA_FISICA.getCod()) &&
                !BR.isValidCPF(objDTO.getDocument()))
            list.add(new FieldMessage("Type", "CPF inválido"));

        if(objDTO.getType().equals(TypeCustomer.PESSOA_JURIDICA.getCod()) &&
                !BR.isValidCNPJ(objDTO.getDocument()))
            list.add(new FieldMessage("Type", "CNPJ inválido"));

        Customer customerAux = repository.findByEmail(objDTO.getEmail());
        
        if(customerAux != null)
        	list.add(new FieldMessage("Email", "Email já existente"));
        	
        
        for (FieldMessage fieldMessage : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(fieldMessage.getMessage())
                    .addPropertyNode(fieldMessage.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}
