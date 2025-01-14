package me.claymanatee.utils;

import me.claymanatee.database.StaffDataAccess;
import me.claymanatee.database.StaffDataCache;
import me.claymanatee.database.StaffMember;

import com.earth2me.essentials.Essentials;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class StaffListUtil {
    private static final Essentials essentials = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");

    public static void sendOnlineStaffList(StaffMember staffMember) {
        StringBuilder list = new StringBuilder(staffMember.getTextColor() + "---[" + staffMember.getAccentColor() + "Online Staff" + staffMember.getTextColor() + "]---");
        for (StaffMember onlineStaffMember : StaffDataCache.getAllOnlineStaff()){
            Player staffPlayer = Bukkit.getPlayer(onlineStaffMember.getStaffUUID());
            if(staffPlayer != null) {
                String staffMemberInfo = "\n" + staffMember.getTextColor() + " - " + staffMember.getAccentColor() + staffPlayer.getName();
                if (essentials != null) {
                    String vanishStatus = essentials.getUser(staffPlayer).isHidden() ? ChatColor.RED + "[Vanished]" : ChatColor.GREEN + "[Visible]";
                    String afkStatus = essentials.getUser(staffPlayer).isAfk() ? ChatColor.RED + "[AFK]" : ChatColor.GREEN + "[Active]";
                    staffMemberInfo += " " + vanishStatus + " " + afkStatus;
                }
                list.append(staffMemberInfo);
            }
        }
        Player staffPlayer = Bukkit.getPlayer(staffMember.getStaffUUID());
        if(staffPlayer != null) {
            staffPlayer.sendMessage(list.toString());
        }
    }

    public static void sendFullStaffRoster(StaffMember staffMember) {
        ComponentBuilder staffRosterBuilder = new ComponentBuilder("").color(staffMember.getTextColor().asBungee())
                .append("Full Staff Roster").color(staffMember.getAccentColor().asBungee())
                .append("]---").color(staffMember.getTextColor().asBungee());
        TextComponent staffRoster = new TextComponent(staffRosterBuilder.create());

        for(StaffMember sm : StaffDataAccess.findAll()){
            Player staffPlayer = Bukkit.getPlayer(sm.getStaffUUID());
            if(staffPlayer != null) {
                TextComponent fullMessage = new TextComponent("\n- ");
                fullMessage.setColor(staffMember.getAccentColor().asBungee());

                TextComponent messageBody = new TextComponent(sm.getName());
                messageBody.setColor(staffMember.getTextColor().asBungee());
                fullMessage.addExtra(messageBody);

                TextComponent breaker = new TextComponent(", ");
                breaker.setColor(staffMember.getTextColor().asBungee());
                fullMessage.addExtra(breaker);

                TextComponent uuidLink = new TextComponent("Staff UUID");
                uuidLink.setItalic(true);
                uuidLink.setColor(staffMember.getAccentColor().asBungee());
                uuidLink.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(sm.getStaffUUID().toString())));
                uuidLink.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, sm.getStaffUUID().toString()));
                fullMessage.addExtra(uuidLink);

                staffRoster.addExtra(fullMessage);
            }
        }

        Player staffPlayer = Bukkit.getPlayer(staffMember.getStaffUUID());
        if(staffPlayer != null) {
            staffPlayer.spigot().sendMessage(staffRoster);
        }
    }
}
