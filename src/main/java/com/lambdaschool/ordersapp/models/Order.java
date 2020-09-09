package com.lambdaschool.ordersapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name ="orders")
public class Order
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long ordnum;

    @Column(nullable = false)
    private double ordamount;
    private double advanceamount;

    // foreign key relationship to Customer
    @ManyToOne
    @JoinColumn(name = "custcode", nullable = false)
    @JsonIgnoreProperties(value = "orders")
    private Customer custcode;

    private String orderdescription;

    // relationship to Payment
    @ManyToMany
    @JoinTable(name = "orderspayments", joinColumns = @JoinColumn(name = "ordnum"), inverseJoinColumns = @JoinColumn(name = "paymentid"))
    @JsonIgnoreProperties(value = "orders")
    private Set<Payment> payments = new HashSet<>();

    // default constructor
    public Order()
    {
    }

    //constructor
    //(ordnum, ordamount, advanceamount, custcode, orderdescription)
    public Order(double ordamount, double advanceamount, Customer custcode, String orderdescription)
    {
        this.ordnum = ordnum;
        this.ordamount = ordamount;
        this.advanceamount = advanceamount;
        this.custcode = custcode;
        this.orderdescription = orderdescription;
    }

    //getters and setters
    public long getOrdnum()
    {
        return ordnum;
    }

    public void setOrdnum(long ordnum)
    {
        this.ordnum = ordnum;
    }

    public double getOrdamount()
    {
        return ordamount;
    }

    public void setOrdamount(double ordamount)
    {
        this.ordamount = ordamount;
    }

    public double getAdvanceamount()
    {
        return advanceamount;
    }

    public void setAdvanceamount(double advanceamount)
    {
        this.advanceamount = advanceamount;
    }

    public String getOrderdescription()
    {
        return orderdescription;
    }

    public void setOrderdescription(String orderdescription)
    {
        this.orderdescription = orderdescription;
    }

    public Customer getCustcode() {
        return custcode;
    }

    public void setCustcode(Customer custcode) {
        this.custcode = custcode;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    // addPayments from SeedData
    public void addPayments(Payment pay) {
        this.getPayments().add(pay);
    }


}
