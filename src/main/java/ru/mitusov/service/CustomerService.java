package ru.mitusov.service;

import ru.mitusov.entity.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> getAllCustomers();

    void saveCustomer(Customer customer);

    Customer findCustomerById(int id);

    void deleteCustomer(int id);
}
