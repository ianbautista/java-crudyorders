package com.lambdaschool.crudyorders.controllers;

import com.lambdaschool.crudyorders.models.Customer;
import com.lambdaschool.crudyorders.services.CustomerServices;
import com.lambdaschool.crudyorders.view.OrderCount;
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

    // Day 3
    // POST /customers/customer - Adds a new customer including any new orders
    // http://localhost:2019/customers/customer
    @PostMapping(value = "/customer", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> addNewCustomer(@Valid @RequestBody Customer newCustomer)
    {
        // return new ResponseEntity<>(data, header, status)
        newCustomer.setCustcode(0);
        newCustomer = customerServices.save(newCustomer);

        // Response Headers -> Location Header = URL to the new restaurant
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newCustomerURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{custcode}").buildAndExpand(newCustomer.getCustcode()).toUri();
        responseHeaders.setLocation((newCustomerURI));
        return new ResponseEntity<>("No Body Data", responseHeaders, HttpStatus.CREATED);

    }

    // PUT /customers/customer/{custcode} - completely replaces the customer record including associated orders with the provided data
    // http://localhost:2019/customers/customer/{custcode}
    // PUT http://localhost:2019/customers/customer/19
    @PutMapping(value = "/customer/{custcode}", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> updateFullCustomerById(@PathVariable long custcode, @Valid @RequestBody Customer updateCustomer)
    {
        updateCustomer.setCustcode(custcode);
        updateCustomer = customerServices.save(updateCustomer);

        return new ResponseEntity<>("No Body Data", HttpStatus.OK);
    }

    // PATCH /customers/customer/{custcode} - updates customers with the new data. Only the new data is to be sent from the frontend client.
    // http://localhost:2019/customers/customer/19
    @PatchMapping(value = "/customer/{custcode}", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> updatePartCustomerById(@PathVariable long custcode, @RequestBody Customer updateCustomer)
    {
        updateCustomer = customerServices.update(updateCustomer, custcode);

        return new ResponseEntity<>("No Body Data", HttpStatus.OK);
    }

    // DELETE /customers/customer/{custcode} - Deletes the given customer including any associated orders
    // http://localhost:2019/customers/customer/54
    @DeleteMapping(value = "/customer/{custcode}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable long custcode)
    {
        customerServices.delete(custcode);
        return new ResponseEntity<>("No Body Data", HttpStatus.OK);
    }
}
