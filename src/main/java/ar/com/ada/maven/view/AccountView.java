package ar.com.ada.maven.view;

import ar.com.ada.maven.model.dto.AccountDTO;
import ar.com.ada.maven.utils.CommandLineTable;
import ar.com.ada.maven.utils.ScannerSingleton;

import java.util.List;
import java.util.Scanner;

public class AccountView {

    private Scanner keyboard = ScannerSingleton.getInstance();

    public Integer accountMenuSelectedOption() {
        System.out.println("\n+----------------------------------------+");
        System.out.println("\t\t BANK ADA APP :: Modulo Cuentas");
        System.out.println("+----------------------------------------+\n");

        System.out.println("Seleccione una acción del menú:\n");
        System.out.println("| 1 | Listar");
        System.out.println("| 2 | Agregar"); //preguntar si conviene mostrar agregar
        System.out.println("| 3 | Eliminar");
        System.out.println("| 4 | Regresar al menú principal");
        System.out.println("-------------------------\n");

        return keyboard.getInputInteger();

    }

    public String printCustomerPerPage(List<AccountDTO> accounts, List<String> paginator, String optionEdithOrDelete, boolean showHeader) {
        if (showHeader) {
            System.out.println("\n+----------------------------------------+");
            System.out.println("\t\t BANK ADA APP :: Modulo Cuentas :: Lista de Cuentas");
            System.out.println("+----------------------------------------+\n");
        }

        CommandLineTable st = new CommandLineTable();
        st.setShowVerticalLines(true);

        st.setHeaders("ID", "CUENTA", "CLIENTE");
        accounts.forEach(accountDTO ->
                st.addRow(accountDTO.getId().toStrng(), accountDTO.getNumber(), accountDTO.getBalance(), accountDTO.getIban(), accountDTO.getCustomerID(), accountDTO.getAccountTypeID())
        );
        st.print();

        if (optionEdithOrDelete != null && !optionEdithOrDelete.isEmpty())
            paginator.set(paginator.size() - 2, optionEdithOrDelete);

        System.out.println("\n+----------------------------------------+");
        paginator.forEach(page -> System.out.print(page + " "));
        System.out.println("\n+----------------------------------------+");

        return keyboard.getInputString();
    }

    public void choiceCustomerIdInfo() {
        System.out.println("Seleccione de la siguiente lista, el cliente al que pertenece la cuenta");
        keyboard.pressEnterkeyToContinue();
    }

    public void newAccountCanceled() {
        System.out.println("Se ha cancelado la creación de una nueva cuenta\n");
        keyboard.pressEnterKeyToContinue();
    }







}
