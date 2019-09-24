package br.com.duosdevelop.ecommerce.dto;

import java.io.Serializable;

import br.com.duosdevelop.ecommerce.domain.Product;

public class ProductDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private Double value;
	
	public ProductDTO() {}

	public ProductDTO(Product obj) {
		id = obj.getId();
		name = obj.getName();
		value = obj.getValue();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
	
}
