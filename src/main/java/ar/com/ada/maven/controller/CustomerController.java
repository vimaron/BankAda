package ar.com.ada.maven.controller;

import ar.com.ada.maven.model.dao.CustomerDAO;
import ar.com.ada.maven.model.dto.CustomerDTO;
import ar.com.ada.maven.view.CustomerView;
import ar.com.ada.maven.view.MainView;

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
                    customersList();
                    break;
                case 2:
                    createNewCustomer();
                default:
                    System.out.println("Se debe seleccionar una opcion valida");
            }

        }
    }


    private static void customersList() {
        listCustomersPerPage(null, true);
    }

    static int listCustomersPerPage(String optionSelectEdithOrDelete, boolean showHeader) {
        int limit = 4, currentPage = 0, totalCustomers, totalPages, customerIdSelected = 0;
        List<CustomerDTO> customers;
        List<String> paginator;
        boolean shouldGetOut = false;

        while (!shouldGetOut) {
            totalCustomers = customerDAO.getTotalCustomers(); //crear metodo en customerDAO
            totalPages = (int) Math.ceil((double) totalCustomers / limit);
            paginator = Paginator.buildPaginator(currentPage, totalPages); //importar cambios de develor e importar clase

            customers = customerDAO.findAll(limit, currentPage * limit);
            String choice = view.printCustomersPerPage(customers, paginator, optionSelectEdithOrDelete, showHeader);

            switch (choice) {
                case "i":
                case "I":
                    currentPage = 0;
                    break;
                case "a":
                case "A":
                    if (currentPage > 0) currentPage--;
                    break;
                case "s":
                case "S":
                    if (currentPage + 1 < totalPages) currentPage++;
                    break;
                case "u":
                case "U":
                    currentPage = totalPages - 1;
                    break;
                case "e":
                case "E":
                    if (optionSelectEdithOrDelete != null) {
                        customerIdSelected = view.customerIdSelected(optionSelectEdithOrDelete);
                        shouldGetOut = true;
                    }
                    break;
                case "q":
                case "Q":
                    shouldGetOut = true;
                    break;
                default:
                    if (choice.matches("^-?\\d+$")) {
                        int page = Integer.parseInt(choice);
                        if (page > 0 && page <= totalPages) currentPage = page - 1;
                    } else MainView.invalidData();
            }
        }
        return customerIdSelected;
    }

}
