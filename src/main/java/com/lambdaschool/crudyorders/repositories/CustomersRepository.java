package com.lambdaschool.crudyorders.repositories;

import com.lambdaschool.crudyorders.models.Customer;
import com.lambdaschool.crudyorders.view.OrderCount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomersRepository extends CrudRepository<Customer, Long>
{
    Customer findByCustcode(long custcode);

    List<Customer> findByCustnameContainingIgnoringCase(String subname);

    @Query(value = "SELECT c.custname, count(o.ordnum) ordercount " +
            "FROM  customers c LEFT JOIN orders o " +
            "ON c.custcode = o.custcode " +
            "GROUP BY c.custname, o.ordnum", nativeQuery = true)
    List<OrderCount> findOrderCount();
}
