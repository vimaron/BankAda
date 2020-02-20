package ar.com.ada.maven.utils;

import ar.com.ada.maven.view.MainView;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ScannerSingleton {

    public static Scanner keyboard;

    private ScannerSingleton(Scanner keyboard){this.keyboard=keyboard;}

    private ScannerSingleton(){}

    public static Scanner getInstance(){
        if (keyboard == null){
            keyboard = new Scanner(System.in);
        }
        return keyboard;
    }
    public static void pressEnterKeyToContinue(){
        System.out.println("Presione ENTER para continuar ");
        try{
            System.in.read();
        } catch (IOException e) {
        }
    }

    public static String getInputString() {
        Scanner keyboard = getInstance();
        while (true) {
            try {
                System.out.print("? ");
                String txt = keyboard.nextLine().trim();
                while (!txt.matches("^[A-Za-záéíóúüÁÉÍÓÚÜ\\s]+$") && !txt.isEmpty()) {
                    MainView.invalidData();
                    txt = keyboard.nextLine();
                }
                return txt;
            } catch (InputMismatchException e) {
                MainView.invalidData();
                keyboard.next();
            }
        }
    }

    public static String getInputInteger(){
        Scanner keyboard = getInstance();
        while (true){
            try{
                System.out.println("? ");
                String entero = keyboard.nextLine().trim();
                while (!entero.matches("^[0-9]+$") && !entero.isEmpty()){
                    MainView.invalidData();
                    entero = keyboard.nextLine();
                }
                return entero;
            } catch (InputMismatchException e){
                MainView.invalidData();
                keyboard.next();
            }
        }
    }



}
