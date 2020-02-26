package ar.com.ada.maven.view;

import ar.com.ada.maven.model.dto.TransactionTypeDTO;
import ar.com.ada.maven.utils.ScannerSingleton;

import java.util.Date;

public class TransactionView {

    public String getTransactionType() {
        System.out.println("\n+----------------------------------------------------------------+");
        System.out.println("\t  Corrupt Bank :: Modulo de Transacciones");
        System.out.println("+----------------------------------------------------------------+\n");

        System.out.print("Ingrese el tipo de transaccion a ingresar: 1.Credito 2.Debito ");
        System.out.println("(para cancelar, no ingresar datos y presionar enter):");
        System.out.println("-------------------------\n");

        return ScannerSingleton.getInputInteger();
    }

    public Double getTransactionAmount(){
        System.out.println("Ingrese el monto de la transaccion: ");
        return Double.valueOf( ScannerSingleton.getInputInteger());
    }

    public void  choiceAccountIdInfo() {
        System.out.println("Seleccione de la siguiente lista, la cuenta sobre la que desea operar:");
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public void invalidData(){
        System.out.println("Ingrese un dato valido ");
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public String invalidTransactionAmount(String optionAccountType) {

        System.out.print("La transaccion para este tipo de cuenta debe ser menor a " + optionAccountType);

        return ScannerSingleton.getInputString();
    }
    public void showNewTransaction(Date date, Double amount, TransactionTypeDTO transactionTypeId) {
        System.out.println("En la fecha "+ date + " se ha agregado exitosamente la transaccion "
                + transactionTypeId + "por " + amount);
        ScannerSingleton.pressEnterKeyToContinue();
    }

    public void newTransactionCancelled() {
        System.out.println("Ha cancelado la nueva transaccion\n");
        ScannerSingleton.pressEnterKeyToContinue();
    }
}
