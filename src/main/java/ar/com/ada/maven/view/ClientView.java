package ar.com.ada.maven.view;

import ar.com.ada.maven.utils.ScannerSingleton;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ClientView {
    public int clientMenuSelectOption(){
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

}
