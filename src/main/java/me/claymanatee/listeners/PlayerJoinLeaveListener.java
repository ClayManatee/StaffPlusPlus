package me.claymanatee.listeners;

import me.claymanatee.database.StaffDataCache;
import me.claymanatee.staffPlusPlus.StaffPlusPlus;
import me.claymanatee.database.StaffMember;
import me.claymanatee.utils.StaffListUtil;
import me.claymanatee.utils.StaffNotiUtil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinLeaveListener implements Listener {
    private final BukkitScheduler scheduler;

    public PlayerJoinLeaveListener() {
        this.scheduler = StaffPlusPlus.getPlugin().getServer().getScheduler();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("staffplusplus.staffchat")) {
            StaffMember staffMember = StaffDataCache.loadStaff(player.getUniqueId());
            String joinMessage = ChatColor.GREEN + player.getName() + " joined the game.";
            for (StaffMember otherStaffMember : StaffDataCache.getAllLoadedStaff()){
                StaffNotiUtil.notify(otherStaffMember, joinMessage);
            }

            if (staffMember != null) {
                scheduler.runTaskLater(StaffPlusPlus.getPlugin(), () -> {
                    StaffListUtil.sendOnlineStaffList(staffMember);
                }, 10);
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("staffplusplus.staffchat")) {
            StaffDataCache.unloadStaff(player.getUniqueId());
            String leaveMessage = ChatColor.RED + player.getName() + " left the game.";
            for (StaffMember otherStaffMember : StaffDataCache.getAllLoadedStaff()){
                StaffNotiUtil.notify(otherStaffMember, leaveMessage);
            }
        }
    }
}
