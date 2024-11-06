package com.samvolvo.utils;

import com.samvolvo.Main;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;

public class DiscordUtils {
    private Main module;

    public DiscordUtils(Main module){
        this.module = module;
    }

    public User getUserById(String id){
        try{
            User user = module.getBotEnvironment().getShardManager().getUserById(id);
            return null;
        }catch (ErrorResponseException e){
            module.getLogger().error("No user could be found with id " + id);
            return null;
        }
    }


}
