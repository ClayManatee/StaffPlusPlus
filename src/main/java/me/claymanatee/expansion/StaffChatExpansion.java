package me.claymanatee.expansion;

import me.claymanatee.database.StaffDataCache;
import me.claymanatee.database.StaffMember;
import me.claymanatee.staffPlusPlus.StaffPlusPlus;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StaffChatExpansion extends PlaceholderExpansion {

    private final StaffPlusPlus plugin;

    public StaffChatExpansion() {
        this.plugin = StaffPlusPlus.getPlugin();
    }

    @Override
    @NotNull
    public String getAuthor() {
        return String.join(", ", plugin.getDescription().getAuthors());
    }

    @Override
    @NotNull
    public String getIdentifier() {
        return "staffplusplus";
    }

    @Override
    @NotNull
    public String getVersion() {
        return StaffPlusPlus.getPlugin().getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if (params.equals("staffchat_toggled")) {
            if (player.hasPermission("staffplusplus.staffchat")) {
                StaffMember staffMember = StaffDataCache.getLoadedStaff(player.getUniqueId());
                if (staffMember != null) {
                    return (staffMember.getStaffChatToggled() ? "On" : "Off");
                }
            } else {
                return "N/A";
            }
        }
        return null;
    }
}
