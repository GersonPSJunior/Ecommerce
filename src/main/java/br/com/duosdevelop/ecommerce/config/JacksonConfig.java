package br.com.duosdevelop.ecommerce.config;

import br.com.duosdevelop.ecommerce.domain.CardPayment;
import br.com.duosdevelop.ecommerce.domain.PaymentSlip;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {

    // configuração de herança no jackson
    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder(){
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
            public void configure(ObjectMapper objectMapper) {
                objectMapper.registerSubtypes(PaymentSlip.class);
                objectMapper.registerSubtypes(CardPayment.class);
                super.configure(objectMapper);
            }
        };
        return builder;
    }
}
