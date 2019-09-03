package br.com.duosdevelop.ecommerce.domain;

import java.util.Date;

import javax.persistence.Entity;

import br.com.duosdevelop.ecommerce.domain.enums.StatePayment;

@Entity
public class PaymentSlip extends Payment{
	private static final long serialVersionUID = 1L;

	private Date dateVencimento;
	private Date datePayment;
	
	public PaymentSlip() {}

	public PaymentSlip(Long id, StatePayment statePayment, Pedido order, Date dateVencimento, Date datePayment) {
		super(id, statePayment, order);
		this.dateVencimento = dateVencimento;
		this.datePayment = datePayment;
	}

	public Date getDateVencimento() {
		return dateVencimento;
	}

	public void setDateVencimento(Date dateVencimento) {
		this.dateVencimento = dateVencimento;
	}

	public Date getDatePayment() {
		return datePayment;
	}

	public void setDatePayment(Date datePayment) {
		this.datePayment = datePayment;
	}
	
}
