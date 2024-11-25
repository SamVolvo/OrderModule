package com.samvolvo.managers;

import com.samvolvo.database.models.OrderData;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.time.Instant;

public class EmbedManager {
    private static String link = "https://www.samvolvo.com/";
    private static String imageLink = "https://samvolvo.com/favicon.png";
    private static String footer = "Powered by SamVolvo";

    public static MessageEmbed help(boolean isStaff, User user){
        EmbedBuilder embed = new EmbedBuilder();
        /// Default
        embed.setTitle("Help")
                .setAuthor(user.getName())
                .setDescription("Here you have all the commands you can use.")
                .setTimestamp(Instant.now())
                .setFooter(footer, imageLink)
                .setColor(Color.decode("#019A70"))
        ;

        if (!isStaff){
            embed.addField("/help", "Get all commands for this bot.", true)
                    .addField("Comming soon!", "Comming soon!", true)
            ;
        }

        if (isStaff){
            embed.addField("/help", "Get all commands for this bot.", true)
                    .addField("/createorder", "Create an order.", true)
                    .addField("/getorder", "Get an order from the database.", true)
                    .addField("/updateorder", "Update an order in the database.", true)
                    .addField("/deleteorder", "Remove an order from the database.", true)
                    .addField("/comming soon", "Comming soon!", true)
            ;
        }

        return embed.build();
    }

    public static MessageEmbed order(OrderData data, User user){
        EmbedBuilder embed = new EmbedBuilder();

        String deliveryTime = (data.getDeliveryTime() != null) ? data.getDeliveryTime().toString() : "not set";

        embed.setTitle("CricketHosting order", link)
                .setAuthor(user.getName(), link, user.getAvatarUrl())
                .setDescription("Your request for the orderData.")
                .addField("OrderId:", data.getOrderId(), true)
                .addField("Customer:", data.getCustomer().getAsMention(), true)
                .addField("Total:", "€" + data.getTotalPrice().toString(), true)
                .addField("PaymentMethod:", data.getPaymentMethod(), true)
                .addField("OrderTime:", data.getOrderTime().toString(), true)
                .addField("Status:", data.getOrderStatus(), true)
                .addField("DeliveryTime:", deliveryTime, true)
                .addField("Supporter:", data.getSupporter().getAsMention(),true)
                .addField("notes:", data.getNotes(),true)
                .setColor(Color.BLUE)
                .setFooter(footer, imageLink)
        ;
        return embed.build();
    }
}
