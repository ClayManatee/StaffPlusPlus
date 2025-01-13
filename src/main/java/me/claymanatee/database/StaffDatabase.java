package me.claymanatee.database;

import me.claymanatee.staffPlusPlus.StaffPlusPlus;
import org.bukkit.Bukkit;

import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class StaffDatabase {

    private static Connection connection;
    private static StaffDataAccess staffDataAccess;

    public static StaffDataAccess getStaffDataAccess() {
        return staffDataAccess;
    }

    public static Connection getConnection() {

        //Try to establish a connection if it has not yet been made
        if (connection == null){
            try {
                Class.forName("org.h2.Driver");

                try {
                    connection = DriverManager.getConnection(StaffPlusPlus.getConnectionURL());

                } catch (SQLException e) {
                    Bukkit.getLogger().warning("[StaffPlusPlus] Unable to establish a connection with the database");
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException ex) {
                Bukkit.getLogger().warning("[StaffPlusPlus] Unable to establish a connection with the database.");
            }
        }
        return connection;
    }

    public static void initializeDatabase() {

        try {
            Statement statement = getConnection().createStatement();

            statement.execute("CREATE TABLE IF NOT EXISTS StaffMembers(StaffID int NOT NULL IDENTITY(1, 1), StaffUUID varchar(255), TextColor varchar(255), AccentColor varchar(255), StaffChatToggled boolean);");

            staffDataAccess = new StaffDataAccess();

            Bukkit.getLogger().info("[StaffPlusPlus] Database loaded");

            statement.close();

        } catch (SQLException e) {
            Bukkit.getLogger().warning("[StaffPlusPlus] Database initialization error.");
            e.printStackTrace();
        }


    }

}
