package ar.com.ada.maven.model.dao;

import ar.com.ada.maven.model.DBConnection;
import ar.com.ada.maven.model.dto.CustomerDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class CustomerDAO implements Dao<CustomerDTO> {

    private Boolean willCloseConnection = true;

    private CustomerDAO customerDAO = new CustomerDAO(false);

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
                CustomerDTO customer = new CustomerDTO(rs.getInt("id"), rs.getString("name"), rs.getString("last_name"), rs.getString("identificationType"),  rs.getInt("identification"));
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


}
