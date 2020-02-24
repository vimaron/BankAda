package ar.com.ada.maven.view;

import ar.com.ada.maven.model.dto.AccountTypeDTO;
import ar.com.ada.maven.utils.Ansi;
import ar.com.ada.maven.utils.CommandLineTable;
import ar.com.ada.maven.utils.ScannerSingleton;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class AccountTypeView {
    private Scanner keyboard = ScannerSingleton.getInstance();

    public int accountTypeMenuSelectOption() {
        System.out.println("\n+--------------------------------------------------+");
        System.out.println("\t\t  Bank Ada App :: Modulo Tipo de cuenta");
        System.out.println("+--------------------------------------------------+\n");

        System.out.println("Seleccione una accion del menu:\n");
        System.out.println("| 1 | Listar");
        System.out.println("| 2 | Agregar");
        System.out.println("| 3 | Editar");
        System.out.println("| 4 | Eliminar");
        System.out.println("| 5 | Regresar el meno principal");
        System.out.println("-------------------------\n");

        return Integer.valueOf(ScannerSingleton.getInputInteger());

    }

    public String getNameNewAccountType() {
        System.out.println("\n+----------------------------------------------------------------+");
        System.out.println("\t  Bank Ada:: Modulo Tipo de Cuenta :: Nuevo Tipo de cuenta");
        System.out.println("+----------------------------------------------------------------+\n");

        System.out.print("Ingrese el nombre de la nueva tipo de cuenta ");
        System.out.println("(para cancelar, no ingresar datos y presionar enter):");
        System.out.println("-------------------------\n");

        Scanner keyboard = ScannerSingleton.getInstance();
        keyboard.nextLine();

        while (true) {
            try {
                System.out.print("? ");
                String name = keyboard.nextLine().trim();
                while (!name.matches("^[A-Za-záéíóúüÁÉÍÓÚÜ\\s]+$") && !name.isEmpty()) {
                    System.out.println("Error, debe ingresar un dato valido");
                    name = keyboard.nextLine();
                }
                return name;
            } catch (InputMismatchException e) {
                System.out.println("Error, debe ingresar un dato valido");
                keyboard.next();
            }
        }
    }

    public void showNewAccountType(String name) {
        System.out.println("El tipo de cuenta " + name + " se ha creado exitosamente");
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public void newAccountTypeCanceled() {
        System.out.println("Ha cancelado el ingreso de un nuevo tipo de cuenta\n");
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public void accountTypeAlreadyExists(String name) {
        System.out.println("Error al guardar, ya existe un tipo de cuenta con el nombre " + name);
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public String printAccountsTypePerPage(List<AccountTypeDTO> accountsType, List<String> paginator, String optionEdithOrDelete, boolean showHeader) {
        if (showHeader) {
            System.out.println("\n+----------------------------------------------------------------+");
            System.out.println("\t  Zoo Wolrd App :: Modulo Continente :: Lista Continente");
            System.out.println("+----------------------------------------------------------------+\n");
        }

        CommandLineTable st = new CommandLineTable();
        st.setShowVerticalLines(true);

        st.setHeaders("ID", "TIPO DE CUENTA");
        accountsType.forEach(accountTypeDTO ->
                st.addRow(accountTypeDTO.getId().toString(), accountTypeDTO.getName())
        );
        st.print();

        if (optionEdithOrDelete != null && !optionEdithOrDelete.isEmpty())
            paginator.set(paginator.size() - 2, optionEdithOrDelete);

        System.out.println("\n+----------------------------------------------------------------+");
        paginator.forEach(page -> System.out.print(page + " "));
        System.out.println("\n+----------------------------------------------------------------+\n");

        while (true) {
            try {
                System.out.print("? ");
                String name = keyboard.nextLine().trim();
                while (!name.matches("^[0-9IiAaSsUuEeqQ]+$") && !name.isEmpty()) {
                    MainView.chooseValidOption();
                    System.out.print("? ");
                    name = keyboard.nextLine();
                }
                return name;
            } catch (InputMismatchException e) {
                MainView.chooseValidOption();
                keyboard.next();
            }
        }
    }

    public int accountTypeIdSelected(String actionOption) {
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
        System.out.println("Ingrese el numero de ID del tipo de cuenta para " + actionOption + " ó 0 para cancelar: \n");

        while (true) {
            try {
                System.out.print("? ");
                int choice = keyboard.nextInt();
                return choice;
            } catch (InputMismatchException e) {
                System.out.println("Error, debe ingresar un id valido");
                keyboard.next();
            }
        }
    }

    public static String getNameToUpdate(AccountTypeDTO accountTypeDTO) {
        System.out.print("Se actualizará el nombre del siguiente tipo de cuenta: ");
        System.out.println(Ansi.PURPLE + accountTypeDTO.getId() + "\t" + accountTypeDTO.getName() + Ansi.RESET);

        System.out.print("Ingrese el nombre del continente para actualizar ");
        System.out.println("(para cancelar, no ingresar datos y presionar enter):\n");

        Scanner keyboard = ScannerSingleton.getInstance();
        keyboard.nextLine();

        while (true) {
            try {
                System.out.print("? ");
                String name = keyboard.nextLine().trim();
                while (!name.matches("^[A-Za-záéíóúüÁÉÍÓÚÜ\\s]+$") && !name.isEmpty()) {
                    System.out.println("Error, debe ingresar un dato valido");
                    name = keyboard.nextLine();
                }
                return name;
            } catch (InputMismatchException e) {
                System.out.println("Error, debe ingresar un dato valido");
                keyboard.next();
            }
        }
    }

    public void accountTypeNotExist(int id) {
        System.out.println("No existe un tipo de cuenta con el id " + id + " asociado");
        System.out.println("Selecciones un ID valido o 0 para cancelar");
    }

    public void updateAccountTypeCanceled() {
        System.out.println("Ha cancelado la actualizacion del Continente\n");
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public void showUpdateAccountType(String name) {
        System.out.println("El continente " + name + " se ha actualizado exitosamente");
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public static Boolean getResponseToDelete(AccountTypeDTO accountTypeDTO) {
        System.out.print("Se Eliminará el siguiente tipo de cuenta: ");
        System.out.println(Ansi.PURPLE + accountTypeDTO.getId() + " " + accountTypeDTO.getName() + Ansi.RESET);

        System.out.println("Esta seguro que desea eliminarla? ");
        System.out.println("| 1 | Si");
        System.out.println("| 2 | No");

        Scanner keyboard = ScannerSingleton.getInstance();
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

    public void deleteAccountTypeCanceled() {
        System.out.println("Ha cancelado la eliminacion del tipo de cuenta\n");
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public void showDeleteAccountType(String name) {
        System.out.println("El tipo de cuenta " + name + " se ha eliminado exitosamente");
        ScannerSingleton.pressEnterKeyToContinue();
    }
}

