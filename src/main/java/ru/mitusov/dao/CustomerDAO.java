package ru.mitusov.dao;



import ru.mitusov.entity.Customer;

import java.util.List;

public interface CustomerDAO {

    List<Customer> getAllCustomers();

    void saveCustomer(Customer customer);

    Customer findCustomerById(int id);

    void deleteCustomer(int id);
}
