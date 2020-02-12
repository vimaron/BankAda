package ar.com.ada.maven.view;

import ar.com.ada.maven.model.dto.CustomerDTO;
import ar.com.ada.maven.utils.ScannerSingleton;
import jdk.internal.jline.internal.Ansi;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static ar.com.ada.maven.utils.ScannerSingleton.keyboard;

public class CustomerView {
    public int customerMenuSelectOption(){
        System.out.println("Corrupt Bank :: Modulo Cliente");
        System.out.println("Seleccione una opcion: \n 1.Listar \n 2.Agregar \n 3.Editar \n 4.Eliminar \n 5.Salir");

        Scanner keyboard = ScannerSingleton.getInstance();

        while (true){
            try {
                int selectOpcion = keyboard.nextInt();
                keyboard.nextLine();
                return selectOpcion;
            } catch (InputMismatchException e){
                System.out.println("El caracter ingresado es erroneo");
                keyboard.next();
            }
        }
    }

    public void printAllCustomers(List<CustomerDTO> customers){
        System.out.println("Listado de clientes: ");
        customers.forEach(customer -> {
            String customerName = customer.getName();
            int customerId = customer.getId();
            System.out.println("Cliente [id:  " + customerId + " y nombre: " + customerName + "]");
        });
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public String printCustomersPerPage(List<CustomerDTO> customers, List<String> paginator,
                                        String optionEdithOrDelete, boolean showHeader) {
        if (showHeader) {
            System.out.println("\n+----------------------------------------------------------------+");
            System.out.println("\t  Corrupt Bank :: Modulo Clientes :: Lista Cliente");
            System.out.println("+----------------------------------------------------------------+\n");
        }

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
                    MainView.invalidData();
                    System.out.print("? ");
                    name = keyboard.nextLine();
                }
                return name;
            } catch (InputMismatchException e) {
                MainView.invalidData();
                keyboard.next();
            }
        }
    }

    public int customerIdSelected(String actionOption) {
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
        System.out.println("Ingrese el numero de ID del cliente para " + actionOption + " ó 0 para cancelar: \n");

        while (true) {
            try {
                System.out.print("? ");
                int choice = keyboard.nextInt();
                return choice;
            } catch (InputMismatchException e) {
                MainView.invalidData();
                keyboard.next();
            }
        }
    }


}
