package com.lambdaschool.ordersapp.controllers;

import com.lambdaschool.ordersapp.models.Customer;
import com.lambdaschool.ordersapp.services.CustomerServices;
import com.lambdaschool.ordersapp.view.OrderCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController
{
    @Autowired
    CustomerServices customerServices;

    // http://localhost:2019/customers/orders
    @GetMapping(value = "/orders", produces = {"application/json"})
    public ResponseEntity<?> listCustomerOrders()
    {
        List<Customer> customerList = customerServices.findCustomerOrders();
        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }

    // http://localhost:2019/customers/customer/77
    // http://localhost:2019/customers/customer/7
    @GetMapping(value = "/customer/{custcode}", produces = {"application/json"})
    public ResponseEntity<?> findCustomerByCustcode(@PathVariable long custcode)
    {
        Customer customer = customerServices.findByCustCode(custcode);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    // http://localhost:2019/customers/namelike/mes
    // http://localhost:2019/customers/namelike/cin
    @GetMapping(value = "/namelike/{subname}", produces = {"application/json"})
    public ResponseEntity<?> findCustomerBySubname(@PathVariable String subname)
    {
        List<Customer> rtnList = customerServices.findByCustomerName(subname);
        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }

    // http://localhost:2019/customers/orders/count
    @GetMapping(value = "/orders/count", produces = {"application/json"})
    public ResponseEntity<?> customerOrderCount()
    {
        List<OrderCount> rtnList = customerServices.countOrderByCustomer();
        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }

}
