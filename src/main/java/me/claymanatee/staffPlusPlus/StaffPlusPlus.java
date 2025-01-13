package me.claymanatee.staffPlusPlus;

import me.claymanatee.commands.StaffChatCommand;
import me.claymanatee.commands.StaffListCommand;
import me.claymanatee.commands.StaffPurgeCommand;
import me.claymanatee.commands.StaffRosterCommand;
import me.claymanatee.database.StaffDataCache;
import me.claymanatee.database.StaffDatabase;
import me.claymanatee.expansion.StaffChatExpansion;
import me.claymanatee.listeners.PlayerChatListener;
import me.claymanatee.listeners.PlayerGivenPermissionListener;
import me.claymanatee.listeners.PlayerJoinLeaveListener;

import net.luckperms.api.LuckPerms;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class StaffPlusPlus extends JavaPlugin {

    private static LuckPerms luckPerms;
    private static final ChatColor errorColor = ChatColor.RED;
    private static StaffPlusPlus plugin;
    private static String url;

    public static StaffPlusPlus getPlugin() {
        return plugin;
    }

    public static String getConnectionURL() {
        return url;
    }

    @Override
    public void onEnable() {
        plugin = this;
        luckPerms = getServer().getServicesManager().load(LuckPerms.class);
        url = "jdbc:h2:" + getDataFolder().getAbsolutePath() + "/data/staffplusplus";
        Bukkit.getLogger().info("[StaffPlusPlus] DB URL - " + url);
        StaffDatabase.initializeDatabase();

        getCommand("staffchat").setExecutor(new StaffChatCommand());
        getCommand("stafflist").setExecutor(new StaffListCommand());
        getCommand("staffroster").setExecutor(new StaffRosterCommand());
        getCommand("staffpurge").setExecutor(new StaffPurgeCommand());

        getServer().getPluginManager().registerEvents(new PlayerJoinLeaveListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerChatListener(), this);

        new PlayerGivenPermissionListener(luckPerms).register();

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new StaffChatExpansion().register();
        }
    }

    @Override
    public void onDisable() {
        StaffDataCache.unloadAllStaff();

        try {
            StaffDatabase.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ChatColor getErrorColor() {
        return errorColor;
    }

    public static String getPluginHeader(ChatColor textColor, ChatColor accentColor){
        return textColor + "[" + accentColor + "Staff" + textColor + "] ";
    }
}
