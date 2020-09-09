package com.lambdaschool.ordersapp.services;

import com.lambdaschool.ordersapp.models.Order;
import com.lambdaschool.ordersapp.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Transactional
@Service
public class OrderServicesImpl implements OrderServices
{
    @Autowired
    OrdersRepository ordersrepos;

    @Override
    public Order save(Order order)
    {
        return ordersrepos.save(order);
    }

    @Override
    public Order findOrderByOrdnum(long ordnum)
    {
        return ordersrepos.findById(ordnum).orElseThrow(() -> new EntityNotFoundException("Order " + ordnum + " Not Found!"));
    }

    @Override
    public List<Order> getByAdvanceamount(double amount)
    {

        return ordersrepos.findByAdvanceamountGreaterThan(0);
    }
}
