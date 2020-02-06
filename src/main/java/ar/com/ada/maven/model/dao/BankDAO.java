package ar.com.ada.maven.model.dao;

import ar.com.ada.maven.model.DBConnection;
import ar.com.ada.maven.model.dto.BankDTO;
import ar.com.ada.maven.model.dto.CountryDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class BankDAO implements Dao<BankDTO>{

    private Boolean willCloseConnection = true;

    private BankDAO bankDAO = new BankDAO(false);
    private CountryDAO countryDAO = new CountryDAO(false);

    public BankDAO() {}
    public BankDAO(Boolean willCloseConnection){this.willCloseConnection = willCloseConnection;}

    @Override
    public ArrayList<BankDTO> findAll() {
        String sql = "SELECT * FROM Bank";
        ArrayList<BankDTO> banks = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()){
                CountryDTO country = new CountryDAO.findById(rs.getInt("Country_id"));
                BankDTO bank = new BankDTO(rs.getInt("id"), rs.getString("code"), rs.getString("name"), country);
                banks.add(bank);
            }
            connection.close();
        } catch (InstantiationException |SQLException | IllegalAccessException | ClassNotFoundException e) {
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }
        return banks;
    }

    @Override
    public BankDTO findById(Integer id) {
        String sql = "SELECT * FROM Bank WHERE id = ?";
        BankDTO bank = null;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                CountryDTO country = countryDAO.findById(rs.getInt(Country_id));
                bank = new BankDTO(rs.getInt("id"), rs.getString("code"), rs.getString("name"), country);
            } if (willCloseConnection)
                connection.close();
        } catch (Exception e){
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }
        return bank;
    }

    @Override
    public Boolean save(BankDTO bankDTO) {
        String sql = "INSERT INTO Bank (code, name, Country_id) VALUES (?, ?, ?)";
        int affectedRows = 0;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, bankDTO.getCode());
            preparedStatement.setString(2, bankDTO.getName());
            preparedStatement.setInt(3, bankDTO.getCountryID().getId());
            affectedRows = preparedStatement.executeUpdate();
            connection.close();
        } catch (Exception e){
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }
        return affectedRows == 1;
    }

    @Override
    public Boolean update(BankDTO bankDTO, Integer id) {
        String sql = "UPDATE Bank SET code = ?, name = ?, Country_id = ? WHERE Id = ?";
        int hasUpdate = 0;
        BankDTO bankDB = findById(id);

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, bankDTO.getCode());
            preparedStatement.setString(2, bankDTO.getName());
            preparedStatement.setInt(3, bankDTO.getCountryID().getId());

            if (!(bankDTO.getCode().equals(bankDB.getCode()) && bankDTO.getName().equals(bankDB.getName()) &&
                    bankDTO.getCountryID().equals(bankDB.getCountryID())));
            hasUpdate = preparedStatement.executeUpdate();
        } catch (Exception e){
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }
        return hasUpdate == 1;
    }

    @Override
    public Boolean delete(Integer id) {
        String sql = "DELETE FROM Bank WHERE id = ?";
        int hasDelete = 0;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            hasDelete = preparedStatement.executeUpdate();
        } catch (Exception e){
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }
        return hasDelete == 1;
    }
}
