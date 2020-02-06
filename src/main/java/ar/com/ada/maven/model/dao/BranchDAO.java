package ar.com.ada.maven.model.dao;

import ar.com.ada.maven.model.DBConnection;
import ar.com.ada.maven.model.dto.AccountDTO;
import ar.com.ada.maven.model.dto.BankDTO;
import ar.com.ada.maven.model.dto.BranchDTO;
import com.sun.imageio.plugins.bmp.BMPMetadataFormatResources;

import javax.print.DocFlavor;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class BranchDAO {
    private Boolean willCloseConection = true;

    private BankDAO bankDAO = new BankDAO(false);

    public BranchDAO() {
    }

    public BranchDAO(Boolean willCloseConection) {
        this.willCloseConection = willCloseConection;
    }


    public ArrayList<BranchDTO> findAll() {
        String sql = "SELECT * FROM  Branch";
        ArrayList<BranchDTO> branchs = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                BankDTO bank = bankDAO.findById(rs.getInt("Bank_id"));
                BranchDTO branch = new BranchDTO(rs.getInt("id"), rs.getString("identificationCode"),
                        rs.getString("name"), bank);
                branchs.add(branch);
            }
            connection.close();
        } catch (InstantiationException | SQLException | IllegalAccessException | ClassNotFoundException e) {
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }
        return branchs;
    }

    public BranchDTO findById(Integer id) {
        String sql = "SELECT FROM Branch WHERE id = ?";
        BranchDTO branch = null;

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                BankDTO bank = bankDAO.findById(rs.getInt("Bank_id"));
                branch = new BranchDTO(rs.getInt("id"), rs.getString("identificationCode"),
                        rs.getString("name"), bank);
            }
            if (willCloseConection) {
                connection.close();
            }
        } catch (Exception e) {
            System.out.println("CONNECTION ERROR: " + e.getMessage());
        }
        return branch;
    }

