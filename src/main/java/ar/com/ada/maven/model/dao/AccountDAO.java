package ar.com.ada.maven.model.dao;

import ar.com.ada.maven.model.DBConnection;
import ar.com.ada.maven.model.dto.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AccountDAO implements Dao<AccountDTO> {

    private Boolean willCloseConnection = true;

    private CustomerDAO customerDAO = new CustomerDAO(false);
    private AccountTypeDAO accountTypeDAO = new AccountTypeDAO(false);
    private BranchDAO branchDAO = new BranchDAO(false);

    public AccountDAO() {}

    public AccountDAO(Boolean willCloseConnection) {this.willCloseConnection = willCloseConnection;}

    public ArrayList<AccountDTO> findAll(int limit, int offset) {
        String sql = "SELECT * FROM Account LIMIT ? OFFSET ?";
        ArrayList<AccountDTO> accounts = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, offset);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                CustomerDTO customer = customerDAO.findById(rs.getInt("Customer_id"));
                AccountTypeDTO accountType = accountTypeDAO.findById(rs.getInt("Account_type_id"));
                BranchDTO branch = branchDAO.findById(rs.getInt("Branch_id"));
                AccountDTO account = new AccountDTO(rs.getInt("id"), rs.getString("number"), rs.getDouble("balance"), rs.getString("iban"), customer, accountType, branch);
                accounts.add(account);
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }

        return accounts;
    }

    public AccountDTO getLastAccount() {
        String sql = "SELECT * FROM Account ORDER BY id DESC limit 1";
        AccountDTO account = null;
        try {
            Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                account = new AccountDTO(rs.getInt("id"), rs.getString("number"), rs.getDouble("balance"), rs.getString("iban"));
            } if (willCloseConnection)
                connection.close();
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }
        return account;

    }


    public int getTotalAccounts() {
        String sql = "SELECT COUNT(*) AS total FROM Account";
        int total = 0;
        try {
            Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) total = rs.getInt("total");
            connection.close();
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }
        return total;
    }

    public AccountDTO findByIban(String iban) {
        String sql = "SELECT * FROM Account WHERE iban = ?";
        AccountDTO account = null;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, iban);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                CustomerDTO customer = customerDAO.findById(rs.getInt("Customer_id"));
                AccountTypeDTO accountType = accountTypeDAO.findById(rs.getInt("Account_type_id"));
                BranchDTO branch = branchDAO.findById(rs.getInt("Branch_id"));
                account = new AccountDTO(rs.getInt("id"), rs.getString("number"), rs.getDouble("balance"), rs.getString("iban"), customer, accountType, branch);
            }

            if (willCloseConnection) connection.close();
        } catch (Exception e) {
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }

        return account;
    }


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
                AccountDTO account = new AccountDTO(rs.getInt("id"), rs.getString("number"), rs.getDouble("balance"), rs.getString("iban"), customer, accountType, branch);
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
                account = new AccountDTO(rs.getInt("id"), rs.getString("number"), rs.getDouble("balance"), rs.getString("iban"), customer, accountType, branch);
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
            preparedStatement.setString(1, accountDTO.getNumber());
            preparedStatement.setDouble(2, accountDTO.getBalance());
            preparedStatement.setString(3, accountDTO.getIban());
            preparedStatement.setInt(4, accountDTO.getCustomerID().getId());
            preparedStatement.setInt(5, accountDTO.getAccountTypeID().getId());
            preparedStatement.setInt(6, accountDTO.getBranchID().getId());
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
            preparedStatement.setString(1, accountDTO.getNumber());
            preparedStatement.setDouble(2, accountDTO.getBalance());
            preparedStatement.setString(3, accountDTO.getIban());
            preparedStatement.setInt(4, accountDTO.getCustomerID().getId());
            preparedStatement.setInt(5, accountDTO.getAccountTypeID().getId());
            preparedStatement.setInt(6, accountDTO.getBranchID().getId());

            if (!(accountDTO.getNumber() == (accountDB.getNumber()) && accountDTO.getBalance() == (accountDB.getBalance()) &&
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
    
    public ArrayList<AccountDTO> findByCustomerId(int customerId) {
        String sql = "SELECT * FROM Account WHERE Customer_id = ?";
        ArrayList<AccountDTO> accounts = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customerId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                // con el campo Customer_id busco el cliente con el dao de Customer
                CustomerDTO customer = customerDAO.findById(rs.getInt("Customer_id"));
                AccountDTO account = new AccountDTO(rs.getInt("id"), rs.getString("number"), customer);
                accounts.add(account);
            }
            connection.close();
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }
        return accounts;
    }
}
