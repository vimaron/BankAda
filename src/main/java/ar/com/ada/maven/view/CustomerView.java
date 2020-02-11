package ar.com.ada.maven.view;

import ar.com.ada.maven.model.dto.CustomerDTO;
import ar.com.ada.maven.utils.ScannerSingleton;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CustomerView {
    public int customerMenuSelectOption(){
        System.out.println("Corrupt Bank :: Modulo Cliente");
        System.out.println("Seleccione una opcion: \n 1.Lista \n 2.Agregar \n 3.Editar \n 4.Eliminar \n 5.Salir");

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

}
