package br.com.duosdevelop.ecommerce.services;

import br.com.duosdevelop.ecommerce.domain.Customer;
import br.com.duosdevelop.ecommerce.repositories.CustomerRepository;
import br.com.duosdevelop.ecommerce.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private CustomerRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = repository.findByEmail(email);
        if (customer == null){
            throw new UsernameNotFoundException(email);
        }
        return new UserSS(customer.getId(), customer.getEmail(), customer.getPassword(), customer.getPerfis());
    }
}
