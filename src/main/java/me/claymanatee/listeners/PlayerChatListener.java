package me.claymanatee.listeners;

import me.claymanatee.database.StaffDataCache;
import me.claymanatee.database.StaffMember;
import me.claymanatee.utils.StaffNotiUtil;

import com.alessiodp.parties.api.events.bukkit.player.BukkitPartiesPlayerPreChatEvent;

import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import java.util.UUID;


public class PlayerChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(BukkitPartiesPlayerPreChatEvent event) {
        UUID uuid =  event.getPartyPlayer().getPlayerUUID();
        StaffMember staffMember = StaffDataCache.getOnlineStaff(uuid);
        if (staffMember != null) {
            event.setCancelled(staffMember.getStaffChatToggled());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("staffplusplus.staffchat")){
            StaffMember staffMember = StaffDataCache.getOnlineStaff(player.getUniqueId());
            if (staffMember.getStaffChatToggled()) {
                event.setCancelled(true);
                StaffNotiUtil.notifyAll(staffMember, event.getMessage());
            }
        }
    }
}
