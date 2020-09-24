package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Payment;
import com.lambdaschool.crudyorders.repositories.PaymentRepository;
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
