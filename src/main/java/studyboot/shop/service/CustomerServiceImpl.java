package studyboot.shop.service;

import studyboot.shop.model.entity.Cart;
import studyboot.shop.model.entity.Customer;
import studyboot.shop.repository.CustomerRepository;
import studyboot.shop.service.interfaces.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public Customer save(Customer customer) {
        customer.setId(null);
        customer.setactive(true);

        Cart cart = new Cart();
        cart.setCustomer(customer);
        customer.setCart(cart);


        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllactiveCustomers() {
        return List.of();
    }

}