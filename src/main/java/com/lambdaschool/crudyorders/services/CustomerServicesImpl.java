package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Agent;
import com.lambdaschool.crudyorders.models.Customer;
import com.lambdaschool.crudyorders.models.Order;
import com.lambdaschool.crudyorders.repositories.AgentsRepository;
import com.lambdaschool.crudyorders.repositories.CustomersRepository;
import com.lambdaschool.crudyorders.repositories.OrdersRepository;
import com.lambdaschool.crudyorders.view.OrderCount;
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

        // foreign key field
        Agent newAgent = agentrepos.findById(customer.getAgentcode().getAgentcode()).orElseThrow(() -> new EntityNotFoundException("Agent " + customer.getAgentcode().getAgentcode() + " NotFound!"));
        newCustomer.setAgentcode(newAgent);

        // collections field
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

    @Transactional
    @Override
    public Customer update(Customer customer, long custcode)
    {
        Customer updateCustomer = findByCustCode(custcode);

        //single value fields
        if (customer.getCustname() != null) // checker
        {
            updateCustomer.setCustname(customer.getCustname()); // setter when check is valid
        }

        if (customer.getCustcity() != null)
        {
            updateCustomer.setCustcity(customer.getCustcity());
        }

        if (customer.getWorkingarea() != null)
        {
            updateCustomer.setWorkingarea(customer.getWorkingarea());
        }

        if (customer.getCustcountry() != null)
        {
            updateCustomer.setCustcountry(customer.getCustcountry());
        }

        if (customer.getGrade() != null)
        {
            updateCustomer.setGrade(customer.getGrade());
        }

        if (customer.hasOpeningAmount)
        {
            updateCustomer.setOpeningamt(customer.getOpeningamt());
        }

        if (customer.hasReceiveAmount)
        {
            updateCustomer.setReceiveamt(customer.getReceiveamt());
        }

        if (customer.hasPaymentAmount)
        {
            updateCustomer.setPaymentamt(customer.getPaymentamt());
        }

        if (customer.hasOutstandingAmount)
        {
            updateCustomer.setOutstandingamt(customer.getOutstandingamt());
        }

        if (customer.getPhone() != null)
        {
            updateCustomer.setPhone(customer.getPhone());
        }

        // foreign key field
        if (customer.getAgentcode() != null)
        {
            Agent newAgent = agentrepos.findById(customer.getAgentcode().getAgentcode()).orElseThrow(() -> new EntityNotFoundException("Agent " + customer.getAgentcode() + " NotFound!"));
            updateCustomer.setAgentcode(newAgent);
        }

        // collections field
        // private List<Order> orders = new ArrayList<>();
        if (customer.getOrders().size() > 0)
        {
            updateCustomer.getOrders().clear();
            for (Order o : customer.getOrders())
            {
                // (double ordamount, double advanceamount, String orderdescription)
                Order newOrder = new Order(o.getOrdamount(), o.getAdvanceamount(), updateCustomer, o.getOrderdescription());
                updateCustomer.getOrders().add(newOrder);
            }
        }

        //primary
        return custrepos.save(updateCustomer);
    }

    @Transactional
    @Override
    public void delete(long custcode)
    {
        if (custrepos.findById(custcode).isPresent())
        {
            custrepos.deleteById(custcode);
        }else
        {
            throw new EntityNotFoundException("Customer " + custcode + " Not Found!");
        }
    }
}
