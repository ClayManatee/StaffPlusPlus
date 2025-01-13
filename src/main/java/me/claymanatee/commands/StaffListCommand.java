package me.claymanatee.commands;

import me.claymanatee.database.StaffDataCache;
import me.claymanatee.database.StaffMember;
import me.claymanatee.staffPlusPlus.StaffPlusPlus;
import me.claymanatee.utils.StaffListUtil;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffListCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Player only command.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("staffplusplus.staffchat")) {
            sender.sendMessage(StaffPlusPlus.getErrorColor() + "No permission.");
            return true;
        }

        StaffMember staffMember = StaffDataCache.getOnlineStaff(player.getUniqueId());
        if (staffMember != null) {
            StaffListUtil.sendOnlineStaffList(staffMember);
        }
        return true;
    }
}
