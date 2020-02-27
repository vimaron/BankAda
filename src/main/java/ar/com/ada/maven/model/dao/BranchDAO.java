package ar.com.ada.maven.model.dao;


import ar.com.ada.maven.model.DBConnection;
import ar.com.ada.maven.model.dto.BankDTO;
import ar.com.ada.maven.model.dto.BranchDTO;

import java.sql.*;
import java.util.ArrayList;


public class BranchDAO {
    private Boolean willCloseConection = true;

    private BankDAO bankDAO = new BankDAO(false);

    public BranchDAO(){}
    public BranchDAO(Boolean willCloseConection){this.willCloseConection=willCloseConection;}



    public ArrayList<BranchDTO> findAll() {
        String sql = "SELECT * FROM  Branch";
        ArrayList<BranchDTO> branchs = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs =statement.executeQuery(sql);
            while (rs.next()){
                BankDTO bank = bankDAO.findById(rs.getInt("Bank_id"));
                BranchDTO branch = new BranchDTO(rs.getInt("id"), rs.getString("identificationCode"),
                        rs.getString("name"), bank);
                branchs.add(branch);
            }
            connection.close();
        } catch (InstantiationException |SQLException |IllegalAccessException| ClassNotFoundException e) {
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }
        return branchs;
    }


    public BranchDTO findById(Integer id) {
        String sql = "SELECT * FROM Branch WHERE id = ?";
        BranchDTO branch = null;

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                BankDTO bank = bankDAO.findById(rs.getInt("Bank_id"));
                branch = new BranchDTO(rs.getInt("id"), rs.getString("identification_code"),
                        rs.getString("name"), bank);
            } if (willCloseConection){
                connection.close();
            }
        } catch (Exception e){
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }
        return branch;
    }

    public Boolean save(BranchDTO branch) {
        String sql = "INSERT INTO Branch (identification_Code, name, Bank_id) VALUES (?,?,?)";
        int hasInsert = 0;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, branch.getIdentificationCode());
            preparedStatement.setString(2, branch.getName());
            preparedStatement.setInt(3, branch.getBankID().getId());
            hasInsert = preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }
        return hasInsert == 1;
    }

    public Boolean update(BranchDTO branch, Integer id) {
        String sql = "UPDATE Branch SET Identification_Code = ?, name = ?, Bank_id = ? WHERE id = ?";
        int hasUpdate = 0;

        BranchDTO branchDB = findById(branch.getId());

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, branch.getIdentificationCode());
            preparedStatement.setString(2, branch.getName());
            preparedStatement.setInt(3, branch.getBankID().getId());

            if (!branch.equals(branchDB))
                hasUpdate = preparedStatement.executeUpdate();

            connection.close();
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }

        return hasUpdate == 1;
    }

    public Boolean delete(Integer id) {
        String sql = "DELETE FROM Branch WHERE id = ?";
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

    public int getTotalBranchs() {
        String sql = "SELECT COUNT(*) AS total FROM Branch";
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

    public ArrayList<BranchDTO> findAll(int limit, int offset) {
        String sql = "SELECT * FROM Branch LIMIT ? OFFSET ?";
        ArrayList<BranchDTO> branchs = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, offset);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                BankDTO bank = bankDAO.findById(rs.getInt("Bank_id"));
                BranchDTO branch = new BranchDTO(rs.getInt("id"), rs.getString("identification_Code"), rs.getString("name"), bank);
                branchs.add(branch);
            }
            connection.close();
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }
        return branchs;
    }

    public BranchDTO findByIdentificationCode(String identificationCode) {
        String sql = "SELECT * FROM Branch WHERE identification_code = ?";
        BranchDTO branch = null;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, identificationCode);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                BankDTO bank = bankDAO.findById(rs.getInt("Bank_id"));
                branch = new BranchDTO(rs.getInt("id"), rs.getString("identification_code"), rs.getString("name"), bank);
            }

            if (willCloseConection) connection.close();
        } catch (Exception e) {
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }

        return branch;
    }

    public BranchDTO findByName(String name) {
        String sql = "SELECT * FROM Branch WHERE name = ?";
        BranchDTO branch = null;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                BankDTO bank = bankDAO.findById(rs.getInt("Bank_Id"));
                branch = new BranchDTO(rs.getInt("id"), rs.getString("identification_Code"), rs.getString("name"), bank);
            }
            if (willCloseConection) connection.close();
        } catch (Exception e) {
            System.out.println("CONNECTION ERROR: " + e.getMessage());

        }
        return branch;
    }
}


