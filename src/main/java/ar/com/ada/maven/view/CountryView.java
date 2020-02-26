package ar.com.ada.maven.view;

import ar.com.ada.maven.model.dto.CountryDTO;
import ar.com.ada.maven.utils.Ansi;
import ar.com.ada.maven.utils.CommandLineTable;
import ar.com.ada.maven.utils.ScannerSingleton;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CountryView {

    private Scanner keyboard = ScannerSingleton.getInstance();

    public Integer countryMenuSelectedOption() {
        System.out.println("\n+--------------------------------------------------+");
        System.out.println("\t\t  Bank Ada app :: Modulo de Países");
        System.out.println("+--------------------------------------------------+\n");

        System.out.println("Seleccione una accion del menu:\n");
        System.out.println("| 1 | Listar");
        System.out.println("| 2 | Agregar");
        System.out.println("| 3 | Editar");
        System.out.println("| 4 | Eliminar");
        System.out.println("| 5 | Regresar el menu principal");
        System.out.println("-------------------------\n");

        return Integer.valueOf(ScannerSingleton.getInputInteger());
    }

    public String printCountriesPerPage(List<CountryDTO> countries, List<String> paginator, String optionEdithOrDelete, boolean showHeader) {
        if (showHeader) {
            System.out.println("\n+----------------------------------------------------------------+");
            System.out.println("\t  Bank Ada app :: Modulo de Países :: Lista de Países");
            System.out.println("+----------------------------------------------------------------+\n");
        }

        CommandLineTable st = new CommandLineTable();
        st.setShowVerticalLines(true);

        st.setHeaders("ID", "CÓDIGO", "PAÍS");
        countries.forEach(country ->
                st.addRow(country.getId().toString(), country.getCode(), country.getName())
        );
        st.print();

        if (optionEdithOrDelete != null && !optionEdithOrDelete.isEmpty())
            paginator.set(paginator.size() - 2, optionEdithOrDelete);

        System.out.println("\n+----------------------------------------------------------------+");
        paginator.forEach(page -> System.out.print(page + " "));
        System.out.println("\n+----------------------------------------------------------------+\n");

        return ScannerSingleton.getInputString();
    }

    public String getNameNewCountry() {
        System.out.println("\n+----------------------------------------------------------------+");
        System.out.println("\t  Bank Ada app :: Modulo de Países :: Nuevo Pais");
        System.out.println("+----------------------------------------------------------------+\n");

        System.out.print("Ingrese el nombre del nuevo Pais ");
        System.out.println("(para cancelar, no ingresar datos y presionar enter):");
        System.out.println("-------------------------\n");

        return ScannerSingleton.getInputString();
    }


    public void newCountryCanceled() {
        System.out.println("Ha cancelado el ingreso de un nuevo Pais\n");
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public void countryAlreadyExists(String name) {
        System.out.println(Ansi.RED);
        System.out.println("Error al guardar, ya existe un pais con el nombre " + name);
        System.out.println(Ansi.RESET);
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public void showNewCountry(String name) {
        System.out.println(Ansi.GREEN);
        System.out.println("El pais " + name + " se ha creado exitosamente");
        System.out.println(Ansi.RESET);
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public void updateCountryCanceled() {
        System.out.println("Ha cancelado la actualizacion del Pais\n");
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public void countryNotExist(int id) {
        System.out.println("No existe un pais con el id " + id + " asociado");
        System.out.println("Selecciones un ID valido o 0 para cancelar");
    }

    public String getNameToUpdate(CountryDTO country) {
        System.out.print("Se actualizará el nombre del siguiente paìs: ");
        System.out.println(Ansi.PURPLE + country.getId() + " " + country.getCode() + " " + country.getName() + Ansi.RESET);

        System.out.print("Ingrese el nuevo nombre del pais para actualizar ");
        System.out.println("(para cancelar, no ingresar datos y presionar enter):\n");

        return ScannerSingleton.getInputString();
    }
    public Integer countryIdSelected(String actionOption) {
        switch (actionOption) {
            case "[" + Ansi.CYAN + "E" + Ansi.RESET + "ditar]":
                actionOption = "editar";
                break;
            case "[" + Ansi.CYAN + "E" + Ansi.RESET + "liminar]":
                actionOption = "eliminar";
                break;
            case "[" + Ansi.CYAN + "E" + Ansi.RESET + "lejir]":
                actionOption = "elejir";
                break;
        }
        System.out.println("Ingrese el numero de ID del país para " + actionOption + " ó 0 para cancelar: \n");

        return Integer.valueOf(ScannerSingleton.getInputString());
    }

    public static void selectCountryIdToEdithOrDeleteInfo(String actions) {
        System.out.println("De la siguiente lista de paises, seleccione el id para  " + actions);
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public void showUpdateCountry(CountryDTO country) {
        System.out.println("El pais " + country.getCode() + " (" + country.getName() + ") se ha actualizado exitosamente");
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public Boolean getResponseToDelete(CountryDTO country) {
        System.out.print("Se Eliminará el siguiente pais: ");
        System.out.println(Ansi.PURPLE + country.getId() + " " + country.getCode() + " " + country.getName() + Ansi.RESET);


        System.out.println("Esta seguro que desea eliminarlo? ");
        System.out.println("| 1 | Si");
        System.out.println("| 2 | No");

        keyboard.nextLine();

        while (true) {
            try {
                System.out.print("? ");
                String name = keyboard.nextLine().trim();
                while (!name.matches("^[1-2]+$") && !name.isEmpty()) {
                    System.out.println("Error, debe ingresar una opcion valida");
                    name = keyboard.nextLine();
                }
                return "1".equals(name);
            } catch (InputMismatchException e) {
                System.out.println("Error, debe ingresar una opcion valida");
                keyboard.next();
            }
        }
    }

    public void showDeleteCountry(String name) {
        System.out.println("El Pais " + name + " se ha eliminado exitosamente");
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public void deleteCountryCanceled() {
        System.out.println("Ha cancelado la eliminacion del Pais");
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public String getCode(){
        System.out.println("Ingresar el código de país: ");
        return String.valueOf( ScannerSingleton.getInputString());
    }
    public String getCountryName(){
        System.out.println("Ingresar el nombre del país: ");
        return String.valueOf( ScannerSingleton.getInputString());
    }
}
