package ar.com.ada.maven.view;

import ar.com.ada.maven.model.dto.BankDTO;
import ar.com.ada.maven.utils.Ansi;
import ar.com.ada.maven.utils.CommandLineTable;
import ar.com.ada.maven.utils.Paginator;
import ar.com.ada.maven.utils.ScannerSingleton;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class BankView {

        private Scanner keyboard = ScannerSingleton.getInstance();

        public Integer bankMenuSelectedOption() {
            System.out.println("\n+----------------------------------------+");
            System.out.println("\t\t BANK ADA APP :: Modulo BANCOS");
            System.out.println("+----------------------------------------+\n");

            System.out.println("Seleccione una acción del menú:\n");
            System.out.println("| 1 | Listar");
            System.out.println("| 2 | Agregar"); //preguntar si conviene mostrar agregar
            System.out.println("| 3 | Eliminar");
            System.out.println("| 4 | Regresar al menú principal");
            System.out.println("-------------------------\n");

            return Integer.valueOf(ScannerSingleton.getInputInteger());

        }

        public String printBanksPerPage(List<BankDTO> banks, List<String> paginator, String optionEdithOrDelete, boolean showHeader) {
            if (showHeader) {
                System.out.println("\n+----------------------------------------+");
                System.out.println("\t\t BANK ADA APP :: Modulo Bancos :: Lista de Bancos");
                System.out.println("+----------------------------------------+\n");
            }

            CommandLineTable st = new CommandLineTable();
            st.setShowVerticalLines(true);

            st.setHeaders("ID", "CODIGO", "BANCO", "PAÌS");
            banks.forEach(bankDTO ->
                    st.addRow(bankDTO.getId().toString(), bankDTO.getCode(), bankDTO.getName(), bankDTO.getCountryID().toString())
            );
            st.print();

            if (optionEdithOrDelete != null && !optionEdithOrDelete.isEmpty())
                paginator.set(paginator.size() - 2, optionEdithOrDelete);

            System.out.println("\n+----------------------------------------+");
            paginator.forEach(page -> System.out.print(page + " "));
            System.out.println("\n+----------------------------------------+");

            return ScannerSingleton.getInputString();
        }

        public void choiceCountryIdInfo() {
            System.out.println("Seleccione de la siguiente lista, el país al que pertenece la sucursal");
            ScannerSingleton.pressEnterKeyToContinue();
        }

        public void newBankCanceled() {
            System.out.println("Se ha cancelado la creación de un nuevo banco\n");
            ScannerSingleton.pressEnterKeyToContinue();
        }

        public void bankAlreadyExists(String code) {
            System.out.println(Ansi.RED);
            System.out.println("Error al crear banco, ya existe un banco con este còdigo " + code);
            System.out.println(Ansi.RESET);
            ScannerSingleton.pressEnterKeyToContinue();
        }

        public void showNewBank(String name) {
            System.out.println(Ansi.GREEN);
            System.out.println("El banco se ha creado exitosamente" + name);
            System.out.println(Ansi.RESET);
            ScannerSingleton.pressEnterKeyToContinue();
        }
    public void updateBankCanceled() {
        System.out.println("Ha cancelado la actualizacion del Banco\n");
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public void bankNotExist(int id) {
        System.out.println("No existe un banco con este id " + id + " asociado");
        System.out.println("Selecciones un ID valido o 0 para cancelar");
    }

    public void countryNotExist(int id) {
        System.out.println("No existe un país con el id " + id + " asociado");
        System.out.println("Selecciones un ID valido o 0 para cancelar");
    }

    public String getNameToUpdate(BankDTO bank) {
        System.out.print("Se actualizará el nombre de la siguiente sucursal: ");
        System.out.println(Ansi.PURPLE + bank.getId() + " " + bank.getCode() + " " + bank.getName() + Ansi.RESET);

        System.out.print("Ingrese el nuevo nombre de la sucursal para actualizar ");
        System.out.println("(para cancelar, no ingresar datos y presionar enter):\n");

        keyboard.nextLine();

        while (true) {
            try {
                System.out.print("? ");
                String name = keyboard.nextLine().trim();
                while (!name.matches("^[A-Za-záéíóúüÁÉÍÓÚÜ\\s]+$") && !name.isEmpty()) {
                    MainView.invalidData();
                    name = keyboard.nextLine();
                }
                return name;
            } catch (InputMismatchException e) {
                MainView.invalidData();
                keyboard.next();
            }
        }
    }


    public static void selectBankIdToEdithOrDeleteInfo(String actions) {
        System.out.println("De la siguiente lista de bancos, seleccione el id para  " + actions);
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public static void selectCountryIdToEdithInfo(String actions) {
        System.out.println("De la siguiente lista de países, seleccione el id para  " + actions);
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public void showUpdateBank(BankDTO bank) {
        System.out.println("La sucursal " + bank.getCode() + " (" + bank.getName() + ") se ha actualizado exitosamente");
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public Integer countryIdSelected(String actionOption) {
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
        System.out.println("Ingrese el numero de ID del país para " + actionOption + " ó 0 para cancelar: \n");

        return Integer.valueOf(ScannerSingleton.getInputInteger());
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


        public Boolean getResponseToDelete(BankDTO bankDTO) {
            System.out.print("Se Eliminará el siguiente banco: ");
            System.out.println(Ansi.PURPLE + bankDTO.getId() + " " + bankDTO.getCode() + " " + bankDTO.getName() + Ansi.RESET);


            System.out.println("¿Esta seguro que desea eliminarla? ");
            System.out.println("| 1 | Sí");
            System.out.println("| 2 | No");

            keyboard.nextLine();

            while (true) {
                try {
                    System.out.print("? ");
                    String code = keyboard.nextLine().trim();
                    while (!code.matches("^[1-2]+$") && !code.isEmpty()) {
                        System.out.println("Error, debe ingresar una opcion valida");
                        code = keyboard.nextLine();
                    }
                    return "1".equals(code);
                } catch (InputMismatchException e) {
                    System.out.println("Error, debe ingresar una opción valida");
                    keyboard.next();
                }
            }

        }

        public void showDeleteBank (String name) {
            System.out.println("El banco " + name + " se ha eliminado exitosamente");
            ScannerSingleton.pressEnterKeyToContinue();
        }

        public void deleteBankCanceled() {
            System.out.println("Ha cancelado la eliminacion del banco");
            ScannerSingleton.pressEnterKeyToContinue();
        }

    public String getCode(){
        System.out.println("Ingresar el código de banco: ");
        return String.valueOf( ScannerSingleton.getInputString());
    }
    public String getBankName(){
        System.out.println("Ingresar el nombre del banco: ");
        return String.valueOf( ScannerSingleton.getInputString());
    }
}
