package ar.com.ada.maven.model.dao;

import ar.com.ada.maven.model.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AccountTypeDAO implements Dao<AccountTypeDTO> {

    private Boolean willCloseConnection = true;

    public AccountTypeDAO() {}

    public AccountTypeDAO(Boolean willCloseConnection) {this.willCloseConnection = willCloseConnection;}

    @Override
    public List<AccountTypeDTO> findAll(int limit, int offset) {
        String sql = "SELECT * FROM account_type LIMIT ? OFFSET ?";
        List<AccountTypeDTO> accountTypes = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, offset);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                AccountTypeDTO accountType = new AccountTypeDTO(rs.getInt("id"), rs.getString("name"));
                accountTypes.add(accountType);
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }

        return accountTypes;
    }

    @Override
    public AccountTypeDTO findById(Integer id) {
        String sql = "SELECT * FROM account_type WHERE id = ?";
        AccountTypeDTO accountType = null;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next())
                accountType = new AccountTypeDTO(rs.getInt("id"), rs.getString("name"));
            if (willCloseConnection)
                connection.close();
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            ex.printStackTrace();
            System.out.println("CONNECTION ERROR: " + ex.getMessage());
        }
        return accountType;
    }

    @Override
    public Boolean save(AccountTypeDTO accountTypeDTO) {
        String sql = "INSERT INTO account_type (name) values (?)";
        int affectatedRows = 0;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, accountTypeDTO.getName());
            affectatedRows = preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return affectatedRows == 1;
    }

    @Override
    public Boolean update(AccountTypeDTO accountTypeDTO, Integer id) {
        String sql = "UPDATE account_type SET name = ? WHERE Id = ?";
        int hasUpdate = 0;

        //para comparar el objeto que quiero actualizar con la base de datos.
        AccountTypeDTO accountTypeDB = findById(id);

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, accountTypeDTO.getName());
            preparedStatement.setInt(2, id);

            if (!accountTypeDTO.getName().equals(accountTypeDB.getName()))
                hasUpdate = preparedStatement.executeUpdate();
            connection.close();
        } catch (Exception e) {
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }
        return hasUpdate == 1;
    }

    @Override
    public Boolean delete(Integer id) {
        String sql = "DELETE FROM account_type WHERE Id = ?";
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
