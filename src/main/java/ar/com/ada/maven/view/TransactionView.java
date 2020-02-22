package ar.com.ada.maven.view;

import ar.com.ada.maven.utils.ScannerSingleton;

public class TransactionView {

    public int getTransactionType() {
        System.out.println("\n+----------------------------------------------------------------+");
        System.out.println("\t  Corrupt Bank :: Modulo de Transacciones");
        System.out.println("+----------------------------------------------------------------+\n");

        System.out.print("Ingrese el tipo de transaccion a ingresar: 1.Credito 2.Debito ");
        System.out.println("(para cancelar, no ingresar datos y presionar enter):");
        System.out.println("-------------------------\n");

        return ScannerSingleton.getInputString();
    }

    public Double getTransactionAmount(){
        System.out.println("Ingrese el monto de la transaccion: ");
        return Double.valueOf( ScannerSingleton.getInputInteger());
    }

    public void  choiceAccountIdInfo() {
        System.out.println("Seleccione de la siguiente lista, la cuenta sobre la que desea operar:");
        ScannerSingleton.pressEnterKeyToContinue();
    }

}
