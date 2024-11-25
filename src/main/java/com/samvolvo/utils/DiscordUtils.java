package com.samvolvo.utils;

import com.samvolvo.Main;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class DiscordUtils {
    private final Main module;

    public DiscordUtils(Main module){
        this.module = module;
    }

    public User getUserById(String id) {
        try {
            return module.getShardManager().getUserById(id);
        } catch (Exception e) {
            Logger.error("No user could be found with id " + id);
            return null;
        }
    }

    public TextChannel getTextChannelById(String channelId) {
        return module.getShardManager().getTextChannelById(channelId);
    }

    public void sendLog(String message, MessageEmbed embed) {
        String logId = module.getConfigLoader().getConfig().getString("channels.log");
        Logger.debug("Trying to send a log!");

        TextChannel channel = getTextChannelById(logId);
        if (channel == null) {
            Logger.debug("No Channel found to send log!");
            return;
        }

        if (message != null && embed != null) {
            channel.sendMessage(message).addEmbeds(embed).queue();
        } else if (message != null) {
            channel.sendMessage(message).queue();
        } else if (embed != null) {
            channel.sendMessageEmbeds(embed).queue();
        } else {
            Logger.debug("Log has not returned.");
        }
    }

    public boolean isStaff(Member member) {
        String staffId = module.getConfigLoader().getConfig().getString("roles.staff");
        for (Role role : member.getRoles()) {
            if (role.getId().equals(staffId)) {
                return true;
            }
        }
        return false;
    }


}
