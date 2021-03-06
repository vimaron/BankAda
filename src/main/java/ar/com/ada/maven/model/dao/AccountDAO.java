package ar.com.ada.maven.model.dao;

import ar.com.ada.maven.model.DBConnection;
import ar.com.ada.maven.model.dto.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class AccountDAO implements Dao<AccountDTO> {

    private Boolean willCloseConnection = true;

    private CustomerDAO customerDAO = new CustomerDAO(false);
    private AccountTypeDAO accountTypeDAO = new AccountTypeDAO(false);
    private BranchDAO branchDAO = new BranchDAO(false);

    public AccountDAO() {}

    public AccountDAO(Boolean willCloseConnection) {this.willCloseConnection = willCloseConnection;}

    @Override
    public ArrayList<AccountDTO> findAll() {
        String sql = "SELECT * FROM Account";
        ArrayList<AccountDTO> accounts = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                CustomerDTO customer = customerDAO.findById(rs.getInt("Customer_id"));
                AccountTypeDTO accountType = accountTypeDAO.findById(rs.getInt("Account_type_id"));
                BranchDTO branch = branchDAO.findById(rs.getInt("Branch_id"));
                AccountDTO account = new AccountDTO(rs.getInt("id"), rs.getInt("number"), rs.getDouble("balance"), rs.getString("iban"), customer, accountType, branch);
                accounts.add(account);
            }
            connection.close();
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }
        return accounts;
    }

    @Override
    public AccountDTO findById(Integer id) {
        String sql = "SELECT * FROM Account WHERE Id = ?";
        AccountDTO account = null;

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                CustomerDTO customer = customerDAO.findById(rs.getInt("Customer_id"));
                AccountTypeDTO accountType = accountTypeDAO.findById(rs.getInt("Account_type_id"));
                BranchDTO branch = branchDAO.findById(rs.getInt("Branch_id"));
                account = new AccountDTO(rs.getInt("id"), rs.getInt("number"), rs.getDouble("balance"), rs.getString("iban"), customer, accountType, branch);
            } if (willCloseConnection)
                connection.close();
        } catch (Exception e) {
            System.out.println("CONNECTION ERROR ANIMAL findById : " + e.getMessage());
        }
        return account;
    }

    @Override
    public Boolean save(AccountDTO accountDTO) {
        String sql = "INSERT INTO Account (number, balance, iban, Customer_id, Account_type_id, Branch_id) VALUES (?, ?, ?, ?,?,?)";
        int affectedRows = 0;

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountDTO.getNumber());
            preparedStatement.setDouble(2, accountDTO.getBalance());
            preparedStatement.setString(3, accountDTO.getIban());
            preparedStatement.setInt(4, accountDTO.getCustomerID().getId());
            preparedStatement.setInt(accountDTO.getAccountTypeID().getId());
            preparedStatement.setInt(accountDTO.getBranchID().getId());
            affectedRows = preparedStatement.executeUpdate();
            connection.close();
        } catch (Exception e) {
            System.out.println("CONNECTION ERROR ACCOUNT save : " + e.getMessage());
        }
        return affectedRows == 1;
    }

    @Override
    public Boolean update(AccountDTO accountDTO, Integer id) {
        String sql = "UPDATE Account SET number = ?, balance = ?, iban = ?, Customer_id = ?, Account_type_id = ?, Branch_id = ? WHERE Id = ?";
        int hasUpdate = 0;

        AccountDTO accountDB = findById(id);

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountDTO.getNumber());
            preparedStatement.setDouble(2, accountDTO.getBalance());
            preparedStatement.setString(3, accountDTO.getIban());
            preparedStatement.setInt(4, accountDTO.getCustomerID().getId());
            preparedStatement.setInt(accountDTO.getAccountTypeID().getId());
            preparedStatement.setInt(accountDTO.getBranchID().getId());

            if (!(accountDTO.getNumber().equals(accountDB.getNumber()) && accountDTO.getBalance().equals(accountDB.getBalance()) &&
                    accountDTO.getIban().equals(accountDB.getIban()) && accountDTO.getCustomerID().equals(accountDB.getCustomerID()) &&
                    accountDTO.getAccountTypeID().equals(accountDB.getAccountTypeID()) && accountDTO.getBranchID().equals(accountDB.getBranchID())));
            hasUpdate = preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("CONNECTION ERROR ACCOUNT update : " + e.getMessage());
        }

        return hasUpdate == 1;
    }

    @Override
    public Boolean delete(Integer id) {
        String sql = "DELETE FROM Account WHERE Id = ?";
        int hasDelete = 0;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            hasDelete = preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("CONNECTION ERROR ACCOUNT delete : " + e.getMessage());
        }
        return hasDelete == 1;
    }
}
