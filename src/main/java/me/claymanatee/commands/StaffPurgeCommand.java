package me.claymanatee.commands;

import me.claymanatee.database.StaffDataCache;
import me.claymanatee.database.StaffDatabase;
import me.claymanatee.staffPlusPlus.StaffPlusPlus;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffPurgeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Player only command.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("staffplusplus.admin")) {
            sender.sendMessage(StaffPlusPlus.getErrorColor() + "No permission.");
            return true;
        }

        player.sendMessage(StaffPlusPlus.getErrorColor() + "Purging staff database...");
        StaffDataCache.unloadAllStaff();
        StaffDatabase.getStaffDataAccess().deleteAll();
        player.sendMessage(StaffPlusPlus.getErrorColor() + "Staff database purged.");

        //TODO: can someone send a message in staff chat right "now", when no record of them exists? w/ async, might be possible...

        player.sendMessage(StaffPlusPlus.getErrorColor() + "Re-registering online staff...");
        StaffDataCache.loadAllStaff();
        player.sendMessage(StaffPlusPlus.getErrorColor() + "Online staff re-registered.");
        return true;
    }
}
