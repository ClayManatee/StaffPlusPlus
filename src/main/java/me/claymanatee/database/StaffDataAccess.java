package me.claymanatee.database;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class StaffDataAccess {

    public StaffMember insert(UUID staffUUID){
        StaffMember defaultStaffMember = new StaffMember(staffUUID);
        return insert(defaultStaffMember);
    }

    public StaffMember insert(StaffMember staffMember){
        try {
            PreparedStatement statement = StaffDatabase.getConnection()
                    .prepareStatement("INSERT INTO StaffMembers(StaffUUID, TextColor, AccentColor, StaffChatToggled) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, staffMember.getStaffUUID().toString());
            statement.setString(2, staffMember.getTextColorChar());
            statement.setString(3, staffMember.getAccentColorChar());
            statement.setBoolean(4, staffMember.getStaffChatToggled());

            statement.execute();

            //Get the id of the just created staff member
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    staffMember.setId(generatedKeys.getInt(1));
                    return staffMember;
                } else {
                    throw new SQLException("Creating staff member failed - no ID obtained.");
                }
            }

        } catch (SQLException throwables) {
            Bukkit.getLogger().warning("[StaffPlusPlus]" + throwables.toString());
        }

        return null;
    }

    public void update(StaffMember staffMember){
        PreparedStatement statement;

        try {

            statement = StaffDatabase.getConnection()
                    .prepareStatement("UPDATE StaffMembers SET StaffUUID = ?, TextColor = ?, AccentColor = ?, StaffChatToggled = ? WHERE StaffID = ?");
            statement.setString(1, staffMember.getStaffUUID().toString());
            statement.setString(2, staffMember.getTextColorChar());
            statement.setString(3, staffMember.getAccentColorChar());
            statement.setBoolean(4, staffMember.getStaffChatToggled());
            statement.setInt(5, staffMember.getId());

            statement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Error updating staff member info in the database.");
            Bukkit.getLogger().warning("[StaffPlusPlus] Error updating staff member info in the database.");
        }
    }

    public StaffMember findByUUID(UUID staffUUID){
        PreparedStatement preparedStatement;
        StaffMember staffMember = null;

        try {
            preparedStatement = StaffDatabase.getConnection()
                    .prepareStatement("SELECT * FROM StaffMembers WHERE StaffUUID = ?");
            preparedStatement.setString(1, staffUUID.toString());

            ResultSet found = preparedStatement.executeQuery();

            while (found.next()) {
                staffMember = new StaffMember(
                        staffUUID,
                        found.getString("TextColor"),
                        found.getString("AccentColor"),
                        found.getBoolean("StaffChatToggled"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return staffMember;
    }

    public static ArrayList<StaffMember> findAll() {

        ArrayList<StaffMember> staffRoster = new ArrayList<>();
        PreparedStatement preparedStatement;

        try{
            preparedStatement = StaffDatabase.getConnection().prepareStatement("SELECT * FROM StaffMembers");
            ResultSet result = preparedStatement.executeQuery();

            while(result.next()){
                StaffMember staffMember = new StaffMember(
                        UUID.fromString(result.getString("StaffUUID")),
                        result.getString("TextColor"),
                        result.getString("AccentColor"),
                        result.getBoolean("StaffChatToggled"));
                staffRoster.add(staffMember);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return staffRoster;
    }

    public void deleteByUUID(UUID staffUUID){
        PreparedStatement preparedStatement;

        try {
            preparedStatement = StaffDatabase.getConnection()
                    .prepareStatement("DELETE FROM StaffMembers WHERE StaffUUID = ?");
            preparedStatement.setString(1, staffUUID.toString());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll(){
        PreparedStatement preparedStatement;

        try {
            preparedStatement = StaffDatabase.getConnection().prepareStatement("DELETE FROM StaffMembers");

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
