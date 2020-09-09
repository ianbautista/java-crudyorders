package com.lambdaschool.ordersapp.services;

import com.lambdaschool.ordersapp.models.Payment;
import com.lambdaschool.ordersapp.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServicesImpl implements PaymentServices
{
    @Autowired
    PaymentRepository paymentrepos;

    @Override
    public Payment save(Payment payment)
    {
        return paymentrepos.save(payment);
    }
}
