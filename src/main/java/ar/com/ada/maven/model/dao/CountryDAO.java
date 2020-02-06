package ar.com.ada.maven.model.dao;

import ar.com.ada.maven.model.DBConnection;
import ar.com.ada.maven.model.dto.CountryDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CountryDAO implements Dao<CountryDTO>{

    private Boolean willCloseConnection = true;

    public CountryDAO(){}
    public CountryDAO(Boolean willCloseConnection) {this.willCloseConnection = willCloseConnection;}

    @Override
    public ArrayList<CountryDTO> findAll(int limit, int offset) {
        String sql = "SELECT * FROM Country LIMIT ? OFFSET ?";
        ArrayList<CountryDTO> countries = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, offset);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                CountryDTO country = new CountryDTO(rs.getInt("id"), rs.getString("name"));
                countries.add(country);
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }

        return countries;
    }

    @Override
    public CountryDTO findById(Integer id) {
        String sql = "SELECT * FROM Country WHERE id = ?";
        CountryDTO country = null;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next())
                country = new CountryDTO(rs.getInt("id"), rs.getString("name"));
            if (willCloseConnection)
                connection.close();
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            ex.printStackTrace();
            System.out.println("CONNECTION ERROR: " + ex.getMessage());
        }
        return country;
    }

    @Override
    public Boolean save(CountryDTO countryDTO) {
        String sql = "INSERT INTO Country (name, code) values (?, ?)";
        int affectatedRows = 0;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, countryDTO.getName());
            preparedStatement.setString(2, countryDTO.getCode());
            affectatedRows = preparedStatement.executeUpdate();
            connection.close();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return affectatedRows == 1;
    }

    @Override
    public Boolean update(CountryDTO countryDTO, Integer id) {
        String sql = "UPDATE Country SET name = ?, code = ? WHERE id = ?";
        int hasUpdate = 0;

        CountryDTO countryDB = findById(id);

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, countryDTO.getName());
            preparedStatement.setString(2, countryDTO.getCode());
            preparedStatement.setInt(3, id);

            if (!(countryDTO.getName().equals(countryDB.getName()) &&
                    countryDTO.getCode().equals(countryDB.getCode())));
            hasUpdate = preparedStatement.executeUpdate();
            connection.close();
        } catch (Exception e) {
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }
        return hasUpdate == 1;
    }

    @Override
    public Boolean delete(Integer id) {
        String sql = "DELETE FROM Country WHERE id = ?";
        int hasDelete = 0;

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            hasDelete = preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }
        return hasDelete == 1;
    }
}
