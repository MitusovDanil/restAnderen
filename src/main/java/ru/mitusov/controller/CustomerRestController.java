package ru.mitusov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mitusov.entity.Customer;
import ru.mitusov.exception.CustomerNotFoundException;
import ru.mitusov.service.CustomerService;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerRestController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {

     return customerService.getAllCustomers();

    }

    @GetMapping("/customers/{customerId}")
    public Customer getCustomerById(@PathVariable int customerId) {
        Customer customer = customerService.findCustomerById(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException("The Customer with id = " +
                    customerId + " not found");
        }
        return customerService.findCustomerById(customerId);
    }

   @PostMapping("/customers")
   public Customer saveCustomer(@RequestBody Customer customer) {
        customer.setId(0);
       customerService.saveCustomer(customer);
       return customer;
   }

    @DeleteMapping("/customers/{customerId}")
    public void deleteCustomerById(@PathVariable int customerId) {
        customerService.deleteCustomer(customerId);
    }


}
