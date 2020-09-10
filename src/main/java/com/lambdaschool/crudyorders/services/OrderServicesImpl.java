package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Customer;
import com.lambdaschool.crudyorders.models.Order;
import com.lambdaschool.crudyorders.models.Payment;
import com.lambdaschool.crudyorders.repositories.CustomersRepository;
import com.lambdaschool.crudyorders.repositories.OrdersRepository;
import com.lambdaschool.crudyorders.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class OrderServicesImpl implements OrderServices
{
    @Autowired
    OrdersRepository ordersrepos;

    @Autowired
    CustomersRepository custrepos;

    @Autowired
    PaymentRepository paymentrepos;

    @Transactional
    @Override
    public Order save(Order order)
    {
        Order newOrder = new Order();

        if (order.getOrdnum() > 0)
        {
            findOrderByOrdnum(order.getOrdnum());
            newOrder.setOrdnum(order.getOrdnum());
        }


        newOrder.setOrdamount(order.getOrdamount());
        newOrder.setAdvanceamount(order.getAdvanceamount());
        newOrder.setOrderdescription(order.getOrderdescription());

        Customer newCustomer = custrepos.findById(order.getCustcode().getCustcode()).orElseThrow(() -> new EntityNotFoundException("Customer " + order.getCustcode().getCustcode() + " NotFound!"));
        newOrder.setCustcode(newCustomer);

        newOrder.getPayments().clear();
        for (Payment p : order.getPayments())
        {
            Payment newPayment = paymentrepos.findById(p.getPaymentid()).orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " Not Found!"));
            newOrder.getPayments().add(newPayment);
        }

        return ordersrepos.save(newOrder);
    }

    @Override
    public Order findOrderByOrdnum(long ordnum)
    {
        return ordersrepos.findById(ordnum).orElseThrow(() -> new EntityNotFoundException("Order " + ordnum + " Not Found!"));
    }

    @Override
    public List<Order> getByAdvanceamount(double amount)
    {

        return ordersrepos.findByAdvanceamountGreaterThan(0);
    }

    @Override
    public List<Order> findAllOrders()
    {
        List<Order> list = new ArrayList<>();
        ordersrepos.findAll().iterator().forEachRemaining(list::add);
       return list;
    }

    @Transactional
    @Override
    public void delete(long ordernum)
    {
        if (ordersrepos.findById(ordernum).isPresent())
        {
            ordersrepos.deleteById(ordernum);
        }else
        {
            throw new EntityNotFoundException("Order " + ordernum + " Not Found!");
        }
    }
}
