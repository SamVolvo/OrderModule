package com.samvolvo.database;

import com.samvolvo.Main;
import com.samvolvo.database.models.OrderData;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.dv8tion.jda.api.entities.User;
import com.samvolvo.utils.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Locale;

public class Database {
    private Main module;
    private HikariDataSource dataSource;

    public Database(Main module){
        try{
            this.module = module;
            Class.forName("com.mysql.cj.jdbc.Driver");
            HikariConfig dbConfig = new HikariConfig();
            dbConfig.setJdbcUrl("jdbc:mysql://"+ module.getConfigLoader().getConfig().getString("database.URL") +"/" + module.getConfigLoader().getConfig().getString("database.Name"));
            dbConfig.setUsername(module.getConfigLoader().getConfig().getString("database.User"));
            dbConfig.setPassword(module.getConfigLoader().getConfig().getString("database.Password"));
            dbConfig.addDataSourceProperty("cachePrepStmts", "true");
            dbConfig.addDataSourceProperty("prepStmtCacheSize", "250");
            dbConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            dbConfig.setMaximumPoolSize(100);

            dataSource = new HikariDataSource(dbConfig);


            // Test the connection and log
            try(Connection connection = dataSource.getConnection()){
                if (connection != null && !connection.isClosed()){
                    module.getLogger().info("Successfully connected to the database");
                }
                createTables();
            }catch (SQLException e){
                System.out.println(e);
                module.getLogger().error("Failed to connect to the database.");
            }

        }catch (Exception e){
            System.out.println(e);
            module.getLogger().error("Could not load the database!");
        }
    }

    public Connection getConnection(){
        try{
            return dataSource.getConnection();
        }catch (SQLException e){
            module.getLogger().error("An error occurred while trying to get the database.");
            return null;
        }
    }

    public void close(){
        if (dataSource != null && !dataSource.isClosed()){
            dataSource.close();
        }
    }


    // OrderTable
    private void createTables(){
        Connection connection = getConnection();
        try{
            Statement orderDataStatement = connection.createStatement();
            orderDataStatement.execute(
                    "CREATE TABLE IF NOT EXISTS OrderData (\n" +
                            "    OrderID VARCHAR(35) PRIMARY KEY,\n" +
                            "    CustomerID VARCHAR(35) NOT NULL,\n" +
                            "    OrderTime DATETIME NOT NULL,\n" +
                            "    TotalAmount DECIMAL(10, 2) NOT NULL,\n" +
                            "    PaymentMethod VARCHAR(50) NOT NULL,\n" +
                            "    OrderStatus VARCHAR(20) NOT NULL,\n" +
                            "    DeliveryTime DATETIME,\n" +
                            "    Notes TEXT,\n" +
                            "    SupporterID VARCHAR(35) NOT NULL\n" +
                            ");\n"
            );
            Logger.succes("Succesfully created the database.");
        }catch (SQLException e){
            Logger.error("An error occurred while trying to create the tables.");
            Logger.error(e.getMessage());
        }
    }

    // OrderTable
    public OrderData findOrderDataByOrderId(String orderId){
        PreparedStatement statement = null;
        try{
            statement = getConnection().prepareStatement("SELECT * FROM OrderData WHERE orderId = ?;");
            statement.setString(1, orderId);

            ResultSet results = statement.executeQuery();

            if (results.next()){
                User customer = module.getDiscordUtils().getUserById(results.getString("CustomerID"));
                Timestamp orderDate = results.getTimestamp("OrderTime");
                BigDecimal totalAmount = results.getBigDecimal("TotalAmount");
                String paymentMethod = results.getString("PaymentMethod");
                String orderStatus = results.getString("OrderStatus");
                Timestamp deliveryDate = results.getTimestamp("DeliveryTime");
                String notes = results.getString("Notes");
                User supporter = module.getDiscordUtils().getUserById(results.getString("SupporterID"));

                OrderData data = new OrderData(orderId, customer, orderDate, totalAmount, paymentMethod, orderStatus, deliveryDate, supporter, notes);
                statement.close();
                return data;
            } else {
                // Handle case where no result is found
                statement.close();
                return null;
            }
        }catch (SQLException e){
            Logger.error("An error has occurred while trying to find the OrderData.");
            Logger.error(e.getMessage());
            return null;
        }
    }

    public void createOrderData(OrderData data){
        try{
            PreparedStatement statement = getConnection().prepareStatement(
                    "INSERT INTO OrderData (OrderID, CustomerID, OrderTime, TotalAmount, PaymentMethod, OrderStatus, DeliveryTime, SupporterID, Notes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);"
            );

            statement.setString(1, data.getOrderId());
            statement.setString(2, data.getCustomer().getId());
            statement.setTimestamp(3, data.getOrderTime());
            statement.setBigDecimal(4, data.getTotalPrice());
            statement.setString(5, data.getPaymentMethod());
            statement.setString(6, data.getOrderStatus());
            statement.setTimestamp(7, data.getDeliveryTime());
            statement.setString(8, data.getSupporter().getId());
            statement.setString(9, data.getNotes());
            statement.executeUpdate();
            statement.close();
            Logger.debug("Created an order on id: " + data.getOrderId());
        }catch (SQLException e){
            Logger.error("An error occurred while creating the data in the table.");
            Logger.error(e.getMessage());
        }
    }

    public boolean checkIfExists(String orderId){
        if (findOrderDataByOrderId(orderId) != null){
            Logger.warning("An order has been found with id: " + orderId);
            return true;
        } else {
            Logger.warning("No Data was found with the id: " + orderId);
            return false;
        }
    }

    public void updateOrderData(OrderData data){
        try {
            PreparedStatement statement = getConnection().prepareStatement(
                    "UPDATE OrderData SET CustomerID = ?, OrderTime = ?, TotalAmount = ?, PaymentMethod = ?, OrderStatus = ?, DeliveryTime = ?, SupporterID = ?, Notes = ? WHERE OrderID = ?;"
            );
            statement.setString(1, data.getCustomer().getId());
            statement.setTimestamp(2, data.getOrderTime());
            statement.setBigDecimal(3, data.getTotalPrice());
            statement.setString(4, data.getPaymentMethod());
            statement.setString(5, data.getOrderStatus());
            statement.setTimestamp(6, data.getDeliveryTime());
            statement.setString(7, data.getSupporter().getId());
            statement.setString(8, data.getNotes());
            statement.setString(9, data.getOrderId());
            statement.executeUpdate();
            statement.close();
        }catch (SQLException e){
            Logger.error("An error occurred while updating the OrderData");
            Logger.error(e.getMessage());
        }
    }

    public boolean deleteOrderData(String orderId) {
        try {
            PreparedStatement statement = getConnection().prepareStatement(
                    "DELETE FROM OrderData WHERE OrderID = ?;"
            );
            statement.setString(1, orderId);
            int rowsAffected = statement.executeUpdate();
            statement.close();

            if (rowsAffected > 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            Logger.error("An error occurred while deleting the OrderData.");
            Logger.error(e.getMessage());
            return false;
        }
    }
}
