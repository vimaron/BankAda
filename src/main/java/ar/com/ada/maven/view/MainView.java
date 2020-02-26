package ar.com.ada.maven.view;

import ar.com.ada.maven.utils.Ansi;
import ar.com.ada.maven.utils.ScannerSingleton;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainView {
    public int selectOption(){
        System.out.println("Welcome to Corrupt Bank");
        System.out.println("Selecciones una opcion: \n 1.Cliente \n 2.Cuentas \n 3.Salir");
        return Integer.valueOf(ScannerSingleton.getInputInteger());
    }

    public static void invalidData(){
        System.out.println("ERROR :: El dato ingresado es erroneo");
    }

    public static void chooseValidOption() {
        System.out.println(Ansi.RED);
        System.out.println("Error, debe ingresar una opcion valida");
        System.out.println(Ansi.RESET);
    }
 }

