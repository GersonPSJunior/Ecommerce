package br.com.duosdevelop.ecommerce.services.validation;

import br.com.duosdevelop.ecommerce.domain.enums.TypeCustomer;
import br.com.duosdevelop.ecommerce.dto.NewCustomerDTO;
import br.com.duosdevelop.ecommerce.resources.exceptions.FieldMessage;
import br.com.duosdevelop.ecommerce.services.validation.utils.BR;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<CustomerInsert, NewCustomerDTO> {
    @Override
    public void initialize(CustomerInsert constraintAnnotation) {

    }

    @Override
    public boolean isValid(NewCustomerDTO newCustomerDTO, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        if(newCustomerDTO.getType().equals(TypeCustomer.PESSOA_FISICA.getCod()) &&
                !BR.isValidCPF(newCustomerDTO.getDocument()))
            list.add(new FieldMessage("Type", "CPF inválido"));

        if(newCustomerDTO.getType().equals(TypeCustomer.PESSOA_JURIDICA.getCod()) &&
                !BR.isValidCNPJ(newCustomerDTO.getDocument()))
            list.add(new FieldMessage("Type", "CNPJ inválido"));

        for (FieldMessage fieldMessage : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(fieldMessage.getMessage())
                    .addPropertyNode(fieldMessage.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}
