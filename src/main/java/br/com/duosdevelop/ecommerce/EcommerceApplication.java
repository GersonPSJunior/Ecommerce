package br.com.duosdevelop.ecommerce;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.duosdevelop.ecommerce.domain.Address;
import br.com.duosdevelop.ecommerce.domain.CardPayment;
import br.com.duosdevelop.ecommerce.domain.Category;
import br.com.duosdevelop.ecommerce.domain.City;
import br.com.duosdevelop.ecommerce.domain.Customer;
import br.com.duosdevelop.ecommerce.domain.ItemPedido;
import br.com.duosdevelop.ecommerce.domain.Payment;
import br.com.duosdevelop.ecommerce.domain.PaymentSlip;
import br.com.duosdevelop.ecommerce.domain.Pedido;
import br.com.duosdevelop.ecommerce.domain.Product;
import br.com.duosdevelop.ecommerce.domain.State;
import br.com.duosdevelop.ecommerce.domain.enums.StatePayment;
import br.com.duosdevelop.ecommerce.domain.enums.TypeCustomer;
import br.com.duosdevelop.ecommerce.repositories.AddressRepository;
import br.com.duosdevelop.ecommerce.repositories.CategoryRepository;
import br.com.duosdevelop.ecommerce.repositories.CityRepository;
import br.com.duosdevelop.ecommerce.repositories.CustomerRepository;
import br.com.duosdevelop.ecommerce.repositories.ItemPedidoRepository;
import br.com.duosdevelop.ecommerce.repositories.PaymentRepository;
import br.com.duosdevelop.ecommerce.repositories.PedidoRepository;
import br.com.duosdevelop.ecommerce.repositories.ProductRepository;
import br.com.duosdevelop.ecommerce.repositories.StateRepository;

@SpringBootApplication
public class EcommerceApplication implements CommandLineRunner{

	@Autowired
	private CategoryRepository repositoryCategory;
	
	@Autowired
	private ProductRepository repositoryProduct;
	
	@Autowired
	private StateRepository repositoryState;
	
	@Autowired
	private CityRepository repositoryCity;
	
	@Autowired
	private CustomerRepository repositoryCustomer;
	
	@Autowired
	private AddressRepository repositoryAddress;
	
	@Autowired
	private PedidoRepository repositoryPedido;
	
	@Autowired
	private PaymentRepository repositoryPayment;
	
	@Autowired
	private ItemPedidoRepository repositoryItemPedido;
	
	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Category cat1 = new Category(null, "Informática");
		Category cat2 = new Category(null, "Escritório");
		Category cat3 = new Category(null, "Cama mesa e banho");
		Category cat4 = new Category(null, "Eletrônicos");
		Category cat5 = new Category(null, "Jardinagem");
		Category cat6 = new Category(null, "Decoração");
		Category cat7 = new Category(null, "Perfumaria");
		
		Product prod1 = new Product(null, "Computador", 2000.00);
		Product prod2 = new Product(null, "Impressora", 800.00);
		Product prod3 = new Product(null, "Mouse", 80.00);
		
		State mg = new State(null, "Minas Gerais");
		State sp = new State(null, "São Paulo");
		
		City ubCity = new City(null, "Uberlândia", mg);
		City spCity = new City(null, "São Paulo", sp);
		City cmCity = new City(null, "Campinas", sp);
		
		Customer customer1 = new Customer(null, "Maria das Dores", "maria@gmail.com", "123.456.789-12", TypeCustomer.PESSOA_FISICA);
		customer1.getTel().addAll(Arrays.asList("24351435", "53126355"));
		
		Address address1 = new Address(null, "Rua 1", "300", "casa", "Jardim", "03297266", customer1, ubCity);
		Address address2 = new Address(null, "Rua 2", "400", "casa 3", "Jardim", "63429628", customer1, spCity);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), customer1, address1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), customer1, address2);
		
		Payment payment1 = new CardPayment(null, StatePayment.QUITADO, ped1, 6);
		ped1.setPayment(payment1);
		
		Payment payment2 = new PaymentSlip(null, StatePayment.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPayment(payment2);
		
		ItemPedido item1 = new ItemPedido(ped1, prod1, 0.00, 1, 2000.00);
		
		ItemPedido item2 = new ItemPedido(ped1, prod3, 0.00, 2, 80.00);
		
		ItemPedido item3 = new ItemPedido(ped2, prod2, 100.00, 1, 800.00);
		
		customer1.getAddress().addAll(Arrays.asList(address1, address2));
		cat1.getProducts().addAll(Arrays.asList(prod1, prod2, prod3));
		cat2.getProducts().addAll(Arrays.asList(prod2));
		prod1.getCategories().addAll(Arrays.asList(cat1));
		prod2.getCategories().addAll(Arrays.asList(cat1, cat2));
		prod3.getCategories().addAll(Arrays.asList(cat1));
		
		mg.getCities().addAll(Arrays.asList(ubCity));
		sp.getCities().addAll(Arrays.asList(spCity, cmCity));
		
		customer1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		ped1.getItens().addAll(Arrays.asList(item1, item2));
		ped2.getItens().addAll(Arrays.asList(item3));
		
		prod1.getItens().addAll(Arrays.asList(item1));
		prod2.getItens().addAll(Arrays.asList(item3));
		prod3.getItens().addAll(Arrays.asList(item2));
		
		repositoryCategory.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		repositoryProduct.saveAll(Arrays.asList(prod1, prod2, prod3));
		repositoryState.saveAll(Arrays.asList(mg, sp));
		repositoryCity.saveAll(Arrays.asList(ubCity, spCity, cmCity));
		repositoryCustomer.saveAll(Arrays.asList(customer1));
		repositoryAddress.saveAll(Arrays.asList(address1, address2));
		
		repositoryPedido.saveAll(Arrays.asList(ped1, ped2));
		repositoryPayment.saveAll(Arrays.asList(payment1, payment2));
		
		repositoryItemPedido.saveAll(Arrays.asList(item1, item2, item3));
	}

}
