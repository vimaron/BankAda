package ar.com.ada.maven.view;

import ar.com.ada.maven.model.dto.AccountDTO;
import ar.com.ada.maven.utils.Ansi;
import ar.com.ada.maven.utils.CommandLineTable;
import ar.com.ada.maven.utils.Paginator;
import ar.com.ada.maven.utils.ScannerSingleton;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class AccountView {

    private Scanner keyboard = ScannerSingleton.getInstance();

    public Integer accountMenuSelectedOption() {
        System.out.println("\n+----------------------------------------+");
        System.out.println("\t\t BANK ADA APP :: Modulo Cuentas");
        System.out.println("+----------------------------------------+\n");

        System.out.println("Seleccione una acción del menú:\n");
        System.out.println("| 1 | Listar cuentas");
        System.out.println("| 2 | Agregar nueva cuenta"); //preguntar si conviene mostrar agregar
        System.out.println("| 3 | Eliminar cuenta");
        System.out.println("| 4 | Agregar nueva transaccciòn de cuenta");
        System.out.println("| 5 | Regresar al menú principal");
        System.out.println("-------------------------\n");

        return Integer.valueOf(ScannerSingleton.getInputInteger());

    }

    public String printCustomerPerPage(List<AccountDTO> accounts, List<String> paginator, String optionEdithOrDelete, boolean showHeader) {
        if (showHeader) {
            System.out.println("\n+----------------------------------------+");
            System.out.println("\t\t BANK ADA APP :: Modulo Cuentas :: Lista de Cuentas");
            System.out.println("+----------------------------------------+\n");
        }

        CommandLineTable st = new CommandLineTable();
        st.setShowVerticalLines(true);

        st.setHeaders("ID", "CUENTA", "SALDO", "IBAN", "CLIENTE", "TIPO DE CUENTA", "SUCURSAL");
        accounts.forEach(accountDTO ->

                st.addRow(
                        accountDTO.getId().toString(),
                        accountDTO.getNumber(),
                        String.valueOf(accountDTO.getBalance()),
                        accountDTO.getIban(),
                        accountDTO.getCustomerID().toString(),
                        accountDTO.getAccountTypeID().toString(),
                        accountDTO.getBranchID().toString())

        );
        st.print();

        if (optionEdithOrDelete != null && !optionEdithOrDelete.isEmpty())
            paginator.set(paginator.size() - 2, optionEdithOrDelete);

        System.out.println("\n+----------------------------------------+");
        paginator.forEach(page -> System.out.print(page + " "));
        System.out.println("\n+----------------------------------------+");

        return ScannerSingleton.getInputString();
    }

    public void choiceCustomerIdInfo() {
        System.out.println("Seleccione de la siguiente lista, el cliente al que pertenece la cuenta");
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public void choiceAccountTypeIdInfo() {
        System.out.println("Seleccione de la siguiente lista, el Id del tipo de cuenta al que pertenece la cuenta");
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public void choiceBranchIdInfo() {
        System.out.println("Seleccione de la siguiente lista, el Id de la sucursal al que pertenece la cuenta");
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public void choiceBranchCodeInfo() {
        System.out.println("Escriba de la siguiente lista, el codigo de la sucursal al que pertenece la cuenta");
        ScannerSingleton.pressEnterKeyToContinue();
    }
    public void choiceBankCodeInfo() {
        System.out.println("escriba de la siguiente lista, el código del banco al que pertenece la cuenta");
        ScannerSingleton.pressEnterKeyToContinue();
    }


    public void newAccountCanceled() {
        System.out.println("Se ha cancelado la creación de una nueva cuenta\n");
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public void accountAlreadyExists(String iban) {
        System.out.println(Ansi.RED);
        System.out.println("Error al crear cuenta, ya existe una cuenta con este còdigo " + iban);
        System.out.println(Ansi.RESET);
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public void showNewAccount(String iban) {
        System.out.println(Ansi.GREEN);
        System.out.println("La cuenta se ha creado exitosamente :: CODE IBAN : " + iban);
        System.out.println(Ansi.RESET);
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public void accountNotExist(int id) {
        System.out.println("No existe una cuenta con este id " + id + " asociado");
        System.out.println("Selecciones un ID valido o 0 para cancelar");
    }

    public void customerNotExist(int id) {
        System.out.println("No existe un continente con el id " + id + " asociado");
        System.out.println("Selecciones un ID valido o 0 para cancelar");
    }

    public void accountTypeNotExist(int id) {
        System.out.println("No existe un tipo de cuenta con el id " + id + " asociado");
        System.out.println("Selecciones un ID valido o 0 para cancelar");
    }

    public void branchNotExist(int id) {
        System.out.println("No existe una sucursal con el id " + id + " asociado");
        System.out.println("Selecciones un ID valido o 0 para cancelar");
    }

    public static void selectAccountIdToEdithOrDeleteInfo(String actions) {
        System.out.println("De la siguiente lista de cuentas, seleccione el id para  " + actions);
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public static void selectCustomerIdToEdithInfo(String actions) {
        System.out.println("De la siguiente lista de clientes, seleccione el id para  " + actions);
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public Integer customerIdSelected(String actionOption) {
        switch (actionOption) {
            case Paginator.EDITH:
                actionOption = "editar";
                break;
            case Paginator.DELETE:
                actionOption = "eliminar";
                break;
            case Paginator.SELECT:
                actionOption = "elejir";
                break;
        }
        System.out.println("Ingrese el numero de ID del cliente para " + actionOption + " ó 0 para cancelar: \n");

        return Integer.valueOf( ScannerSingleton.getInputInteger());
    }

    public Boolean getResponseToDelete(AccountDTO account) {
        System.out.print("Se Eliminará la siguiente cuenta: ");
        System.out.println(Ansi.PURPLE + account.getId() + " " + account.getIban() + " " + account.getCustomerID().getName() + Ansi.RESET);


        System.out.println("¿Esta seguro que desea eliminarla? ");
        System.out.println("| 1 | Sí");
        System.out.println("| 2 | No");

        keyboard.nextLine();

        while (true) {
            try {
                System.out.print("? ");
                String iban = keyboard.nextLine().trim();
                while (!iban.matches("^[1-2]+$") && !iban.isEmpty()) {
                    System.out.println("Error, debe ingresar una opcion valida");
                    iban = keyboard.nextLine();
                }
                return "1".equals(iban);
            } catch (InputMismatchException e) {
                System.out.println("Error, debe ingresar una opción valida");
                keyboard.next();
            }
        }

    }

    public void showDeleteAccount (String iban) {
        System.out.println("La cuenta " + iban + " se ha eliminado exitosamente");
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public void deleteAccountCanceled() {
        System.out.println("Ha cancelado la eliminacion de la cuenta");
        ScannerSingleton.pressEnterKeyToContinue();
    }
}
