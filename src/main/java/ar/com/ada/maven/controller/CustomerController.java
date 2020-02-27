package ar.com.ada.maven.controller;

import ar.com.ada.maven.model.dao.CustomerDAO;
import ar.com.ada.maven.model.dto.CustomerDTO;
import ar.com.ada.maven.utils.Paginator;
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
                    break;
                case 3:
                    edithCustomer();
                    break;
                case 4:
                    deleteCustomer();
                    break;
                case 5:
                    bool = true;
                    break;
                default:
                    MainView.invalidData();
            }

        }
    }


    private static void customersList() {
        listCustomersPerPage(null, true);
    }

    private static void createNewCustomer() {
        String customerName = view.getNameNewContinent();
        String customerLastName = view.getNewLastName();
        String customerIdType = view.getNewIdType();
        Integer customerIdentification = view.getNewIdentification();

        if (!customerName.isEmpty() && !customerLastName.isEmpty() && !customerIdType.isEmpty()
                && customerIdentification != null) {

            CustomerDTO newCust = new CustomerDTO(customerName, customerLastName, customerIdType, customerIdentification);
            CustomerDTO byName = customerDAO.findByIdentification(customerIdentification);

            if (byName != null && byName.equals(newCust)) {
                view.customerAlreadyExists(newCust.getName());
            } else {
                Boolean isSaved = customerDAO.save(newCust);

                if (isSaved)
                    view.showNewCustomer(newCust.getName());
            }
        } else {
            view.newCustomerCanceled();
        }

    }

    private static void edithCustomer() {
        int customerIdToEdith = listCustomersPerPage(Paginator.EDITH, true);
        if (customerIdToEdith != 0)
            clientUpdate(customerIdToEdith);
        else
            view.updateCustomerCanceled();
    }

    private static void deleteCustomer() {
        String optionDelete = "[Eliminar]";

        int customerIdToDelete = listCustomersPerPage(Paginator.DELETE, true);

   
        if (customerIdToDelete != 0)
            deleteSelectedCustomer(customerIdToDelete);
        else
            view.updateCustomerCanceled();
    }

    public static void clientUpdate(int id){
        int update = view.selectCustomerUpdate();
        switch (update){
            case 1:
                updateName(id);
                break;
            case 2:
                updateLastName(id);
                break;
            case 3:
                updateIdentification(id);
                break;
            default:
                System.out.println("Se debe seleccionar una opcion valida");
        }
    }

    private static void updateName(int id){
        CustomerDTO customerById = customerDAO.findById(id);
        if (customerById !=null){
            view.getUpdate(customerById);
            String nameToUpdate = view.nameToUpdate(customerById);
            if (!nameToUpdate.isEmpty()){
                customerDAO.findByName(nameToUpdate);
                customerById.setName(nameToUpdate);

                Boolean isSaved = customerDAO.update(customerById, id);

                if (isSaved)
                    view.showUpdateCustomer(customerById.getName());

            } else
                view.updateCustomerCanceled();
        }
    }
    private static void updateLastName(int id){
        CustomerDTO customerById = customerDAO.findById(id);
        if (customerById !=null){
            view.getUpdate(customerById);
            String lastNameUpdate = view.lastNameToUpdate(customerById);
            if (!lastNameUpdate.isEmpty()){
                customerDAO.findByName(lastNameUpdate);
                customerById.setLastName(lastNameUpdate);

                Boolean isSaved = customerDAO.update(customerById, id);

                if (isSaved)
                    view.showUpdateCustomer(customerById.getLastName());

            } else
                view.updateCustomerCanceled();
        }

    }

    private static void updateIdentification(int id){
        CustomerDTO customerById = customerDAO.findById(id);
        if (customerById !=null){
            view.getUpdate(customerById);
            Integer identificationUpdate = view.identificationToUpdate(customerById);
            if (identificationUpdate != null){
            customerDAO.findById(identificationUpdate);
            customerById.setIdentification(identificationUpdate);

                Boolean isSaved = customerDAO.update(customerById, id);

                if (isSaved)
                    view.showUpdateCustomer(customerById.getLastName());

            } else
                view.updateCustomerCanceled();
        }
    }


    private static void deleteSelectedCustomer(int id) {
        CustomerDTO customer = customerDAO.findById(id);

        if (customer != null && customer.getAccounts().isEmpty()) {
            Integer toDelete = view.getResponseToDelete(customer);
            if (toDelete == 1) {

                Boolean isDelete = customerDAO.delete(id);

                if (isDelete)
                    view.showDeleteCustomer(customer.getName());
            } else
                view.deleteCustomerCanceled();
        } else {
            view.customerNotExist(id);
            int customerIdSelected = view.customerIdSelected("Eliminar");
            if (customerIdSelected != 0)
                deleteSelectedCustomer(customerIdSelected);
            else
                view.deleteCustomerCanceled();
        }
    }


    static int listCustomersPerPage(String optionSelectEdithOrDelete, boolean showHeader) {
        int limit = 4, currentPage = 0, totalCustomers, totalPages, customerIdSelected = 0;
        List<CustomerDTO> customers;
        List<String> paginator;
        boolean shouldGetOut = false;

        while (!shouldGetOut) {
            totalCustomers = customerDAO.getTotalCustomers(); //crear metodo en customerDAO
            totalPages = (int) Math.ceil((double) totalCustomers / limit);
            paginator = Paginator.buildPaginator(currentPage, totalPages); 

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
