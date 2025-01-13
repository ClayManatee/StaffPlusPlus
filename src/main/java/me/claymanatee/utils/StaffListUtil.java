package me.claymanatee.utils;

import me.claymanatee.database.StaffDataAccess;
import me.claymanatee.database.StaffDataCache;
import me.claymanatee.database.StaffMember;

import com.earth2me.essentials.Essentials;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class StaffListUtil {
    private static final Essentials essentials = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");

    public static void sendOnlineStaffList(StaffMember staffMember) {
        StringBuilder list = new StringBuilder(staffMember.getTextColor() + "---[" + staffMember.getAccentColor() + "Online Staff" + staffMember.getTextColor() + "]---");
        for (StaffMember onlineStaffMember : StaffDataCache.getAllOnlineStaff()){
            Player staffPlayer = Bukkit.getPlayer(staffMember.getStaffUUID());
            if(staffPlayer != null) {
                String vanishStatus = essentials.getUser(staffPlayer).isHidden() ? ChatColor.RED + "[Vanished]" : ChatColor.GREEN + "[Visible]";
                String afkStatus = essentials.getUser(staffPlayer).isAfk() ? ChatColor.RED + "[AFK]" : ChatColor.GREEN + "[Active]";
                String staffMemberInfo = "\n" + staffMember.getTextColor() + " - " + staffMember.getAccentColor() + staffPlayer.getName() + " " + vanishStatus + " " + afkStatus;
                list.append(staffMemberInfo);
            }
        }
        Player staffPlayer = Bukkit.getPlayer(staffMember.getStaffUUID());
        if(staffPlayer != null) {
            staffPlayer.sendMessage(list.toString());
        }
    }

    public static void sendFullStaffRoster(StaffMember staffMember) {
        StringBuilder staffRoster = new StringBuilder(staffMember.getTextColor() + "---[" + staffMember.getAccentColor() + "Full Staff Roster" + staffMember.getTextColor() + "]---");
        for(StaffMember sm : StaffDataAccess.findAll()){
            Player staffPlayer = Bukkit.getPlayer(sm.getStaffUUID());
            if(staffPlayer != null) {
                staffRoster.append("\n" + staffMember.getAccentColor() + " - " + staffMember.getTextColor() + staffPlayer.getName() + ", " + staffPlayer.getUniqueId());
            }
        }
        Player staffPlayer = Bukkit.getPlayer(staffMember.getStaffUUID());
        if(staffPlayer != null) {
            staffPlayer.sendMessage(staffRoster.toString());
        }
    }
}
