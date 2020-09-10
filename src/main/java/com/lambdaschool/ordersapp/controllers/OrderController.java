package com.lambdaschool.ordersapp.controllers;

import com.lambdaschool.ordersapp.models.Customer;
import com.lambdaschool.ordersapp.models.Order;
import com.lambdaschool.ordersapp.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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

    // Day 3
    // POST /orders/order - adds a new order to an existing customer
    // http://localhost:2019/orders/order
    @PostMapping(value = "/order", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> addNewOrder(@Valid @RequestBody Order newOrder)
    {
        newOrder.setOrdnum(0);
        newOrder = orderServices.save(newOrder);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newOrderURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{ordnum}").buildAndExpand(newOrder.getOrdnum()).toUri();
        responseHeaders.setLocation((newOrderURI));
        return new ResponseEntity<>("No Body Data", responseHeaders, HttpStatus.CREATED);
    }

}
