package com.samvolvo;

import com.samvolvo.commands.slashCommands.CreateOrder;
import com.samvolvo.commands.slashCommands.DeleteOrder;
import com.samvolvo.commands.slashCommands.GetOrder;
import com.samvolvo.commands.slashCommands.UpdateOrder;
import com.samvolvo.database.Database;
import com.samvolvo.database.utils.OrderDataUtil;
import com.samvolvo.utils.DiscordUtils;
import com.samvolvo.utils.Options;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.vitacraft.api.MBModule;

public class Main extends MBModule {
    private Database database;
    private ShardManager shardManager;
    private static boolean isDebugMode = true;
    private DiscordUtils discordUtils;
    private Options options;
    private OrderDataUtil orderDataUtil;

    @Override
    public void onEnable() {
        getLogger().info("Loading Order Module!");
        sendLogo();
        shardManager = getBotEnvironment().getShardManager();
        sendDataLogin();
        database = new Database(this);
        discordUtils = new DiscordUtils(this);
        options = new Options();
        orderDataUtil = new OrderDataUtil(this, database);

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
                , new CreateOrder(this));


        getBotEnvironment().getCommandManager().registerCommand(Commands.slash("updateorder", "Update an order.")
                .addOptions(options.orderIdForced(), options.customer(), options.totalPrice(), options.paymentMethod(), options.orderStatus(), options.supporter(), options.notes()),
                new UpdateOrder(this));


        getBotEnvironment().getCommandManager().registerCommand(Commands.slash("getorder", "Get an order.").addOptions(options.orderIdForced()),
                new GetOrder(this));

        getBotEnvironment().getCommandManager().registerCommand(Commands.slash("deleteorder", "Remove an order.")
                .addOptions(options.orderIdForced()), new DeleteOrder(this));
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

    public ShardManager getShardManager() {
        return shardManager;
    }

    public static boolean isDebugMode() {
        return isDebugMode;
    }

    public OrderDataUtil getOrderDataUtil() {
        return orderDataUtil;
    }
}