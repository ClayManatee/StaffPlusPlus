package me.claymanatee.database;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class StaffDataCache {
    private static final HashMap<UUID, StaffMember> onlineStaff = new HashMap<UUID, StaffMember>();

    public static void loadAllOnlineStaff() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("staffplusplus.staffchat")) {
                loadStaff(p.getUniqueId());
            }
        }
    }

    public static boolean isLoadedStaff(UUID staffUUID){
        return onlineStaff.containsKey(staffUUID);
    }

    public static boolean isSavedStaff(UUID staffUUID){
        return StaffDatabase.getStaffDataAccess().findStaffByUUID(staffUUID) != null;
    }

    public static void deleteStaff(UUID staffUUID){
        if (isLoadedStaff(staffUUID)) {
            onlineStaff.remove(staffUUID);
        }
        if (isSavedStaff(staffUUID)) {
            StaffDatabase.getStaffDataAccess().deleteStaffByUUID(staffUUID);
        }
    }

    public static StaffMember loadStaff(UUID staffUUID){
        if (isLoadedStaff(staffUUID)) {
            return getLoadedStaff(staffUUID);
        }
        //
        StaffMember staffMember = StaffDatabase.getStaffDataAccess().findStaffByUUID(staffUUID);
        // if null, staff member has not been saved
        if (staffMember == null)
        {
            // check if this player has permission to staffchat
            Player p = Bukkit.getPlayer(staffUUID);
            if (p != null) {
                boolean shouldBeStaff = p.hasPermission("staffplusplus.staffchat");
                // if so, add them with the default settings
                if (shouldBeStaff) {
                    staffMember = StaffDatabase.getStaffDataAccess().insertDefaultStaff(staffUUID);
                }
            }
        }
        if (staffMember != null) {
            // put staff member in onlineStaff & return
            onlineStaff.put(staffUUID, staffMember);
        }
        return staffMember;
    }

    public static StaffMember getLoadedStaff(UUID staffUUID){
        if (!isLoadedStaff(staffUUID)){
            return null;
        }
        return onlineStaff.get(staffUUID);
    }

    public static Collection<StaffMember> getAllLoadedStaff(){
        return onlineStaff.values();
    }

    public static void updateLoadedStaff(StaffMember staffMember) {
        if(onlineStaff.containsKey(staffMember.getStaffUUID())){
            onlineStaff.replace(staffMember.getStaffUUID(), staffMember);
        }
    }

    public static void unloadStaff(UUID staffUUID){
        if (onlineStaff.containsKey(staffUUID)) {
            StaffMember staffMember = onlineStaff.get(staffUUID);
            StaffDatabase.getStaffDataAccess().updateStaff(staffMember);
            onlineStaff.remove(staffUUID);
        }
    }

    public static void unloadAllStaff(){
        for(UUID staffUUID : onlineStaff.keySet()){
            StaffDatabase.getStaffDataAccess().updateStaff(onlineStaff.get(staffUUID));
        }
        onlineStaff.clear();
    }
}
