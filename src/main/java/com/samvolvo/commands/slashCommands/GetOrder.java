package com.samvolvo.commands.slashCommands;

import com.samvolvo.Main;
import com.samvolvo.database.models.OrderData;
import com.samvolvo.managers.EmbedManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.vitacraft.api.addons.SlashCommandAddon;
import org.jetbrains.annotations.NotNull;

public class GetOrder implements SlashCommandAddon {
    private final Main module;

    public GetOrder(Main module){
        this.module = module;
    }

    @Override
    public void execute(@NotNull SlashCommandInteractionEvent event) {
        String orderId = event.getOption("orderid").getAsString();

        OrderData data = module.getOrderDataUtil().getOrder(orderId);

        if (data != null){
            event.reply("Order Found!").addEmbeds(EmbedManager.order(data, event.getUser())).queue();
            return;
        }else{
            event.reply("Order does not exists!").setEphemeral(true).queue();
            return;
        }
    }
}
