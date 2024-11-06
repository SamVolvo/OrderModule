package com.samvolvo.commands;

import com.samvolvo.Main;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.vitacraft.api.addons.SlashCommandAddon;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class OrderCommand implements SlashCommandAddon {
    private final Main module;

    public OrderCommand(Main module){
        this.module = module;
    }

    @Override
    public void execute(@NotNull SlashCommandInteractionEvent event) {
        String orderId = event.getOption("orderid").getAsString();
        User customer = event.getOption("customer").getAsUser();
        Timestamp orderTime = new Timestamp(System.currentTimeMillis());
        BigDecimal totalPrice = new BigDecimal(event.getOption("totalprice").getAsString());
        String paymentMethod = event.getOption("paymentmethod").getAsString();
        String orderStatus = event.getOption("orderstatus").getAsString();
        Timestamp deliveryDate = null;
        User supporter = event.getOption("supporter").getAsUser();
        String notes = event.getOption("notes").getAsString();



    }
}
