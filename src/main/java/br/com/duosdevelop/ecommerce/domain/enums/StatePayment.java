package br.com.duosdevelop.ecommerce.domain.enums;

public enum StatePayment {
	
	PENDENTE(1, "Pendente"),
	QUITADO(2, "Quitado"),
	CANCELADO(3, "Cancelado");

	private int cod;
	private String descricao;
	
	private StatePayment(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public int getCod() {
		return cod;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static StatePayment toEnum(Integer cod) {
		if(cod == null)
			return null;
		
		for(StatePayment x : StatePayment.values()) {
			if(cod.equals(x.getCod()))
				return x;
		}
		
		throw new IllegalArgumentException("Id inválido: "+ cod);
	}
}
