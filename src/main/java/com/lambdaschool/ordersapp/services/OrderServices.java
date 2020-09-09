package com.lambdaschool.ordersapp.services;

import com.lambdaschool.ordersapp.models.Order;

import java.util.List;

public interface OrderServices
{
    Order save(Order order);

    Order findOrderByOrdnum(long ordnum);

    List<Order> getByAdvanceamount(double amount);
}
