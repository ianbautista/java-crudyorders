package com.lambdaschool.ordersapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "payments")
public class Payment
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long paymentid;

    @Column(nullable = false)
    private String type;

    // relationship to Order
    @ManyToMany(mappedBy = "payments")
    @JsonIgnoreProperties(value = "payments")
    private Set<Order> orders = new HashSet<>();

    //default constructor
    public Payment()
    {
    }

    //constructor
    // (paymentid, type)
    public Payment(String type) {
        this.paymentid = paymentid;
        this.type = type;
    }

    public long getPaymentid() {
        return paymentid;
    }

    public void setPaymentid(long paymentid) {
        this.paymentid = paymentid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }


}
