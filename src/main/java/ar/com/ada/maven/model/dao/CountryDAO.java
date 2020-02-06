package ar.com.ada.maven.model.dao;

import ar.com.ada.maven.model.DBConnection;
import ar.com.ada.maven.model.dto.AccountTypeDTO;
import ar.com.ada.maven.model.dto.CountryDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CountryDAO {

    private Boolean willCloseConnection = true;

    public CountryDAO() {
    }

    public CountryDAO(Boolean willCloseConnection) {
        this.willCloseConnection = willCloseConnection;
    }


    public Collection<CountryDTO> findAll(int limit, int offset) {
        String sql = "SELECT * FROM Country LIMIT ? OFFSET ?";
        List<CountryDTO> countries = new ArrayList<>();
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

}