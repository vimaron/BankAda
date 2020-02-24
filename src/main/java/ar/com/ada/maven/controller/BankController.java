package ar.com.ada.maven.controller;

import ar.com.ada.maven.model.dao.BankDAO;
import ar.com.ada.maven.model.dao.CountryDAO;
import ar.com.ada.maven.model.dto.BankDTO;
import ar.com.ada.maven.model.dto.CountryDTO;
import ar.com.ada.maven.utils.Paginator;
import ar.com.ada.maven.view.BankView;
import ar.com.ada.maven.view.MainView;

import java.util.List;

public class BankController {
    private static BankView view = new BankView();
    private static BankDAO bankDAO = new BankDAO(false);
    private static CountryDAO countryDAO = new CountryDAO(false);

    static void init() {
        boolean shouldGetOut = false;

        while (!shouldGetOut) {
            Integer option = view.bankMenuSelectedOption();
            switch (option) {
                case 1:
                    listAllBanks();
                    break;
                case 2:
                    createNewBank();
                    break;
                case 3:
                    updateBank();
                case 4:
                    deleteBank();
                    break;
                case 5:
                    shouldGetOut = true;
                    break;
                default:
                    MainView.chooseValidOption(); //revisar este metodo
            }
        }
    }

    private static void listAllBanks() {
        listBanksPerPage(null, true);
    }

    private static void createNewBank() {
        String bankCode = view.getCode();
        String bankName = view.getBankName();

        view.choiceCountryIdInfo();

        Integer countryId = CountryController.listCountriesPerPage(Paginator.SELECT, false);


        if (countryId != 0 ) {

            BankDTO bankByCode = bankDAO.findbByCode(bankCode);
            CountryDTO countryById = countryDAO.findById(countryId);

            BankDTO newBank = new BankDTO(bankCode, bankName, countryById);

            if (bankByCode != null && bankByCode.equals(newBank)) {
                view.bankAlreadyExists(newBank.getName());
            } else {
                Boolean isSaved = bankDAO.save(newBank);

                if (isSaved)
                    view.showNewBank(newBank.getName());
            }
        } else {
            view.newBankCanceled();
        }

    }

    static int listBanksPerPage(String optionSelectEdithOrDelete, boolean showHeader) {
        int limit = 4, currentPage = 0, totalBanks, totalPages, countryIdSelected = 0;
        List<BankDTO> banks;
        List<String> paginator;
        boolean shouldGetOut = false;

        while (!shouldGetOut) {
            totalBanks = bankDAO.getTotalBanks();
            totalPages = (int) Math.ceil((double) totalBanks / limit);
            paginator = Paginator.buildPaginator(currentPage, totalPages);

            banks = bankDAO.findAll(limit, currentPage * limit);
            String choice = view.printBanksPerPage(banks, paginator, optionSelectEdithOrDelete, showHeader);

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
                        countryIdSelected = view.countryIdSelected(optionSelectEdithOrDelete);
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
        return countryIdSelected;
    }

    private static void updateBank() {
        int bankIdToEdith = listBanksPerPage(Paginator.EDITH, true);
        if (bankIdToEdith != 0)
            updateSelectedBank(bankIdToEdith);
        else
            view.updateBankCanceled();
    }

    private static void updateSelectedBank(int id) {
        BankDTO bankById = bankDAO.findById(id);
        if (bankById != null) {
            String nameToUpdate = view.getNameToUpdate(bankById);
            if (!nameToUpdate.isEmpty()) {
                bankDAO.findByName(nameToUpdate);
                bankById.setName(nameToUpdate);

                Boolean isSaved = bankDAO.update(bankDAO, id);

                if (isSaved)
                    view.showUpdateBank(bankById);
            } else
                view.updateBankCanceled();
        } else {
            view.bankNotExist(id);
            int bankIdSelected = view.bankIdSelected("Editar");
            if (bankIdSelected != 0)
                updateSelectedBank(bankIdSelected);
            else
                view.updateBankCanceled();
        }
    }

    private static BankDTO getBankToDelete(String optionDelete) {
        boolean hasExitWhile = false;
        BankDTO  bankToDelete = null;

        String actionInfo = Paginator.EDITH.equals(optionDelete) ? "Eliminar";

        view.selectBankIdToEdithOrDeleteInfo(actionInfo);

        int bankIdToDelete = listBanksPerPage(optionDelete, true);

        if (bankIdToDelete != 0) {
            while (!hasExitWhile) {
                bankToDelete = bankDAO.findById(bankIdToDelete);
                if (bankToDelete == null) {
                    view.bankNotExist(bankIdToDelete);
                    bankIdToDelete = view.countryIdSelected(optionDelete);
                    hasExitWhile = (bankIdToDelete == 0);
                } else {
                    hasExitWhile = true;
                }
            }
        }

        return bankToDelete;
    }

    private static void deleteBank() {
        BankDTO bankToDelete = getBankToDelete(Paginator.DELETE);

        if (bankToDelete != null) {
            Boolean toDelete = view.getResponseToDelete(bankToDelete);
            if (toDelete) {
                Boolean isDelete = bankDAO.delete(bankToDelete.getId());

                if (isDelete)
                    view.showDeleteBank(bankToDelete.getName());
            }
        } else {
            view.deleteBankCanceled();
        }
    }

}
