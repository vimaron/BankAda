package ar.com.ada.maven.model.dao;

import ar.com.ada.maven.model.DBConnection;
import ar.com.ada.maven.model.dto.BankDTO;
import ar.com.ada.maven.model.dto.CountryDTO;

import java.sql.*;
import java.util.ArrayList;

public class BankDAO {

    private Boolean willCloseConnection = true;

    private CountryDAO countryDAO = new CountryDAO(false);

    public BankDAO() {}
    public BankDAO(Boolean willCloseConnection){this.willCloseConnection = willCloseConnection;}


    public ArrayList<BankDTO> findAll() {
        String sql = "SELECT * FROM Bank";
        ArrayList<BankDTO> banks = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()){
                CountryDTO country = countryDAO.findById(rs.getInt("Country_id"));
                BankDTO bank = new BankDTO(rs.getInt("id"), rs.getString("code"), rs.getString("name"), country);
                banks.add(bank);
            }
            connection.close();
        } catch (InstantiationException |SQLException | IllegalAccessException | ClassNotFoundException e) {
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }
        return banks;
    }

    public BankDTO findById(Integer id) {
        String sql = "SELECT * FROM Bank WHERE id = ?";
        BankDTO bank = null;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                CountryDTO country = countryDAO.findById(rs.getInt("Country_id"));
                bank = new BankDTO(rs.getInt("id"), rs.getString("code"), rs.getString("name"), country);
            } if (willCloseConnection)
                connection.close();
        } catch (Exception e){
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }
        return bank;
    }

    public Boolean save(BankDTO bank) {
        String sql = "INSERT INTO Bank (code, name, Country_id) VALUES (?,?,?)";
        int hasInsert = 0;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, bank.getCode());
            preparedStatement.setString(2, bank.getName());
            preparedStatement.setInt(3, bank.getCountryID().getId());
            hasInsert = preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }
        return hasInsert == 1;
    }

    public Boolean update(BankDTO bank, Integer id) {
        String sql = "UPDATE Bank SET code = ?, name = ?, Country_id = ? WHERE id = ?";
        int hasUpdate = 0;

        BankDTO bankDB = findById(bank.getId());

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, bank.getCode());
            preparedStatement.setString(2, bank.getName());
            preparedStatement.setInt(3, bank.getCountryID().getId());

            if (!bank.equals(bankDB))
                hasUpdate = preparedStatement.executeUpdate();

            connection.close();
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }

        return hasUpdate == 1;
    }

    public Boolean delete(Integer id) {
        String sql = "DELETE FROM Bank WHERE id = ?";
        int hasDelete = 0;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            hasDelete = preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }
        return hasDelete == 1;
    }

    public int getTotalBanks() {
        String sql = "SELECT COUNT(*) AS total FROM Bank";
        int total = 0;
        try {
            Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next())  total = rs.getInt("total");
            connection.close();
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }

        return total;
    }

    public BankDTO findByName(String name) {
        String sql = "SELECT * FROM Bank WHERE name = ?";
        BankDTO bank = null;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                CountryDTO country = countryDAO.findById(rs.getInt("Country_id"));
                bank = new BankDTO(rs.getInt("id"), rs.getString("code"), rs.getString("name"), country);
            }

            if (willCloseConnection) connection.close();
        } catch (Exception e) {
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }

        return bank;
    }

    public BankDTO findByCode(String code) {
        String sql = "SELECT * FROM Bank WHERE code = ?";
        BankDTO bank = null;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, code);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                CountryDTO country = countryDAO.findById(rs.getInt("Country_id"));
                bank = new BankDTO(rs.getInt("id"), rs.getString("code"), rs.getString("name"), country);
            }

            if (willCloseConnection) connection.close();
        } catch (Exception e) {
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }

        return bank;
    }

    public ArrayList<BankDTO> findAll(int limit, int offset) {
        String sql = "SELECT * FROM Bank LIMIT ? OFFSET ?";
        ArrayList<BankDTO> banks = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, offset);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                CountryDTO country = countryDAO.findById(rs.getInt("Country_id"));
                BankDTO bank = new BankDTO(rs.getInt("id"), rs.getString("code"), rs.getString("name"), country);
                banks.add(bank);
            }
            connection.close();
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }
        return banks;
    }

}
