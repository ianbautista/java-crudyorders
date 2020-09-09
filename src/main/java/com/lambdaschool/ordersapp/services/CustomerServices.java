package com.lambdaschool.ordersapp.services;

import com.lambdaschool.ordersapp.models.Customer;
import com.lambdaschool.ordersapp.view.OrderCount;

import java.util.List;

public interface CustomerServices
{
    Customer save(Customer customer);

    List<Customer> findCustomerOrders();

    Customer findByCustCode(long custcode);

    List<Customer> findByCustomerName(String subname);

    List<OrderCount> countOrderByCustomer();

}
