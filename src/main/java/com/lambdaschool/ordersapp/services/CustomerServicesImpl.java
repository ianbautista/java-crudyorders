package com.lambdaschool.ordersapp.services;

import com.lambdaschool.ordersapp.models.Agent;
import com.lambdaschool.ordersapp.models.Customer;
import com.lambdaschool.ordersapp.models.Order;
import com.lambdaschool.ordersapp.models.Payment;
import com.lambdaschool.ordersapp.repositories.AgentsRepository;
import com.lambdaschool.ordersapp.repositories.CustomersRepository;
import com.lambdaschool.ordersapp.repositories.OrdersRepository;
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

    //wiring orders for save method
    @Autowired
    OrdersRepository ordersrepos;

    @Autowired
    AgentsRepository agentrepos;

    // POST - save method
    @Transactional
    @Override
    public Customer save(Customer customer)
    {
        Customer newCustomer = new Customer();

        if (customer.getCustcode() != 0) // checks if id is valid
        {
            findByCustCode(customer.getCustcode());
            newCustomer.setCustcode(customer.getCustcode());
        }

        //single value fields
//       ✔ this.custname = custname;
//       ✔ this.custcity = custcity;
//       ✔ this.workingarea = workingarea;
//       ✔ this.custcountry = custcountry;
//       ✔ this.grade = grade;
//       ✔ this.openingamt = openingamt;
//       ✔ this.receiveamt = receiveamt;
//       ✔ this.paymentamt = paymentamt;
//       ✔ this.outstandingamt = outstandingamt;
//       ✔ this.phone = phone;
//       ✔ this.agentcode = agentcode;
        newCustomer.setCustname(customer.getCustname());
        newCustomer.setCustcity(customer.getCustcity());
        newCustomer.setWorkingarea(customer.getWorkingarea());
        newCustomer.setCustcountry(customer.getCustcountry());
        newCustomer.setGrade(customer.getGrade());
        newCustomer.setOpeningamt(customer.getOpeningamt());
        newCustomer.setReceiveamt(customer.getReceiveamt());
        newCustomer.setPaymentamt(customer.getPaymentamt());
        newCustomer.setOutstandingamt(customer.getOutstandingamt());
        newCustomer.setPhone(customer.getPhone());

        Agent newAgent = agentrepos.findById(customer.getAgentcode().getAgentcode()).orElseThrow(() -> new EntityNotFoundException("Agent " + customer.getAgentcode() + " NotFound!"));
        newCustomer.setAgentcode(newAgent);

        // collections
        // private List<Order> orders = new ArrayList<>();
        newCustomer.getOrders().clear();
        for (Order o : customer.getOrders())
        {
//            {
//                "ordamount": 7777,
//                    "advanceamount": 777,
//                    "orderdescription": "SOD",
//                    "payments" : [
//                {
//                    "paymentid": 4
//                }
//            }
            // (double ordamount, double advanceamount, String orderdescription)
            Order newOrder = new Order(o.getOrdamount(), o.getAdvanceamount(), newCustomer, o.getOrderdescription());
            newCustomer.getOrders().add(newOrder);
        }

        //primary
        return custrepos.save(newCustomer);
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
