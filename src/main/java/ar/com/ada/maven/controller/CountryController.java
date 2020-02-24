package ar.com.ada.maven.controller;

import ar.com.ada.maven.model.dao.CountryDAO;
import ar.com.ada.maven.model.dto.CountryDTO;
import ar.com.ada.maven.utils.Paginator;
import ar.com.ada.maven.view.CountryView;
import ar.com.ada.maven.view.MainView;

import java.util.List;

public class CountryController {
    private static CountryView view = new CountryView();
    private static CountryDAO countryDAO = new CountryDAO(false);


    static void init() {
        boolean shouldGetOut = false;

        while (!shouldGetOut) {
            Integer option = view.countryMenuSelectedOption();
            switch (option) {
                case 1:
                    listAllCountries();
                    break;
                case 2:
                    createNewCountry();
                    break;
                case 3:
                    updateCountry();
                case 4:
                    deleteCountry();
                    break;
                case 5:
                    shouldGetOut = true;
                    break;
                default:
                    MainView.chooseValidOption(); //revisar este metodo
            }
        }
    }

    private static void listAllCountries() {
        listCountriesPerPage(null, true);
    }
    private static void createNewCountry() {
        String countryCode = view.getCode();
        String countryName = view.getCountryName();

        if (!countryCode.isEmpty() && !countryName.isEmpty()) {

            CountryDTO newCountry = new CountryDTO(countryCode, countryName);
            CountryDTO byName = countryDAO.findByName(countryName);

            if (byName != null && byName.equals(newCountry)) {
                view.countryAlreadyExists(newCountry.getName());
            } else {
                Boolean isSaved = countryDAO.save(newCountry);

                if (isSaved)
                    view.showNewCountry(newCountry.getName());
            }
        } else {
            view.newCountryCanceled();
        }

    }

    private static Integer listCountriesPerPage(String optionSelectEdithOrDelete, boolean showHeader) {
        int limit = 4, currentPage = 0, totalCountries, totalPages, countryIdSelected = 0;
        List<CountryDTO> countries;
        List<String> paginator;
        boolean shouldGetOut = false;

        while (!shouldGetOut) {
            totalCountries = countryDAO.getTotalCountries();
            totalPages = (int) Math.ceil((double) totalCountries / limit);
            paginator = Paginator.buildPaginator(currentPage, totalPages);

            countries = countryDAO.findAll(limit, currentPage * limit);
            String choice = view.printCountriesPerPage(countries, paginator, optionSelectEdithOrDelete, showHeader);

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

    private static CountryDTO getCountryToEdithOrDelete(String optionEdithOrDelete) {
        boolean hasExitWhile = false;
        CountryDTO  countryToEdithOrDelete = null;

        String actionInfo = Paginator.EDITH.equals(optionEdithOrDelete) ? "Eliminar";

        view.selectCountryIdToEdithOrDeleteInfo(actionInfo);

        int countryIdToEdith = listCountriesPerPage(optionEdithOrDelete, true);

        if (countryIdToEdith != 0) {
            while (!hasExitWhile) {
                countryToEdithOrDelete = countryDAO.findById(countryIdToEdith);
                if (countryToEdithOrDelete == null) {
                    view.countryNotExist(countryIdToEdith);
                    countryIdToEdith = view.countryIdSelected(optionEdithOrDelete);
                    hasExitWhile = (countryIdToEdith == 0);
                } else {
                    hasExitWhile = true;
                }
            }
        }

        return countryToEdithOrDelete;
    }

    private static void updateCountry() {
        int countryIdToEdith = listCountriesPerPage(Paginator.EDITH, true);
        if (countryIdToEdith != 0)
            updateSelectedCountry(countryIdToEdith);
        else
            view.updateCountryCanceled();
    }
    private static void updateSelectedCountry(int id) {
        CountryDTO countryById = countryDAO.findById(id);
        if (countryById != null) {
            String nameToUpdate = view.getNameToUpdate(countryById);
            if (!nameToUpdate.isEmpty()) {
                countryDAO.findByName(nameToUpdate);
                countryById.setName(nameToUpdate);

                Boolean isSaved = countryDAO.update(countryById, id);

                if (isSaved)
                    view.showUpdateCountry(countryById.getName());
            } else
                view.updateCountryCanceled();
        } else {
            view.countryNotExist(id);
            int continentIdSelected = view.countryIdSelected("Editar");
            if (continentIdSelected != 0)
                updateSelectedCountry(continentIdSelected);
            else
                view.updateCountryCanceled();
        }
    }

    private static void deleteCountry() {
        CountryDTO countryToDelete = getCountryToEdithOrDelete(Paginator.DELETE);

        if (countryToDelete != null) {
            Boolean toDelete = view.getResponseToDelete(countryToDelete);
            if (toDelete) {
                Boolean isDeleted = countryDAO.delete(countryToDelete.getId());

                if (isDeleted)
                    view.showDeleteCountry(countryToDelete.getName());
            }

        } else {
            view.deleteCountryCanceled();
        }
    }
}
