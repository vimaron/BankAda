package ar.com.ada.maven.model.dao;

import ar.com.ada.maven.model.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

public class CustomerDAO implements Dao<CustomerDTO> {

    private Boolean willCloseConnection = true;

    private CustomerDAO customerDAO = new CustomerDAO(false);

    public CustomerDAO() {}

    public CountryDAO (Boolean willCloseConnection) {
        this.willCloseConnection = willCloseConnection;
    }

    @Override
    public ArrayList<CustomerDTO> findAll() {
        String sql = "SELECT * FROM Country";
        ArrayList<CustomerDTO> customers = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                CustomerDTO customer = customerDAO.findById(rs.getInt("id_Continent"));
                CountryDTO country = new CountryDTO(rs.getInt("id"), rs.getString("name"), rs.getInt("iso_cod"), continent);
                countries.add(country);
            }
            connection.close();
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }
        return countries;
    }

    @Override
    public CustomerDTO findById(Integer id) {
        return null;
    }

    @Override
    public Boolean save(CustomerDTO customerDTO) {
        return null;
    }

    @Override
    public Boolean update(CustomerDTO customerDTO, Integer id) {
        return null;
    }

    @Override
    public Boolean delete(Integer id) {
        return null;
    }
}
