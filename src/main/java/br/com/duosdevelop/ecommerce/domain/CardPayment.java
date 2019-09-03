package br.com.duosdevelop.ecommerce.domain;

import javax.persistence.Entity;

import br.com.duosdevelop.ecommerce.domain.enums.StatePayment;

@Entity
public class CardPayment extends Payment {
	private static final long serialVersionUID = 1L;

	private Integer numeroParcelas;
	
	public CardPayment() {}

	

	public CardPayment(Long id, StatePayment statePayment, Pedido order, Integer numeroParcelas) {
		super(id, statePayment, order);
		this.numeroParcelas = numeroParcelas;
	}



	public Integer getNumeroParcelas() {
		return numeroParcelas;
	}

	public void setNumeroParcelas(Integer numeroParcelas) {
		this.numeroParcelas = numeroParcelas;
	}
	
	
}
