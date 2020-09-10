package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Customer;
import com.lambdaschool.crudyorders.view.OrderCount;

import java.util.List;

public interface CustomerServices
{
    Customer save(Customer customer);

    List<Customer> findCustomerOrders();

    Customer findByCustCode(long custcode);

    List<Customer> findByCustomerName(String subname);

    List<OrderCount> countOrderByCustomer();

    Customer update(Customer customer, long custcode);

    void delete(long custcode);

}
