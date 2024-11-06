package com.samvolvo.database.utils;

import com.samvolvo.Main;
import com.samvolvo.database.Database;
import com.samvolvo.database.models.OrderData;
import net.dv8tion.jda.api.entities.User;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class OrderDataUtil {
    private Main module;
    private Database database;

    public OrderDataUtil(Main module, Database database){
        this.module = module;
        this.database = database;
    }

    public boolean createOrder(String orderId, User customer, Timestamp orderTime, BigDecimal totalPrice, String paymentMethod, String orderStatus, User supporter, String notes){
        OrderData data = new OrderData(orderId, customer, orderTime, totalPrice, paymentMethod, orderStatus, null, supporter, notes);
        try{
            OrderData exists = database.checkIfExists(data);
            if (exists != null){
                return false;
            }else{
                database.createOrderData(data);
                return true;
            }
        }catch (Exception e){
            module.getLogger().error("Could not create an order. OrderId: " + orderId);
            return false;
        }
    }

    public OrderData getOrder(String orderId){
        return database.findOrderDataByOrderId(orderId);
    }
}
