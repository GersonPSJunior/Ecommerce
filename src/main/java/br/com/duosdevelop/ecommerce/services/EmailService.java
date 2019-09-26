package br.com.duosdevelop.ecommerce.services;

import br.com.duosdevelop.ecommerce.domain.Pedido;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendOrderConfirmationEmail(Pedido obj);
    void sendEmail(SimpleMailMessage msg);
}
