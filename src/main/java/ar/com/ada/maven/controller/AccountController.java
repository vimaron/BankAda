package ar.com.ada.maven.controller;

import ar.com.ada.maven.model.dao.AccountDAO;
import ar.com.ada.maven.model.dao.AccountTypeDAO;
import ar.com.ada.maven.model.dao.BranchDAO;
import ar.com.ada.maven.model.dao.CustomerDAO;
import ar.com.ada.maven.model.dto.AccountDTO;
import ar.com.ada.maven.utils.Paginator;
import ar.com.ada.maven.view.AccountView;
import ar.com.ada.maven.view.MainView;

import java.util.List;

public class AccountController {

    private static AccountView view = new AccountView();
    private static AccountDAO accountDAO = new AccountDAO(false);
    private static CustomerDAO customerDAO = new CustomerDAO(false);
    private static AccountTypeDAO accountTypeDAO = new AccountTypeDAO(false);
    private static BranchDAO branchDAO = new BranchDAO(false);

    static void init() {
        boolean shouldGetOut = false;

        while (!shouldGetOut) {
            String option = view.accountMenuSelectedOption();
            switch (option) {
                case 1:
                    listAllAccounts();
                    break;
                case 2:
                    createNewAccount();
                    break;
                case 3:
                    edithAccount();
                    break;
                case 4:
                    deleteAccount();
                    break;
                case 5:
                    shouldGetOut = true;
                    break;
                default:
                    MainView.chooseValidOption(); //revisar este metodo
            }
        }
    }

    private static void listAllAccounts() {
        listAccountsPerPage(null, true);
    }

    private static void createNewAccount() {

    }

    private static int listAccountsPerPage(String optionSelectEdithOrDelete, boolean showHeader) {
        int limit = 4, currentPage = 0, totalAccounts, totalPages, customerIdSelected = 0;
        List<AccountDTO> accounts;
        List<String> paginator;
        boolean shouldGetOut = false;

        while (!shouldGetOut) {
            totalAccounts = accountDAO.getTotalAccounts();
            totalPages = (int) Math.ceil((double) totalAccounts / limit);
            paginator = Paginator.buildPaginator(currentPage, totalPages);

            accounts = accountDAO.findAll(limit, currentPage * limit);
            String choice = view.printCustomerPerPage(accounts, paginator, optionSelectEdithOrDelete, showHeader);

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
                    } else MainView.chooseValidOption();
            }

        }
        return customerIdSelected;
    }



}






}