package ar.com.ada.maven.view;

import ar.com.ada.maven.utils.ScannerSingleton;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainView {
    public int selectOption(){
        System.out.println("Welcome to Corrupt Bank");
        System.out.println("Selecciones una opcion: \n 1.Cliente \n 2.Cuentas \n 3.Salir");

        Scanner key = ScannerSingleton.getInstance();


        while (true){
            try {
                int choice = key.nextInt();
                return choice;
            } catch (InputMismatchException e){
                MainView.invalidData();
                key.next();
            }
        }
    }

    public static void invalidData(){
        System.out.println("ERROR :: El dato ingresado es erroneo");
    }
 }

