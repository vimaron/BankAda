package ar.com.ada.maven.model.dao;

import ar.com.ada.maven.model.DBConnection;
import ar.com.ada.maven.model.dto.*;

import java.sql.*;
import java.util.ArrayList;

public class TransactionDAO {
    //clase sin interface

    private Boolean willCloseConnection = true;

    private AccountDAO accountDAO = new AccountDAO(false);
    private TransactionTypeDAO transactionTypeDAO = new TransactionTypeDAO(false);

    public TransactionDAO() {
    }


    public TransactionDAO(Boolean willCloseConnection) {
        this.willCloseConnection = willCloseConnection;
    }

    public ArrayList<TransactionDTO> findAll() {
        String sql = "SELECT * FROM Transaction";
        ArrayList<TransactionDTO> transactions = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                AccountDTO account = accountDAO.findById(rs.getInt("Account_id"));
                TransactionTypeDTO transactionType = transactionTypeDAO.findById(rs.getInt("Transaction_Type"));
                TransactionDTO transaction = new TransactionDTO(rs.getInt("id"), rs.getDate("date"), rs.getDouble("amount"), account, transactionType);
                transactions.add(transaction);
            }
            connection.close();
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }
        return transactions;
    }

    public TransactionDTO findById(Integer id) {
        String sql = "SELECT * FROM Transaction WHERE id = ?";
        TransactionDTO transaction = null;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                AccountDTO account = accountDAO.findById(rs.getInt("Account_id"));
                TransactionTypeDTO transactionType = transactionTypeDAO.findById(rs.getInt("Transaction_Type"));
                transaction = new TransactionDTO(rs.getInt("id"), rs.getDate("date"), rs.getDouble("amount"), account, transactionType);
            } if (willCloseConnection)
                connection.close();
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            ex.printStackTrace();
            System.out.println("CONNECTION ERROR: " + ex.getMessage());
        }
        return transaction;
    }

    public Boolean save(TransactionDTO transactionDTO) {
        String sql = "INSERT INTO Transaction (date, amount, Account_id, Transaction_Type ) values (?, ?, ?, ?)";
        int affectatedRows = 0;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            Date date = new Date(transactionDTO.getDate().getTime());
            preparedStatement.setDate(1, date);
            preparedStatement.setDouble(2, transactionDTO.getAmount());
            preparedStatement.setInt(3, transactionDTO.getAccountID().getId());
            preparedStatement.setInt(4, transactionDTO.getTransactionTypeID().getId());
            affectatedRows = preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return affectatedRows == 1;
    }
}
