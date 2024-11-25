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
import java.sql.Timestamp;

public class CreateOrder implements SlashCommandAddon {
    private Main module;

    public CreateOrder(Main module){
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

        boolean exist = module.getOrderDataUtil().checkIfExistsWithId(orderId);
        Logger.debug("Checked if orderId is already in use!");

        if (exist){
            event.reply("There is already a order with this id.").setEphemeral(true).queue();
            Logger.warning(event.getUser().getName() + " tried to make an order with an existing orderId.");
            return;
        }else{
            OrderData data = module.getOrderDataUtil().createOrder(orderId, customer, orderTime, totalPrice, paymentMethod, orderStatus, supporter, notes);
            if (data == null){
                event.reply("There was an error. Please contact the Bot dev for more info.").setEphemeral(true).queue();
            }
            event.reply("Created Order!").addEmbeds(EmbedManager.order(data, event.getUser())).setEphemeral(true).queue();
            module.getDiscordUtils().sendLog(event.getUser().getAsMention() + " created a new order!", EmbedManager.order(data, event.getUser()));
            return;
        }
    }
}
