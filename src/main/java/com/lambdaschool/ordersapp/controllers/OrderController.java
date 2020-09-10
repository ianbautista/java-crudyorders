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

    // http://localhost:2019/orders/all
    @GetMapping(value = "/all", produces = {"application/json"})
    public ResponseEntity<?> getAllOrdersList()
    {
        List<Order> orderList = orderServices.findAllOrders();
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

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

    // PUT /orders/order/{ordernum} - completely replaces the given order record
    // http://localhost:2019/orders/order/63
    @PutMapping(value = "/order/{ordernum}", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> updateFullOrder(@PathVariable long ordernum, @Valid @RequestBody Order updateOrder)
    {
        updateOrder.setOrdnum(ordernum);
        updateOrder = orderServices.save(updateOrder);

        return new ResponseEntity<>("No Body Data", HttpStatus.OK);
    }

    // DELETE /orders/order/{ordername} - deletes the given order
    // http://localhost:2019/orders/order/58
    @DeleteMapping(value = "/order/{ordernum}")
    public ResponseEntity<?> deleteOrderById(@PathVariable long ordernum)
    {
        orderServices.delete(ordernum);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
