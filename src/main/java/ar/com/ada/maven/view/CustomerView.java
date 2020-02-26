package ar.com.ada.maven.view;

import ar.com.ada.maven.model.dto.CustomerDTO;
import ar.com.ada.maven.utils.CommandLineTable;
import ar.com.ada.maven.utils.Paginator;
import ar.com.ada.maven.utils.ScannerSingleton;


import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;



public class CustomerView {


    public Integer customerMenuSelectOption(){
        System.out.println("\n+----------------------------------------+");
        System.out.println("Corrupt Bank :: Modulo Cliente");
        System.out.println("\n+----------------------------------------+");
        System.out.println("Seleccione una opcion: \n 1.Listar \n 2.Agregar \n 3.Editar " +
                "\n 4.Eliminar \n 5.Regresar al menu principal");
        String num = ScannerSingleton.getInputInteger();
        return Integer.valueOf(num);

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

        CommandLineTable st = new CommandLineTable();
        st.setShowVerticalLines(true);

        st.setHeaders("ID", "CLIENTE");
        customers.forEach(customer ->
                st.addRow(customer.getId().toString(), customer.getName())
        );
        st.print();

        if (optionEdithOrDelete != null && !optionEdithOrDelete.isEmpty())
            paginator.set(paginator.size() - 2, optionEdithOrDelete);

        System.out.println("\n+----------------------------------------------------------------+");
        paginator.forEach(page -> System.out.print(page + " "));
        System.out.println("\n+----------------------------------------------------------------+\n");

        return ScannerSingleton.getInputString();

    }

    public int customerIdSelected(String actionOption) {
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
        System.out.println("Ingrese el numero de ID del cliente para " + actionOption + " ó 0 para cancelar: \n");

        return Integer.valueOf( ScannerSingleton.getInputInteger());
    }


    public String getNameNewContinent(){
        System.out.println("Ingresar el nombre del nuevo cliente: ");
        return String.valueOf( ScannerSingleton.getInputString());
    }
    public String getNewLastName(){
        System.out.println("Ingresar el apellido: ");
        return String.valueOf( ScannerSingleton.getInputString());
    }
    public String getNewIdType(){
        System.out.println("Ingresar el tipo de identificacion: ");
        return String.valueOf( ScannerSingleton.getInputString());
    }
    public Integer getNewIdentification(){
        System.out.println("Ingrese el numero de identificacion: ");
        return Integer.valueOf( ScannerSingleton.getInputInteger());
    }

    public void showNewCustomer(String name) {
        System.out.println("El cliente " + name + " se ha creado exitosamente");
        ScannerSingleton.pressEnterKeyToContinue();
    }
    public void newCustomerCanceled() {
        System.out.println("Ha cancelado el ingreso de un nuevo Cliente\n");
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public void customerAlreadyExists(String name){
        System.out.println("Error al guardar, ya existe un cliente con el nombre " + name);
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public static void getUpdate(CustomerDTO customer) {
        System.out.print("Se actualizará el siguiente cliente: ");
        System.out.println( customer.getId() + "\t" + customer.getName() + "\t" +
                customer.getLastName() + "\t" + customer.getIdentificationType() + "\t" + customer.getIdentification());

    }

    public static String nameToUpdate(CustomerDTO customer){
        System.out.print("Ingrese el nombre del cliente para actualizar ");
        System.out.println("(para cancelar, no ingresar datos y presionar enter):\n");

        Scanner keyboard = ScannerSingleton.getInstance();
        keyboard.nextLine();
        return String.valueOf(ScannerSingleton.getInputString());

    }
    public static String lastNameToUpdate(CustomerDTO customer){
        System.out.print("Ingrese el apellido del cliente para actualizar ");
        System.out.println("(para cancelar, no ingresar datos y presionar enter):\n");

        Scanner keyboard = ScannerSingleton.getInstance();
        keyboard.nextLine();
        return String.valueOf(ScannerSingleton.getInputString());
    }
    public static int identificationToUpdate(CustomerDTO customer){
        System.out.print("Ingrese el numero de identificacion del cliente para actualizar ");
        System.out.println("(para cancelar, no ingresar datos y presionar enter):\n");

        Scanner keyboard = ScannerSingleton.getInstance();
        keyboard.nextLine();
        return Integer.valueOf(ScannerSingleton.getInputInteger());
    }


    public int selectCustomerUpdate(){
        System.out.println("\n+----------------------------------------------------------------+");
        System.out.println("\t  Corrupt Bank :: Modulo Clientes :: Edicion de Cliente");
        System.out.println("+----------------------------------------------------------------+\n");

        System.out.println("Seleccione que desea modificar: 1. Nombre \n 2.Apellido \n 3.Tipo de identificacion");

        return Integer.valueOf(ScannerSingleton.getInputInteger());
    }

    public void showUpdateCustomer(String name) {
        System.out.println("El cliente " + name + " se ha actualizado exitosamente");
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public void updateCustomerCanceled() {
        System.out.println("Ha cancelado la actualizacion del cliente\n");
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public static Boolean getResponseToDelete(CustomerDTO customer){
        System.out.print("Se Eliminará el siguiente cliente: ");
        System.out.println( customer.getId() + " " + customer.getName());

        System.out.println("Esta seguro que desea eliminarlo? ");
        System.out.println("| 1 | Si");
        System.out.println("| 2 | No");

        return Boolean.valueOf(ScannerSingleton.getInputString());
    }

    public void showDeleteCustomer(String name) {
        System.out.println("El cliente " + name + " se ha eliminado exitosamente");
        ScannerSingleton.pressEnterKeyToContinue();
    }


    public void customerNotExist(int id) {
        System.out.println("No existe un cliente con el id " + id + " asociado");
        System.out.println("Selecciones un ID valido o 0 para cancelar");
    }

    public void deleteCustomerCanceled() {
        System.out.println("Ha cancelado la eliminacion del Cliente\n");
        ScannerSingleton.pressEnterKeyToContinue();
    }
}
