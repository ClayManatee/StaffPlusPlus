package me.claymanatee.listeners;

import me.claymanatee.database.StaffDataCache;
import me.claymanatee.staffPlusPlus.StaffPlusPlus;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.user.UserDataRecalculateEvent;
import net.luckperms.api.model.user.User;

import org.bukkit.Bukkit;

public class PlayerGivenPermissionListener {
    private final LuckPerms luckPerms;

    public PlayerGivenPermissionListener(LuckPerms luckPerms) {
        this.luckPerms = luckPerms;
    }

    public void register() {
        if (this.luckPerms != null) {
            EventBus eventBus = this.luckPerms.getEventBus();
            eventBus.subscribe(StaffPlusPlus.getPlugin(), UserDataRecalculateEvent.class, this::onUserDataRecalculated);
        }
    }

    private void onUserDataRecalculated(UserDataRecalculateEvent e) {
        User u = e.getUser();
        boolean isOnline = Bukkit.getPlayer(u.getUniqueId()) != null;
        boolean isStaff = e.getData().getPermissionData().checkPermission("staffplusplus.staffchat").asBoolean();
        boolean isLoadedStaff = StaffDataCache.isLoadedStaff(u.getUniqueId());
        boolean isSavedStaff = StaffDataCache.isSavedStaff(u.getUniqueId());

        if (isOnline) {
            if (!isLoadedStaff && isStaff) {
                StaffDataCache.loadStaff(u.getUniqueId());
            }
            else if (isLoadedStaff && !isStaff) {
                StaffDataCache.deleteStaff(u.getUniqueId());
            }
        }
        else {
            if (isSavedStaff && !isStaff) {
                StaffDataCache.deleteStaff(u.getUniqueId());
            }
        }
    }
}
