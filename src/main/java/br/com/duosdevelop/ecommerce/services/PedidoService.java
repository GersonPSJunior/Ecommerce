package br.com.duosdevelop.ecommerce.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.duosdevelop.ecommerce.domain.Category;
import br.com.duosdevelop.ecommerce.domain.Pedido;
import br.com.duosdevelop.ecommerce.repositories.CategoryRepository;
import br.com.duosdevelop.ecommerce.repositories.PedidoRepository;
import br.com.duosdevelop.ecommerce.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;
	
	public Pedido find(Long id) {
		Optional<Pedido> pedido = repository.findById(id);
		
		return pedido.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: "+ id +
				", Tipo:"+ Pedido.class.getName()));
	}
}
