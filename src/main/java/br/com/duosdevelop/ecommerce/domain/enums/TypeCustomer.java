package br.com.duosdevelop.ecommerce.domain.enums;

public enum TypeCustomer {
	
	
	PESSOA_FISICA(1, "Pessoa Física"),
	PESSOA_JURIDICA(2, "Pessoa Jurídica");
	
	private int cod;
	private String descricao;
	
	private TypeCustomer(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public int getCod() {
		return cod;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static TypeCustomer toEnum(Integer cod) {
		if(cod == null)
			return null;
		
		for(TypeCustomer x : TypeCustomer.values()) {
			if(cod.equals(x.getCod()))
				return x;
		}
		
		throw new IllegalArgumentException("Id inválido: "+ cod);
	}
}
