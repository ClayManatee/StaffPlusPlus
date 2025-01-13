package me.claymanatee.commands;

import me.claymanatee.database.StaffMember;
import me.claymanatee.database.StaffDataCache;
import me.claymanatee.staffPlusPlus.StaffPlusPlus;
import me.claymanatee.utils.ColorUtil;
import me.claymanatee.utils.StaffNotiUtil;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class StaffChatCommand implements CommandExecutor {

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

        if (args.length == 0) {
            String message = StaffPlusPlus.getPluginHeader(staffMember.getTextColor(),
                    StaffPlusPlus.getErrorColor());
            message += staffMember.getTextColor() + "You need to type something, silly!";
            player.sendMessage(message);
            return true;
        }

        if (args[0].equalsIgnoreCase("toggle")) {
            staffMember.setStaffChatToggled(!staffMember.getStaffChatToggled());
            StaffDataCache.updateOnlineStaff(staffMember);

            String message = StaffPlusPlus.getPluginHeader(staffMember.getTextColor(),
                    staffMember.getAccentColor());
            message += staffMember.getTextColor() + "updated staff chat toggle to ";
            message += (staffMember.getStaffChatToggled() ? "on." : "off.");
            player.sendMessage(message);

            return true;
        }

        if (args[0].equalsIgnoreCase("textcolor")) {
            ArrayList<String> availableColorChars = ColorUtil.getAvailableColorChars();

            if (args.length < 2) {
                String message = StaffPlusPlus.getPluginHeader(staffMember.getTextColor(),
                        StaffPlusPlus.getErrorColor());
                message += staffMember.getTextColor() + "please provide a color.\n";
                message += ColorUtil.getAvailableColorInfo();
                player.sendMessage(message);
                return true;
            }

            try {
                ChatColor newColor = ChatColor.getByChar(args[1]);

                if (!availableColorChars.contains(args[1])) {
                    String message = StaffPlusPlus.getPluginHeader(staffMember.getTextColor(),
                            StaffPlusPlus.getErrorColor());
                    message += staffMember.getTextColor() + "your choice is not a color.\n";
                    message += ColorUtil.getAvailableColorInfo();
                    player.sendMessage(message);
                    return true;
                }

                else {
                    staffMember.setTextColor(newColor);
                    StaffDataCache.updateOnlineStaff(staffMember);

                    String message = StaffPlusPlus.getPluginHeader(staffMember.getTextColor(),
                            staffMember.getAccentColor());
                    message += staffMember.getTextColor() + "updated staff text color to ";
                    message += staffMember.getTextColorChar();
                    player.sendMessage(message);
                    return true;
                }
            }
            catch (IllegalArgumentException e) {
                String message = StaffPlusPlus.getPluginHeader(staffMember.getTextColor(),
                        StaffPlusPlus.getErrorColor());
                message += staffMember.getTextColor() + "invalid color choice.\n";
                message += ColorUtil.getAvailableColorInfo();
                player.sendMessage(message);
                return true;
            }
        }

        if (args[0].equalsIgnoreCase("accentcolor")) {
            ArrayList<String> availableColorChars = ColorUtil.getAvailableColorChars();

            if (args.length < 2) {
                String message = StaffPlusPlus.getPluginHeader(staffMember.getTextColor(),
                        StaffPlusPlus.getErrorColor());
                message += staffMember.getTextColor() + "please provide a color.\n";
                message += ColorUtil.getAvailableColorInfo();
                player.sendMessage(message);
                return true;
            }

            try {
                ChatColor newColor = ChatColor.getByChar(args[1]);

                if (!availableColorChars.contains(args[1])) {
                    String message = StaffPlusPlus.getPluginHeader(staffMember.getTextColor(),
                            StaffPlusPlus.getErrorColor());
                    message += staffMember.getTextColor() + "invalid color choice.\n";
                    message += ColorUtil.getAvailableColorInfo();
                    player.sendMessage(message);
                    return true;
                }

                else {
                    staffMember.setAccentColor(newColor);
                    StaffDataCache.updateOnlineStaff(staffMember);

                    String message = StaffPlusPlus.getPluginHeader(staffMember.getTextColor(),
                            staffMember.getAccentColor());
                    message += staffMember.getTextColor() + "updated staff accent color to ";
                    message += staffMember.getAccentColor() + staffMember.getAccentColorChar();
                    player.sendMessage(message);
                    return true;
                }
            }
            catch (IllegalArgumentException e) {
                String message = StaffPlusPlus.getPluginHeader(staffMember.getTextColor(),
                        StaffPlusPlus.getErrorColor());
                message += staffMember.getTextColor() + "invalid color choice.\n";
                message += ColorUtil.getAvailableColorInfo();
                player.sendMessage(message);
                return true;
            }
        }

        else {
            StaffNotiUtil.notifyAll(staffMember, ChatColor.stripColor(String.join(" ", args)));
            return true;
        }
    }
}
