package ar.com.ada.maven.controller;

import ar.com.ada.maven.model.dao.AccountTypeDAO;
import ar.com.ada.maven.model.dto.AccountTypeDTO;
import ar.com.ada.maven.utils.Paginator;
import ar.com.ada.maven.view.AccountTypeView;
import ar.com.ada.maven.view.MainView;

import java.util.List;

public class AccountTypeController {

    private static AccountTypeView view = new AccountTypeView();
    private static AccountTypeDAO accountTypeDAO = new AccountTypeDAO(false);

    static void init() {
        boolean shouldGetOut = false;

        while (!shouldGetOut) {
            Integer option = view.accountTypeMenuSelectOption();
            switch (option) {
                case 1:
                    listAllAccountsType();
                    break;
                case 2:
                    createNewAccountType();
                    break;
                case 3:
                    deleteAccountType();
                    break;
                case 5:
                    shouldGetOut = true;
                    break;
                default:
                    MainView.chooseValidOption(); //revisar este metodo
            }
        }
    }

    private static void listAllAccountsType() {
        listAccountsTypePerPage(null, true);
    }

    private static void createNewAccountType() {
        String nameNewAccountType = view.getNameNewAccountType();

        if (!nameNewAccountType.isEmpty()) {

            AccountTypeDTO newAccountType = new AccountTypeDTO(nameNewAccountType);
            AccountTypeDTO byName = accountTypeDAO.findByName(nameNewAccountType);

            if (byName != null && byName.equals(newAccountType)) {
                view.accountTypeAlreadyExists(newAccountType.getName());
            } else {
                Boolean isSaved = accountTypeDAO.save(newAccountType);
                if (isSaved)
                    view.showNewAccountType(newAccountType.getName());
            }
        } else {
            view.newAccountTypeCanceled();
        }
    }

    static int listAccountsTypePerPage(String optionSelectEdithOrDelete, boolean showHeader) {
        int limit = 4, currentPage = 0, totalAccountsType, totalPages, accountTypeIdSelected = 0;
        List<AccountTypeDTO> accountsType;
        List<String> paginator;
        boolean shouldGetOut = false;

        while (!shouldGetOut) {
            totalAccountsType = accountTypeDAO.getTotalAccountsType();
            totalPages = (int) Math.ceil((double) totalAccountsType / limit);
            paginator = Paginator.buildPaginator(currentPage, totalPages);

            accountsType = accountTypeDAO.findAll(limit, currentPage * limit);
            String choice = view.printAccountsTypePerPage(accountsType, paginator, optionSelectEdithOrDelete, showHeader);

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
                        accountTypeIdSelected = view.accountTypeIdSelected(optionSelectEdithOrDelete);
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
        return accountTypeIdSelected;
    }
    private static void editSelectedAccountType(int id) {
        AccountTypeDTO accountTypeById = accountTypeDAO.findById(id);
        if (accountTypeById != null) {
            String nameToUpdate = view.getNameToUpdate(accountTypeById);
            if (!nameToUpdate.isEmpty()) {
                accountTypeDAO.findByName(nameToUpdate);
                accountTypeById.setName(nameToUpdate);

                Boolean isSaved = accountTypeDAO.update(accountTypeById, id);

                if (isSaved)
                    view.showUpdateAccountType(accountTypeById.getName());
            } else
                view.updateAccountTypeCanceled();
        } else {
            view.accountTypeNotExist(id);
            int accountTypeIdSelected = view.accountTypeIdSelected("Editar");
            if (accountTypeIdSelected != 0)
                editSelectedAccountType(accountTypeIdSelected);
            else
                view.updateAccountTypeCanceled();
        }
    }

    private static void deleteAccountType() {
        AccountTypeDTO accountTypeToDelete = getAccountTypeToDelete(Paginator.DELETE);

        if (accountTypeToDelete != null) {
            Boolean toDelete = view.getResponseToDelete(accountTypeToDelete);
            if (toDelete) {
                Boolean isDelete = accountTypeDAO.delete(accountTypeToDelete.getId());

                if (isDelete)
                    view.showDeleteAccountType(accountTypeToDelete.getName());
            }
        } else {
            view.deleteAccountTypeCanceled();
        }
    }

    private static AccountTypeDTO getAccountTypeToDelete(String optionDelete) {
        boolean hasExitWhile = false;
        AccountTypeDTO  accountTypeToDelete = null;

        String actionInfo = Paginator.EDITH.equals(optionDelete) ? "Eliminar": "Editar";

        view.selectAccountTypeIdToEdithOrDeleteInfo(actionInfo);

        int accountTypeIdToDelete = listAccountsTypePerPage(optionDelete, true);

        if (accountTypeIdToDelete != 0) {
            while (!hasExitWhile) {
                accountTypeToDelete = accountTypeDAO.findById(accountTypeIdToDelete);
                if (accountTypeToDelete == null) {
                    view.accountTypeNotExist(accountTypeIdToDelete);
                    accountTypeIdToDelete = view.accountTypeIdSelected(optionDelete);
                    hasExitWhile = (accountTypeIdToDelete == 0);
                } else {
                    hasExitWhile = true;
                }
            }
        }

        return accountTypeToDelete;
    }


}
