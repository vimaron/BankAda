package ar.com.ada.maven.utils;

import java.io.IOException;
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


}
