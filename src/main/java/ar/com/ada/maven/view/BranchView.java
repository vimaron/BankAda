package ar.com.ada.maven.view;

import ar.com.ada.maven.model.dto.BranchDTO;
import ar.com.ada.maven.utils.Ansi;
import ar.com.ada.maven.utils.CommandLineTable;
import ar.com.ada.maven.utils.Paginator;
import ar.com.ada.maven.utils.ScannerSingleton;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class BranchView {
    private Scanner keyboard = ScannerSingleton.getInstance();

    public Integer branchMenuSelectedOption() {
        System.out.println("\n+----------------------------------------+");
        System.out.println("\t\t BANK ADA APP :: Modulo Sucursales");
        System.out.println("+----------------------------------------+\n");

        System.out.println("Seleccione una acción del menú:\n");
        System.out.println("| 1 | Listar");
        System.out.println("| 2 | Agregar"); //preguntar si conviene mostrar agregar
        System.out.println("| 3 | Eliminar");
        System.out.println("| 4 | Regresar al menú principal");
        System.out.println("-------------------------\n");

        return Integer.valueOf(ScannerSingleton.getInputInteger());

    }

    public String printBranchsPerPage(List<BranchDTO> branchs, List<String> paginator, String optionEdithOrDelete, boolean showHeader) {
        if (showHeader) {
            System.out.println("\n+----------------------------------------+");
            System.out.println("\t\t BANK ADA APP :: Modulo Sucursales :: Lista de Sucursales");
            System.out.println("+----------------------------------------+\n");
        }

        CommandLineTable st = new CommandLineTable();
        st.setShowVerticalLines(true);

        st.setHeaders("ID", "CODIGO", "SUCURSAL", "BANCO");
        branchs.forEach(branchDTO ->
                st.addRow(branchDTO.getId().toString(), branchDTO.getIdentificationCode(), branchDTO.getName(), "SANTANDER")
        );
        st.print();

        if (optionEdithOrDelete != null && !optionEdithOrDelete.isEmpty())
            paginator.set(paginator.size() - 2, optionEdithOrDelete);

        System.out.println("\n+----------------------------------------+");
        paginator.forEach(page -> System.out.print(page + " "));
        System.out.println("\n+----------------------------------------+");

        return ScannerSingleton.getInputString();
    }

    public void choiceBankIdInfo() {
        System.out.println("Seleccione de la siguiente lista, el banco al que pertenece la sucursal");
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public void newBranchCanceled() {
        System.out.println("Se ha cancelado la creación de una nueva sucursal\n");
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public void branchAlreadyExists(String identificationCode) {
        System.out.println(Ansi.RED);
        System.out.println("Error al crear cuenta, ya existe una sucursal con este còdigo " + identificationCode);
        System.out.println(Ansi.RESET);
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public void showNewBranch(String name) {
        System.out.println(Ansi.GREEN);
        System.out.println("La cuenta se ha creado exitosamente" + name);
        System.out.println(Ansi.RESET);
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public void updateBranchCanceled() {
        System.out.println("Ha cancelado la actualizacion de la sucursal\n");
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public void branchNotExist(int id) {
        System.out.println("No existe una sucursal con este id " + id + " asociado");
        System.out.println("Selecciones un ID valido o 0 para cancelar");
    }

    public void bankNotExist(int id) {
        System.out.println("No existe un banco con el id " + id + " asociado");
        System.out.println("Selecciones un ID valido o 0 para cancelar");
    }

    public String getNameToUpdate(BranchDTO branch) {
        System.out.print("Se actualizará el nombre de la siguiente sucursal: ");
        System.out.println(Ansi.PURPLE + branch.getId() + " " + branch.getIdentificationCode() + " " + branch.getName() + Ansi.RESET);

        System.out.print("Ingrese el nuevo nombre de la sucursal para actualizar ");
        System.out.println("(para cancelar, no ingresar datos y presionar enter):\n");

        return ScannerSingleton.getInputString();
    }


    public static void selectBranchIdToEdithOrDeleteInfo(String actions) {
        System.out.println("De la siguiente lista de sucursales, seleccione el id para  " + actions);
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public static void selectBankIdToEdithInfo(String actions) {
        System.out.println("De la siguiente lista de bsncos, seleccione el id para  " + actions);
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public void showUpdateBranch(BranchDTO branch) {
        System.out.println("La sucursal " + branch.getIdentificationCode() + " (" + branch.getName() + ") se ha actualizado exitosamente");
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public Integer bankIdSelected(String actionOption) {
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
        System.out.println("Ingrese el numero de ID del banco para " + actionOption + " ó 0 para cancelar: \n");

        return Integer.valueOf( ScannerSingleton.getInputInteger());
    }

    public Integer branchIdSelected(String actionOption) {
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
        System.out.println("Ingrese el numero de ID de la sucursal para " + actionOption + " ó 0 para cancelar: \n");

        return Integer.valueOf( ScannerSingleton.getInputInteger());
    }

    public Integer branchCodeSelected(String actionOption) {
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
        System.out.println("Ingrese el código de la sucursal para " + actionOption + " ó 0 para cancelar: \n");

        return Integer.valueOf( ScannerSingleton.getInputInteger());
    }

    public Boolean getResponseToDelete(BranchDTO branch) {
        System.out.print("Se Eliminará la siguiente sucursal: ");
        System.out.println(Ansi.PURPLE + branch.getId() + " " + branch.getIdentificationCode() + " " + branch.getName() + Ansi.RESET);


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

    public void showDeleteBranch (String name) {
        System.out.println("La sucursal " + name + " se ha eliminado exitosamente");
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public void deleteBranchCanceled() {
        System.out.println("Ha cancelado la eliminacion de la sucursal");
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public String getIdentificationCode(){
        System.out.println("Ingresar el código de sucursal: ");
        return String.valueOf( ScannerSingleton.getInputString());
    }
    public String getBranchName(){
        System.out.println("Ingresar el nombre de la sucursal: ");
        return String.valueOf( ScannerSingleton.getInputString());
    }
}
