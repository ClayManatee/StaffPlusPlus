package me.claymanatee.database;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class StaffMember {

    public static final ChatColor DEFAULT_TEXT_COLOR = ChatColor.GRAY;
    public static final ChatColor DEFAULT_ACCENT_COLOR = ChatColor.GOLD;
    private static final Boolean DEFAULT_STAFF_CHAT_TOGGLED = false;

    private int id;
    private UUID staffUUID;
    private ChatColor textColor;
    private ChatColor accentColor;
    private Boolean staffChatToggled;

    public StaffMember() {
    }

    public StaffMember(UUID staffUUID) {
        this.staffUUID = staffUUID;
        this.textColor = DEFAULT_TEXT_COLOR;
        this.accentColor = DEFAULT_ACCENT_COLOR;
        this.staffChatToggled = DEFAULT_STAFF_CHAT_TOGGLED;
    }

    public StaffMember(UUID staffUUID, ChatColor textColor, ChatColor accentColor, Boolean staffChatToggled) {
        this.staffUUID = staffUUID;
        this.textColor = textColor;
        this.accentColor = accentColor;
        this.staffChatToggled = staffChatToggled;
    }

    public StaffMember(UUID staffUUID, String textColorChar, String accentColorChar, Boolean staffChatToggled) {
        this.staffUUID = staffUUID;
        this.staffChatToggled = staffChatToggled;
        this.setTextColorByChar(textColorChar);
        this.setAccentColorByChar(accentColorChar);
    }

    public String toString () {
        ArrayList<String> contents = new ArrayList<String>();
        contents.add(this.getName() + ":");
        contents.add("T - " + getTextColorChar());
        contents.add("A - " + getAccentColorChar());
        contents.add("Chat - " + (getStaffChatToggled() ? "on" : "off"));
        return String.join(" ", contents);
    }

    public String getName () {
        return Bukkit.getPlayer(staffUUID).getName();
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(staffUUID);
    }

    public UUID getStaffUUID() {
        return staffUUID;
    }

    public void setStaffUUID(UUID staffUUID) {
        this.staffUUID = staffUUID;
    }

    public ChatColor getTextColor() {
        return textColor;
    }

    public String getTextColorChar() {
        return String.valueOf(textColor.getChar());
    }

    public void setTextColor(ChatColor textColor) {
        this.textColor = textColor;
    }

    public void setTextColorByChar(String colorChar) {
        try {
            textColor = ChatColor.getByChar(colorChar);
        }
        catch (IllegalArgumentException e) {
            textColor = DEFAULT_TEXT_COLOR;
        }
    }

    public ChatColor getAccentColor() {
        return accentColor;
    }

    public String getAccentColorChar() {
        return String.valueOf(accentColor.getChar());
    }

    public void setAccentColor(ChatColor accentColor) {
        this.accentColor = accentColor;
    }

    public void setAccentColorByChar(String colorChar) {
        try {
            accentColor = ChatColor.getByChar(colorChar);
        }
        catch (IllegalArgumentException e) {
            accentColor = DEFAULT_ACCENT_COLOR;
        }
    }

    public Boolean getStaffChatToggled() {
        return staffChatToggled;
    }

    public void setStaffChatToggled(Boolean staffChatToggled) {
        this.staffChatToggled = staffChatToggled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
