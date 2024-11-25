package com.samvolvo.database.utils;

import com.samvolvo.Main;
import com.samvolvo.utils.Logger;
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

    public OrderData createOrder(String orderId, User customer, Timestamp orderTime, BigDecimal totalPrice, String paymentMethod, String orderStatus, User supporter, String notes){
        OrderData data = new OrderData(orderId, customer, orderTime, totalPrice, paymentMethod, orderStatus, null, supporter, notes);
        try{
            boolean exists = database.checkIfExists(orderId);
            if (exists){
                return null;
            }else{
                database.createOrderData(data);
                return data;
            }
        }catch (Exception e){
            Logger.error("Could not create an order. OrderId: " + orderId);
            return null;
        }
    }

    public OrderData getOrder(String orderId){
        return database.findOrderDataByOrderId(orderId);
    }

    public boolean checkIfExistsWithId(String orderId){
        return database.checkIfExists(orderId);
    }

    public OrderData updateOrderData(OrderData data){
        Timestamp deliveryTime = null;


        if (data.getOrderStatus().equalsIgnoreCase("delivered") && data.getDeliveryTime() == null){
            deliveryTime = new Timestamp(System.currentTimeMillis());
            data.setDeliveryTime(deliveryTime);
        }

        if (deliveryTime == null){
            data.setDeliveryTime(deliveryTime);
        }

        database.updateOrderData(data);

        return data;
    }

    public boolean deleteOrder(String orderId){
        boolean exists = database.checkIfExists(orderId);

        if (!exists){
            Logger.debug("There was no order to be deleted!");
            return false;
        }

        database.deleteOrderData(orderId);
        return true;
    }
}
