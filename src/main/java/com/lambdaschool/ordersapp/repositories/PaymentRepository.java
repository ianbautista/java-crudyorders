package com.lambdaschool.ordersapp.repositories;

import com.lambdaschool.ordersapp.models.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment, Long>
{
}
