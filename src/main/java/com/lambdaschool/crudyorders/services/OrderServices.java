package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Order;

import java.util.List;

public interface OrderServices
{
    Order save(Order order);

    Order findOrderByOrdnum(long ordnum);

    List<Order> getByAdvanceamount(double amount);

    List<Order> findAllOrders();

    void delete(long ordernum);

}
