package me.claymanatee.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;

public class ColorUtil {

    public static ArrayList<String> getAvailableColorChars() {
        ChatColor[] allChatColorOptions = ChatColor.values();
        ArrayList<String> availableColors = new ArrayList<String>();
        for (ChatColor color : allChatColorOptions) {
            if (color.isColor()){
                availableColors.add(String.valueOf(color.getChar()));
            }
        }
        return availableColors;
    }

    public static String getAvailableColorInfo(){
        ArrayList<String> list = new ArrayList<String>();
        for (ChatColor color : ChatColor.values()) {
            if (color.isColor()) {
                list.add(color + String.valueOf(color.getChar()));
            }
        }
        return "Color Options: " + String.join(", ", list);
    }
}
