package com.samvolvo.commands.slashCommands;

import com.samvolvo.Main;
import com.samvolvo.database.models.OrderData;
import com.samvolvo.managers.EmbedManager;
import com.samvolvo.utils.Logger;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.vitacraft.api.addons.SlashCommandAddon;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Optional;

public class UpdateOrder implements SlashCommandAddon {
    private Main module;

    public UpdateOrder(Main module){
        this.module = module;
    }

    @Override
    public void execute(@NotNull SlashCommandInteractionEvent event) {
        event.deferReply().queue();


        String orderId = Optional.ofNullable(event.getOption("orderid")).map(option -> option.getAsString()).orElse(null);
        User customer = Optional.ofNullable(event.getOption("customer")).map(option -> option.getAsUser()).orElse(null);
        BigDecimal totalPrice = Optional.ofNullable(event.getOption("totalprice")).map(option -> new BigDecimal(option.getAsString())).orElse(null);
        String paymentMethod = Optional.ofNullable(event.getOption("paymentmethod")).map(option -> option.getAsString()).orElse(null);
        String orderStatus = Optional.ofNullable(event.getOption("orderstatus")).map(option -> option.getAsString()).orElse(null);
        User supporter = Optional.ofNullable(event.getOption("supporter")).map(option -> option.getAsUser()).orElse(null);
        String notes = Optional.ofNullable(event.getOption("notes")).map(option -> option.getAsString()).orElse(null);


        try {
            OrderData data = module.getOrderDataUtil().getOrder(orderId);
            Logger.debug("Loaded orderdata!");

            if (customer != null){
                data.setCustomer(customer);
                Logger.debug("Cusomer updated!");
            }

            if (totalPrice != null){
                data.setTotalPrice(totalPrice);
                Logger.debug("totalprice updated!");
            }

            if (paymentMethod != null){
                data.setPaymentMethod(paymentMethod);
                Logger.debug("PaymentMethod updated!");
            }

            if (orderStatus != null){
                data.setOrderStatus(orderStatus);
                Logger.debug("orderStatus updated!");
            }

            if (supporter != null){
                data.setSupporter(supporter);
                Logger.debug("supporter updated!");
            }

            if (notes != null){
                data.setNotes(notes);
                Logger.debug("notes updated!");
            }

            module.getOrderDataUtil().updateOrderData(data);
            Logger.debug("Data updated!");

            module.getDiscordUtils().sendLog(event.getUser().getName() + " has updated an order.", EmbedManager.order(data, event.getUser()));

            event.getHook().sendMessage("Updated data!").queue();
            Logger.debug("Update Command was fully ran!");
        }catch (Exception e){
            Logger.error(e.getMessage());
        }
    }
}
