package com.samvolvo;

import com.samvolvo.commands.OrderCommand;
import com.samvolvo.database.Database;
import com.samvolvo.utils.DiscordUtils;
import com.samvolvo.utils.Options;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.vitacraft.api.MBModule;

public class Main extends MBModule {
    private Database database;

    private DiscordUtils discordUtils;
    private Options options;

    @Override
    public void onEnable() {
        getLogger().info("Loading Order Module!");
        sendLogo();
        sendDataLogin();
        database = new Database(this);
        discordUtils = new DiscordUtils(this);
        options = new Options();

        registerCommands();


        getLogger().info("Module Loaded!");
    }

    /**
     * Register all commands
     */
    public void registerCommands(){
        // CreateOrder Command
        getBotEnvironment().getCommandManager().registerCommand(Commands.slash("createorder", "Create an order.")
                        .addOptions(options.orderIdForced(), options.customerForced(), options.totalPriceForced(), options.paymentMethodForced(), options.orderStatusForced(), options.supporterForced(), options.notesForced())
                , new OrderCommand(this));
    }

    private void sendLogo(){
        getLogger().info(" ___  _  _");
        getLogger().info("/ __)( \\/ )");
        getLogger().info("\\__ \\ \\  / ");
        getLogger().info("(___/  \\/ ");
    }

    private void sendDataLogin(){
        getLogger().info("URL: " + getConfigLoader().getConfig().getString("database.URL"));
        getLogger().info("Name: " + getConfigLoader().getConfig().getString("database.Name"));
        getLogger().info("User: " + getConfigLoader().getConfig().getString("database.User"));
        getLogger().info("Password: " + getConfigLoader().getConfig().getString("database.Password"));
    }



    /// Getters
    public Database getDatabase() {
        return database;
    }

    public DiscordUtils getDiscordUtils() {
        return discordUtils;
    }
}