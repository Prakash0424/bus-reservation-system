package com.Bus_Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDbAccess {

    private static final String INSERT_ACCOUNT_QUERY = "INSERT INTO Account (userName, password, accountType) VALUES (?, ?, ?)";
    private static final String DISPLAY_ACCOUNT_DETAILS_QUERY = "SELECT user_id, userName, accountType FROM Account WHERE user_id = ?";
    private static final String VALIDATE_ACCOUNT_QUERY = "SELECT password FROM Account WHERE user_id = ?";
    private static final String GET_ACCOUNT_TYPE_QUERY = "SELECT accountType FROM Account WHERE user_id = ?";

    public void addAccount(Account account) throws Exception {
        try (Connection con = Db_connection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_ACCOUNT_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, account.getUserName());
            ps.setString(2, account.getPassword());
            ps.setString(3, account.getAccountType());

            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                int lastInsertedAccountNo = generatedKeys.getInt(1);
                account.setUserId(lastInsertedAccountNo);
            }
        } catch (SQLException e) {
            throw new Exception("Error adding account", e);
        }
    }

    public void displayAccountDetails(Account account) throws Exception {
        try (Connection con = Db_connection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(DISPLAY_ACCOUNT_DETAILS_QUERY)) {

            preparedStatement.setInt(1, account.getUserId());
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                System.out.println("----------------Account Details!---------------");
                System.out.println("Hi! " + rs.getString("userName"));
                System.out.println("Your User Id is: " + rs.getInt("user_id"));
                System.out.println("Account Type: " + rs.getString("accountType"));
            }
        } catch (SQLException e) {
            throw new Exception("Error displaying account details", e);
        }
    }

    public boolean accountValidation(int userId, String password) throws Exception {
        try (Connection con = Db_connection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(VALIDATE_ACCOUNT_QUERY)) {

            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();

            return rs.next() && rs.getString("password").equals(password);
        } catch (SQLException e) {
            throw new Exception("Error validating account", e);
        }
    }

    public String getAccountType(int userId) throws Exception {
        try (Connection con = Db_connection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(GET_ACCOUNT_TYPE_QUERY)) {

            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();

            return rs.next() ? rs.getString("accountType") : "";
        } catch (SQLException e) {
            throw new Exception("Error getting account type", e);
        }
    }

    public void closeAccount(int userId) throws Exception {
        try (Connection con = Db_connection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM Account WHERE user_id = ?")) {
        	
            BookingDbAccess bookingDbAccess = new BookingDbAccess();
            bookingDbAccess.cancelBookingByUserId(userId);
            
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error closing account", e);
        }
    }

    public String getUserName(int userId) throws Exception {
        try (Connection con = Db_connection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement("SELECT userName FROM Account WHERE user_id = ?")) {

            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();

            return rs.next() ? rs.getString("userName") : "";
        } catch (SQLException e) {
            throw new Exception("Error getting user name", e);
        }
    }
}
