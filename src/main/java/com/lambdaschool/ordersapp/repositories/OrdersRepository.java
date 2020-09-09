package com.lambdaschool.ordersapp.repositories;

import com.lambdaschool.ordersapp.models.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrdersRepository extends CrudRepository<Order, Long>
{
    List<Order> findByAdvanceamountGreaterThan(double amount);
}
