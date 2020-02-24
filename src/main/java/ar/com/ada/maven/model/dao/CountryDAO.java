package ar.com.ada.maven.model.dao;

import ar.com.ada.maven.model.DBConnection;
import ar.com.ada.maven.model.dto.CountryDTO;

import java.sql.*;
import java.util.ArrayList;

public class CountryDAO {

    private Boolean willCloseConnection = true;

    public CountryDAO() {
    }

    public CountryDAO(Boolean willCloseConnection) {
        this.willCloseConnection = willCloseConnection;
    }

    public ArrayList<CountryDTO> findAll() {
        String sql = "SELECT * FROM Country LIMIT ? OFFSET ?";
        ArrayList<CountryDTO> countries = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
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

    public Boolean save(CountryDTO country) {
        String sql = "INSERT INTO Country (code, name) VALUES (?,?)";
        int hasInsert = 0;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, country.getCode());
            preparedStatement.setString(2, country.getName());
            hasInsert = preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }
        return hasInsert == 1;
    }

    public Boolean update(CountryDTO country) {
        String sql = "UPDATE Country SET code = ?, name = ? WHERE id = ?";
        int hasUpdate = 0;

        CountryDTO countryDB = findById(country.getId());

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, country.getCode());
            preparedStatement.setString(2, country.getName());

            if (!country.equals(countryDB))
                hasUpdate = preparedStatement.executeUpdate();

            connection.close();
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }

        return hasUpdate == 1;
    }

    public Boolean delete(Integer id) {
        String sql = "DELETE FROM Country WHERE id = ?";
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

    public int getTotalCountries() {
        String sql = "SELECT COUNT(*) AS total FROM Country";
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
                CountryDTO country = new CountryDTO(rs.getInt("id"), rs.getString("code"), rs.getString("name"));
                countries.add(country);
            }
            connection.close();
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }
        return countries;
    }

    public CountryDTO findByName(String name) {
        String sql = "SELECT * FROM Country WHERE name = ?";
        CountryDTO country = null;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                country = new CountryDTO(rs.getInt("id"), rs.getString("code"), rs.getString("name"));
            }

            if (willCloseConnection) connection.close();
        } catch (Exception e) {
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }

        return country;
    }

}