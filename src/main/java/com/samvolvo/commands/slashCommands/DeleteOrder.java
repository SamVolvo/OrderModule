package com.samvolvo.commands.slashCommands;

import com.samvolvo.Main;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.vitacraft.api.addons.SlashCommandAddon;
import org.jetbrains.annotations.NotNull;

public class DeleteOrder implements SlashCommandAddon {
    private final Main module;

    public DeleteOrder(Main module){
        this.module = module;
    }

    @Override
    public void execute(@NotNull SlashCommandInteractionEvent event) {
        String orderId = event.getOption("orderid").getAsString();

        boolean succes = module.getOrderDataUtil().deleteOrder(orderId);

        if (succes){
            event.reply("Order has been removed!").setEphemeral(true).queue();
            module.getDiscordUtils().sendLog(event.getUser().getName() + " has removed an order with id: " + orderId, null);
        }
    }
}
