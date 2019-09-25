package br.com.duosdevelop.ecommerce.services;

import br.com.duosdevelop.ecommerce.domain.PaymentSlip;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class PaymentSlipService {

    public void setPaymentSlip(PaymentSlip paymentSlip, Date instante) {
        Calendar date = Calendar.getInstance();
        date.setTime(instante);
        date.add(Calendar.DAY_OF_MONTH, 7);
        paymentSlip.setDateVencimento(date.getTime());
    }
}
