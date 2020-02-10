package ar.com.ada.maven.model.dao;

import ar.com.ada.maven.model.DBConnection;
import ar.com.ada.maven.model.dto.AccountDTO;
import ar.com.ada.maven.model.dto.CustomerDTO;
import ar.com.ada.maven.model.dto.TransactionDTO;
import ar.com.ada.maven.model.dto.TransactionTypeDTO;

import java.sql.*;
import java.util.ArrayList;

public class TransactionTypeDAO {

    private Boolean willCloseConnection = true;

    public TransactionTypeDAO() {
    }

    public TransactionTypeDAO(Boolean willCloseConnection) {
        this.willCloseConnection = willCloseConnection;
    }

    public ArrayList<TransactionTypeDTO> findAll() {
        String sql = "SELECT * FROM Transaction_type";
        ArrayList<TransactionTypeDTO> transactionTypes = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                TransactionTypeDTO transactionType = new TransactionTypeDTO(rs.getInt("id"), rs.getString("name"));
                transactionTypes.add(transactionType);
            }
            connection.close();
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }
        return transactionTypes;
    }

    public TransactionTypeDTO findById(Integer id) {
        String sql = "SELECT * FROM Transaction_type WHERE id = ?";
        TransactionTypeDTO transactionType = null;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next())
            transactionType = new TransactionTypeDTO(rs.getInt("id"), rs.getString("name"));
            if (willCloseConnection)
                connection.close();
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            ex.printStackTrace();
            System.out.println("CONNECTION ERROR: " + ex.getMessage());
        }
        return transactionType;
    }

    public Boolean save(TransactionTypeDTO transactionTypeDTO) {
        String sql = "INSERT INTO Transaction_type (name) values (?)";
        int affectatedRows = 0;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, transactionTypeDTO.getName());
            affectatedRows = preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return affectatedRows == 1;
    }

}
