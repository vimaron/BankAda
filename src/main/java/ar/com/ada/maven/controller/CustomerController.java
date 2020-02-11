package ar.com.ada.maven.controller;

import ar.com.ada.maven.model.dao.CustomerDAO;
import ar.com.ada.maven.model.dto.CustomerDTO;
import ar.com.ada.maven.view.CustomerView;

import java.util.List;

public class CustomerController {
    private static CustomerView view = new CustomerView();
    private static CustomerDAO customerDAO = new CustomerDAO();

    public static void init(){
        boolean bool = false;
        while (!bool){
            int varWhile = view.customerMenuSelectOption();
            switch (varWhile){
                case 1:
                    customerList();
                    break;
                default:
                    System.out.println("Se debe seleccionar una opcion valida");
            }

        }
    }

    private static void customerList(){
        List<CustomerDTO> customerFindAll = customerDAO.findAll();
        view.printAllCustomers(customerFindAll);
    }
}
