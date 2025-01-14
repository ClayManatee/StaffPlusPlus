package me.claymanatee.database;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class StaffDataCache {
    private static final HashMap<UUID, StaffMember> onlineStaff = new HashMap<UUID, StaffMember>();

    public static void loadAllStaff() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("staffplusplus.staffchat")) {
                loadStaff(p.getUniqueId());
            }
        }
    }

    public static StaffMember loadStaff(UUID staffUUID){
        StaffMember staffMember = StaffDatabase.getStaffDataAccess().findByUUID(staffUUID);
        if (staffMember == null)
        {
            staffMember = StaffDatabase.getStaffDataAccess().insert(staffUUID);
        }
        onlineStaff.put(staffUUID, staffMember);
        return staffMember;
    }

    public static StaffMember getOnlineStaff(UUID staffUUID){
        StaffMember staffMember = onlineStaff.get(staffUUID);
        if (staffMember == null) {
            staffMember = loadStaff(staffUUID);
        }
        return staffMember;
    }

    public static Collection<StaffMember> getAllOnlineStaff(){
        return onlineStaff.values();
    }

    public static void updateOnlineStaff(StaffMember staffMember) {
        if(onlineStaff.containsKey(staffMember.getStaffUUID())){
            onlineStaff.replace(staffMember.getStaffUUID(), staffMember);
        }
    }

    public static void unloadStaff(UUID staffUUID){
        if(onlineStaff.containsKey(staffUUID)) {
            StaffMember staffMember = onlineStaff.get(staffUUID);
            StaffDatabase.getStaffDataAccess().update(staffMember);
            onlineStaff.remove(staffUUID);
        }
    }

    public static void unloadAllStaff(){
        for(UUID staffUUID : onlineStaff.keySet()){
            StaffDatabase.getStaffDataAccess().update(onlineStaff.get(staffUUID));
        }
        onlineStaff.clear();
    }
}
