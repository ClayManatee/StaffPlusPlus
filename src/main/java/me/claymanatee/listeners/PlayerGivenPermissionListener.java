package me.claymanatee.listeners;

import me.claymanatee.database.StaffDataCache;
import me.claymanatee.database.StaffDatabase;
import me.claymanatee.staffPlusPlus.StaffPlusPlus;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.node.NodeAddEvent;
import net.luckperms.api.event.node.NodeRemoveEvent;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;

public class PlayerGivenPermissionListener {
    private final LuckPerms luckPerms;

    public PlayerGivenPermissionListener(LuckPerms luckPerms) {
        this.luckPerms = luckPerms;
    }

    public void register() {
        EventBus eventBus = this.luckPerms.getEventBus();
        eventBus.subscribe(StaffPlusPlus.getPlugin(), NodeAddEvent.class, this::onNodeAdd);
        eventBus.subscribe(StaffPlusPlus.getPlugin(), NodeRemoveEvent.class, this::onNodeRemove);
    }

    private void onNodeAdd(NodeAddEvent e) {
        if (!e.isUser()) {
            return;
        }
        Node node = e.getNode();
        if (node.getType() != NodeType.PERMISSION) {
            return;
        }
        User user = (User) e.getTarget();
        if (node.getKey().equals("staffplusplus.staffchat")) {
            StaffDataCache.loadStaff(user.getUniqueId());
        }
    }

    private void onNodeRemove(NodeRemoveEvent e) {
        if (!e.isUser()) {
            return;
        }
        Node node = e.getNode();
        if (node.getType() != NodeType.PERMISSION) {
            return;
        }
        User user = (User) e.getTarget();
        if (node.getKey().equals("staffplusplus.staffchat")) {
            StaffDataCache.unloadStaff(user.getUniqueId());
            StaffDatabase.getStaffDataAccess().deleteByUUID(user.getUniqueId());
        }
    }
}
