package ar.com.ada.maven.controller;

import ar.com.ada.maven.view.ClientView;

public class ClientController {
    private static ClientView view = new ClientView();

    public static void init(){
        boolean bool = false;
        while (!bool){
            int varWhile = view.clientMenuSelectOption();
            switch (varWhile){
                case 1:
                    System.out.println("lista de clientes");
                    break;
                default:
                    System.out.println("Se debe seleccionar una opcion valida");
            }

        }
    }
}
