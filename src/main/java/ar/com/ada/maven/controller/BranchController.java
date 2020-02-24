package ar.com.ada.maven.controller;

import ar.com.ada.maven.model.dao.BankDAO;
import ar.com.ada.maven.model.dao.BranchDAO;
import ar.com.ada.maven.model.dto.BankDTO;
import ar.com.ada.maven.model.dto.BranchDTO;
import ar.com.ada.maven.utils.Paginator;
import ar.com.ada.maven.view.BranchView;
import ar.com.ada.maven.view.MainView;

import java.util.List;

public class BranchController {

    private static BranchView view = new BranchView();
    private static BranchDAO branchDAO = new BranchDAO(false);
    private static BankDAO bankDAO = new BankDAO(false);

    static void init() {
        boolean shouldGetOut = false;

        while (!shouldGetOut) {
            Integer option = view.branchMenuSelectedOption();
            switch (option) {
                case 1:
                    listAllBranchs();
                    break;
                case 2:
                    createNewBranch();
                    break;
                case 3:
                    updateBranch();
                case 4:
                    deleteBranch();
                    break;
                case 5:
                    shouldGetOut = true;
                    break;
                default:
                    MainView.chooseValidOption(); //revisar este metodo
            }
        }
    }

    private static void listAllBranchs() {
        listBranchsPerPage(null, true);
    }
    private static void createNewBranch() {
        String branchIdentificationCode = view.getIdentificationCode();
        String branchName = view.getBranchName();

        view.choiceBankIdInfo();

        Integer bankId = BankController.listBanksPerPage(Paginator.SELECT, false);


        if (bankId != 0 ) {

            BranchDTO branchByIdentificationCode = branchDAO.findbByIdentificationCode(branchIdentificationCode);
            BankDTO bankById = bankDAO.findById(bankId);

            BranchDTO newBranch = new BranchDTO(branchIdentificationCode, branchName, bankById);

            if (branchByIdentificationCode != null && branchByIdentificationCode.equals(newBranch)) {
                view.branchAlreadyExists(newBranch.getName());
            } else {
                Boolean isSaved = branchDAO.save(newBranch);

                if (isSaved)
                    view.showNewBranch(newBranch.getName());
            }
        } else {
            view.newBranchCanceled();
        }

    }

    static int listBranchsPerPage(String optionSelectEdithOrDelete, boolean showHeader) {
        int limit = 4, currentPage = 0, totalBranchs, totalPages, bankIdSelected = 0;
        List<BranchDTO> branchs;
        List<String> paginator;
        boolean shouldGetOut = false;

        while (!shouldGetOut) {
            totalBranchs = branchDAO.getTotalBranchs();
            totalPages = (int) Math.ceil((double) totalBranchs / limit);
            paginator = Paginator.buildPaginator(currentPage, totalPages);

            branchs = branchDAO.findAll(limit, currentPage * limit);
            String choice = view.printBranchsPerPage(branchs, paginator, optionSelectEdithOrDelete, showHeader);

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
                        bankIdSelected = view.bankIdSelected(optionSelectEdithOrDelete);
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
        return bankIdSelected;
    }

    private static BranchDTO getBranchToDelete(String optionDelete) {
        boolean hasExitWhile = false;
        BranchDTO  branchToDelete = null;

        String actionInfo = Paginator.EDITH.equals(optionDelete) ? "Eliminar" : "Editar";

        view.selectBranchIdToEdithOrDeleteInfo(actionInfo);

        int branchIdToDelete = listBranchsPerPage(optionDelete, true);

        if (branchIdToDelete != 0) {
            while (!hasExitWhile) {
                branchToDelete = branchDAO.findById(branchIdToDelete);
                if (branchToDelete == null) {
                    view.branchNotExist(branchIdToDelete);
                    branchIdToDelete = view.bankIdSelected(optionDelete);
                    hasExitWhile = (branchIdToDelete == 0);
                } else {
                    hasExitWhile = true;
                }
            }
        }

        return branchToDelete;
    }

    private static void updateBranch() {
        int branchIdToEdith = listBranchsPerPage(Paginator.EDITH, true);
        if (branchIdToEdith != 0)
            updateSelectedBranch(branchIdToEdith);
        else
            view.updateBranchCanceled();
    }
    private static void updateSelectedBranch(int id) {
        BranchDTO branchById = branchDAO.findById(id);
        if (branchById != null) {
            String nameToUpdate = view.getNameToUpdate(branchById);
            if (!nameToUpdate.isEmpty()) {
                branchDAO.findByName(nameToUpdate);
                branchById.setName(nameToUpdate);

                Boolean isSaved = branchDAO.update(branchById, id);

                if (isSaved)
                    view.showUpdateBranch(branchById);
            } else
                view.updateBranchCanceled();
        } else {
            view.branchNotExist(id);
            int branchIdSelected = view.branchIdSelected("Editar");
            if (branchIdSelected != 0)
                updateSelectedBranch(branchIdSelected);
            else
                view.updateBranchCanceled();
        }
    }
    private static void deleteBranch() {
        BranchDTO branchToDelete = getBranchToDelete(Paginator.DELETE);

        if (branchToDelete != null) {
            Boolean toDelete = view.getResponseToDelete(branchToDelete);
            if (toDelete) {
                Boolean isDelete = branchDAO.delete(branchToDelete.getId());

                if (isDelete)
                    view.showDeleteBranch(branchToDelete.getName());
            }
        } else {
            view.deleteBranchCanceled();
        }
    }


}
