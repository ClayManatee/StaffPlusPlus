package me.claymanatee.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
                String colorCode = color.toString();
                String colorChar = String.valueOf(color.getChar());
                list.add(colorCode + colorChar);
            }
        }
        return "Color Options: " + String.join(", ", list);
    }
}
