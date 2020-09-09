package com.lambdaschool.ordersapp.controllers;

import com.lambdaschool.ordersapp.models.Order;
import com.lambdaschool.ordersapp.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController
{
    @Autowired
    OrderServices orderServices;

    // http://localhost:2019/orders/order/7
    @GetMapping(value = "/order/{ordnum}", produces = {"application/json"})
    public ResponseEntity<?> findOrderByOrdnum(@PathVariable long ordnum)
    {
        Order order = orderServices.findOrderByOrdnum(ordnum);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    // STRETCH
    // http://localhost:2019/orders/advanceamount
    @GetMapping(value = "/advanceamount", produces = {"application/json"})
    public ResponseEntity<?> ordersAdvanceAmount()
    {
        List<Order> orderList = orderServices.getByAdvanceamount(0);
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

}
