package me.claymanatee.listeners;

import me.claymanatee.database.StaffDataCache;
import me.claymanatee.staffPlusPlus.StaffPlusPlus;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.node.NodeAddEvent;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class PlayerGivenPermissionListener {
    private final LuckPerms luckPerms;

    public PlayerGivenPermissionListener(LuckPerms luckPerms) {
        this.luckPerms = luckPerms;
    }

    public void register() {
        EventBus eventBus = this.luckPerms.getEventBus();
        eventBus.subscribe(StaffPlusPlus.getPlugin(), NodeAddEvent.class, this::onNodeAdd);
    }

    private void onNodeAdd(NodeAddEvent e) {
        // Check if the event was acting on a User
        if (!e.isUser()) {
            return;
        }

        // Check if the node was a permission node
        Node node = e.getNode();
        if (node.getType() != NodeType.PERMISSION) {
            return;
        }

        User user = (User) e.getTarget();
        if (node.getKey().equals("staffplusplus.staffchat")) {
            StaffDataCache.loadStaff(user.getUniqueId());
        }
    }

}
