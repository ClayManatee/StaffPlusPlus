package me.claymanatee.utils;

import me.claymanatee.database.StaffDataCache;
import me.claymanatee.database.StaffMember;

import me.claymanatee.staffPlusPlus.StaffPlusPlus;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class StaffNotiUtil {

    public static void notify(StaffMember staffMember, String message) {
        String h = StaffPlusPlus.getPluginHeader(staffMember.getTextColor(), staffMember.getAccentColor());
        Player p = Bukkit.getPlayer(staffMember.getStaffUUID());
        if (p != null) {
            p.sendMessage(h + staffMember.getTextColor() + message);
        }
    }

    public static void notifyAll(StaffMember sendingStaff, String message) {
        Player p = Bukkit.getPlayer(sendingStaff.getStaffUUID());
        if (p != null) {
            String senderName = p.getName();
            for (StaffMember staffMember : StaffDataCache.getAllOnlineStaff()) {
                String msg = staffMember.getAccentColor() + senderName + staffMember.getTextColor() + ": " + message;
                notify(staffMember, msg);
            }
        }
    }
}
