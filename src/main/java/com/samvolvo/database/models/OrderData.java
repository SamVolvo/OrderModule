package com.samvolvo.database.models;

import net.dv8tion.jda.api.entities.User;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class OrderData {
    private String orderId;
    private User customer;
    private Timestamp orderTime;
    private BigDecimal totalPrice;
    private String paymentMethod;
    private String orderStatus;
    private Timestamp deliveryTime;
    private User supporter;
    private String notes;

    public OrderData(String orderId, User customer, Timestamp orderTime, BigDecimal totalPrice, String paymentMethod, String orderStatus, Timestamp deliveryTime, User supporter, String notes){
        this.orderId = orderId;
        this.customer = customer;
        this.orderTime = orderTime;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.orderStatus = orderStatus;
        this.deliveryTime = deliveryTime;
        this.supporter = supporter;
        this.notes = notes;
    }

    public String getOrderId() {
        return orderId;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Timestamp getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Timestamp deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public User getSupporter() {
        return supporter;
    }

    public void setSupporter(User supporter) {
        this.supporter = supporter;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
