package br.com.duosdevelop.ecommerce.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SmtpEmailService extends AbstractEmailService{

    // Pega configurações de email
    @Autowired
    private MailSender mailSender;
    private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);

    @Override
    public void sendEmail(SimpleMailMessage msg) {
        LOG.info("Simulação email");
        mailSender.send(msg);
        LOG.info("Email enviado");
    }
}
