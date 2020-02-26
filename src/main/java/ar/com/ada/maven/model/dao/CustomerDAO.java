package ar.com.ada.maven.model.dao;

import ar.com.ada.maven.model.DBConnection;
import ar.com.ada.maven.model.dto.AccountDTO;
import ar.com.ada.maven.model.dto.CustomerDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomerDAO implements Dao<CustomerDTO> {

    private AccountDAO accountDAO = new AccountDAO(false);
    private Boolean willCloseConnection = true;

    public CustomerDAO() {}

    public CustomerDAO (Boolean willCloseConnection) {
        this.willCloseConnection = willCloseConnection;
    }

    @Override
    public ArrayList<CustomerDTO> findAll() {
        String sql = "SELECT * FROM Customer";
        ArrayList<CustomerDTO> customers = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                List<AccountDTO> accounts = accountDAO.findByCustomerId(rs.getInt("id"));
                CustomerDTO customer = new CustomerDTO(rs.getInt("id"), rs.getString("name"), rs.getString("last_name"), rs.getString("identificationType"),  rs.getInt("identification"));
                customer.setAccounts(accounts);
                customers.add(customer);
            }
            connection.close();
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }
        return customers;
    }

    @Override
    public CustomerDTO findById(Integer id) {
        String sql = "SELECT * FROM Customer WHERE id = ?";
        CustomerDTO customer = null;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next())
                customer = new CustomerDTO(rs.getInt("id"), rs.getString("name"), rs.getString("last_name"), rs.getString("identification_type"), rs.getInt("identification"));
            if (willCloseConnection)
                connection.close();
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            ex.printStackTrace();
            System.out.println("CONNECTION ERROR: " + ex.getMessage());
        }
        return customer;
    }

    @Override
    public Boolean save(CustomerDTO customerDTO) {
        String sql = "INSERT INTO Customer (name, last_name, identification_type, identification) values (?,?,?,?)";
        int affectatedRows = 0;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, customerDTO.getName());
            preparedStatement.setString(2, customerDTO.getLastName());
            preparedStatement.setString(3, customerDTO.getIdentificationType());
            preparedStatement.setInt(4, customerDTO.getIdentification());
            affectatedRows = preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return affectatedRows == 1;
    }

    @Override
    public Boolean update(CustomerDTO customerDTO, Integer id) {
        String sql = "UPDATE Customer SET name = ?, last_name = ?, identification_type = ?, identification = ? WHERE Id = ?";
        int hasUpdate = 0;

        CustomerDTO customerDB = findById(id);

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, customerDTO.getName());
            preparedStatement.setString(2, customerDTO.getLastName());
            preparedStatement.setString(3, customerDTO.getIdentificationType());
            preparedStatement.setInt(4, customerDTO.getIdentification());

            if (!(customerDTO.getName().equals(customerDB.getName()) &&
                    customerDTO.getLastName().equals(customerDB.getLastName()) &&
                    customerDTO.getIdentificationType().equals(customerDB.getIdentificationType()) &&
                    customerDTO.getIdentification() == (customerDB.getIdentification())));
            hasUpdate = preparedStatement.executeUpdate();
            connection.close();
        } catch (Exception e) {
            System.out.println("CONNECTION ERROR Customer update: " + e.getMessage());
        }
        return hasUpdate == 1;
    }

    @Override
    public Boolean delete(Integer id) {
        String sql = "DELETE FROM Customer WHERE Id = ?";
        int hasDelete = 0;

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            hasDelete = preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("CONNECTION ERROR Customer delete: " + e.getMessage());
        }
        return hasDelete == 1;
    }

    public CustomerDTO findByName(String name) {
        String sql = "SELECT * FROM Customer WHERE name = ?";
        CustomerDTO customer = null;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next())
                customer = new CustomerDTO(rs.getInt("id"), rs.getString("name"));

            if (willCloseConnection) connection.close();
        } catch (Exception e) {
            System.out.println("CONNECTION ERROR: " + e.getMessage());

        }
        return customer;
    }

    public int getTotalCustomers() {
        String sql = "SELECT COUNT(*) AS total FROM Customer";
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
    public CustomerDTO findByIdentification(Integer identification) {
        String sql = "SELECT * FROM Customer WHERE identification = ?";
        CustomerDTO customer = null;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, identification);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next())
                customer = new CustomerDTO(rs.getInt("id"), rs.getInt("identification"));

            if (willCloseConnection) connection.close();
        } catch (Exception e) {
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }

        return customer;
    }

    public List<CustomerDTO> findAll(int limit, int offset) {
        String sql = "SELECT * FROM Customer LIMIT ? OFFSET ?";
        List<CustomerDTO> customers = new ArrayList<>();

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, offset);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                CustomerDTO customer = new CustomerDTO(rs.getInt("id"), rs.getString("name"), rs.getString("last_name"), rs.getString("identification_type"), rs.getInt("identification"));
                customers.add(customer);
            }
            connection.close();
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }

        return customers;
    }

}
