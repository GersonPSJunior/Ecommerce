package br.com.duosdevelop.ecommerce.services.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ClienteInsertValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomerInsert {

    String message() default "erro de validação";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
