package com.lambdaschool.ordersapp.services;

import com.lambdaschool.ordersapp.models.Customer;
import com.lambdaschool.ordersapp.repositories.CustomersRepository;
import com.lambdaschool.ordersapp.view.OrderCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "customerServices")
public class CustomerServicesImpl implements CustomerServices
{
    @Autowired
    CustomersRepository custrepos;

    @Override
    public Customer save(Customer customer)
    {
        return custrepos.save(customer);
    }

    @Override
    public List<Customer> findCustomerOrders()
    {
        List<Customer> customerList = new ArrayList<>();
        custrepos.findAll().iterator().forEachRemaining(customerList::add);
        return customerList;
    }

    @Override
    public Customer findByCustCode(long custcode)
    {
        return custrepos.findById(custcode).orElseThrow(() -> new EntityNotFoundException("Customer " + custcode + " Not Found!"));
    }

    @Override
    public List<Customer> findByCustomerName(String subname)
    {
        List<Customer> customerList = custrepos.findByCustnameContainingIgnoringCase(subname);
        return customerList;
    }

    @Override
    public List<OrderCount> countOrderByCustomer()
    {
        List<OrderCount> orderCountList = custrepos.findOrderCount();
        return orderCountList;
    }
}
