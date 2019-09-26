package br.com.duosdevelop.ecommerce.services;

import br.com.duosdevelop.ecommerce.domain.ItemPedido;
import br.com.duosdevelop.ecommerce.domain.PaymentSlip;
import br.com.duosdevelop.ecommerce.domain.Pedido;
import br.com.duosdevelop.ecommerce.domain.enums.StatePayment;
import br.com.duosdevelop.ecommerce.repositories.ItemPedidoRepository;
import br.com.duosdevelop.ecommerce.repositories.PaymentRepository;
import br.com.duosdevelop.ecommerce.repositories.PedidoRepository;
import br.com.duosdevelop.ecommerce.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;

	@Autowired
	private PaymentSlipService paymentSlipService;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private ProductService productService;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private EmailService emailService;
	
	public Pedido find(Long id) {
		Optional<Pedido> pedido = repository.findById(id);
		
		return pedido.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: "+ id +
				", Tipo:"+ Pedido.class.getName()));
	}

	@Transactional
	public Pedido insert(Pedido pedido) {
		pedido.setId(null);
		pedido.setInstante(new Date());
		pedido.setCustomer(customerService.find(pedido.getCustomer().getId()));
		pedido.getPayment().setStatePayment(StatePayment.PENDENTE);
		pedido.getPayment().setPedido(pedido);
		if (pedido.getPayment() instanceof PaymentSlip) {
			PaymentSlip paymentSlip = (PaymentSlip) pedido.getPayment();
			paymentSlipService.setPaymentSlip(paymentSlip, pedido.getInstante());
		}

		pedido = repository.save(pedido);
		paymentRepository.save(pedido.getPayment());
		for (ItemPedido item : pedido.getItens()) {
			item.setDesconto(0.0);
			item.setProduct(productService.find(item.getProduct().getId()));
			item.setPreco(item.getProduct().getValue());
			item.setPedido(pedido);
		}
		itemPedidoRepository.saveAll(pedido.getItens());
		emailService.sendOrderConfirmationEmail(pedido);
		return pedido;
	}
}
