package studyboot.shop.service.interfaces;

import studyboot.shop.model.entity.Customer;

import java.util.List;

public interface CustomerService {

    Customer save(Customer customer);
    List<Customer> getAllactiveCustomers();
}

