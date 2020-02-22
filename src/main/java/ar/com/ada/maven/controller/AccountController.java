package ar.com.ada.maven.controller;

import ar.com.ada.maven.model.dao.AccountDAO;
import ar.com.ada.maven.model.dao.AccountTypeDAO;
import ar.com.ada.maven.model.dao.BranchDAO;
import ar.com.ada.maven.model.dao.CustomerDAO;
import ar.com.ada.maven.model.dto.AccountDTO;
import ar.com.ada.maven.model.dto.AccountTypeDTO;
import ar.com.ada.maven.model.dto.BranchDTO;
import ar.com.ada.maven.model.dto.CustomerDTO;
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
            Integer option = view.accountMenuSelectedOption();
            switch (option) {
                case 1:
                    listAllAccounts();
                    break;
                case 2:
                    createNewAccount();
                    break;
                case 3:
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
        String newNumber;
        String preIban = "";
        String dc1 = "00";
        String dc = "00";
        AccountDTO account = accountDAO.getLastAccount();
        if (account == null) {
            newNumber = "0000000000";
        } else {
            newNumber = String.valueOf(Integer.valueOf(account.getNumber()) + 1);

            newNumber = AccountController.padLeftZeros(newNumber, 10);
        }

        preIban = account.getBranchID().getBankID().getCountryID().getCode() +
                account.getBranchID().getBankID().getCountryID().getCode() + dc +
                        account.getBranchID().getBankID().getCode() +
                        account.getBranchID().getIdentificationCode() + dc1 + newNumber;
        
            view.choiceCustomerIdInfo();

            Integer customerId = CustomerController.listCustomersPerPage(Paginator.SELECT, false);
            Integer accountTypeId = AccountTypeController.listAccountsTypeControllerPerPage(Paginator.SELECT, false);
            Integer branchId = BranchController.listBranchsPerPage(Paginator.SELECT, false);

            if (customerId != 0 && accountTypeId != 0 && branchId != 0) {
                AccountDTO accountByIban = accountDAO.findByIban(preIban);
                CustomerDTO continentById = customerDAO.findById(customerId);
                AccountTypeDTO accountTypeById = accountTypeDAO.findById(accountTypeId);
                BranchDTO branchById = branchDAO.findById(branchId);

                AccountDTO newAccount = new AccountDTO(newNumber, 0.0, preIban, continentById, accountTypeById, branchById);

                if (accountByIban != null && accountByIban.equals(newAccount)) {
                    view.accountAlreadyExists(newAccount.getIban());
                } else {
                    Boolean isSaved = accountDAO.save(newAccount);
                    if (isSaved)
                        view.showNewAccount(newAccount.getIban());
                }
            } else {
                view.newAccountCanceled();
            }
        } else {
            view.newAccountCanceled();
        }
    }


    public static int listAccountsPerPage(String optionSelectEdithOrDelete, boolean showHeader) {
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

    private static AccountDTO getAccountToDelete(String optionDelete) {
        boolean hasExitWhile = false;
        AccountDTO  accountToDelete = null;

        String actionInfo = Paginator.EDITH.equals(optionDelete) ? "Eliminar";

        view.selectAccountIdToEdithOrDeleteInfo(actionInfo);

        int accountIdToDelete = listAccountsPerPage(optionDelete, true);

        if (accountIdToDelete != 0) {
            while (!hasExitWhile) {
                accountToDelete = accountDAO.findById(accountIdToDelete);
                if (accountToDelete == null) {
                    view.accountNotExist(accountIdToDelete);
                    accountIdToDelete = view.customerIdSelected(optionDelete);
                    hasExitWhile = (accountIdToDelete == 0);
                } else {
                    hasExitWhile = true;
                }
            }
        }

        return accountToDelete;
    }

    private static void deleteAccount() {
        AccountDTO accountToDelete = getAccountToDelete(Paginator.DELETE);

        if (accountToDelete != null) {
            Boolean toDelete = view.getResponseToDelete(accountToDelete);
            if (toDelete) {
                Boolean isDelete = accountDAO.delete(accountToDelete.getId());

                if (isDelete)
                    view.showDeleteAccount(accountToDelete.getIban());
            }
        } else {
            view.deleteAccountCanceled();
        }
    }

    public static String padLeftZeros(String inputString, int length) {
        if (inputString.length() >= length) {
            return inputString;
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - inputString.length()) {
            sb.append('0');
        }
        sb.append(inputString);

        return sb.toString();
    }



}



