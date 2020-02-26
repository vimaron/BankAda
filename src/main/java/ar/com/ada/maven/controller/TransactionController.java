package ar.com.ada.maven.controller;

import ar.com.ada.maven.model.dao.AccountDAO;
import ar.com.ada.maven.model.dao.CustomerDAO;
import ar.com.ada.maven.model.dao.TransactionDAO;
import ar.com.ada.maven.model.dto.AccountDTO;
import ar.com.ada.maven.model.dto.CustomerDTO;
import ar.com.ada.maven.model.dto.TransactionDTO;
import ar.com.ada.maven.utils.Paginator;
import ar.com.ada.maven.view.AccountView;
import ar.com.ada.maven.view.TransactionView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransactionController {

    private static TransactionView view = new TransactionView();
    private static TransactionDAO transactionDAO = new TransactionDAO();
    private static AccountDAO accountDAO = new AccountDAO();
    private static AccountView viewAccount = new AccountView();


    private static void createNewTransaction(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        int trasactionType = view.getTransactionType();
        AccountDTO account = null;

        if (trasactionType == 1) {

            Double transactionAmount = view.getTransactionAmount();

            if (transactionAmount != null){

                view.choiceAccountIdInfo();
                int accountId = AccountController.listAccountsPerPage(Paginator.SELECT, false);

                if (accountId != 0) {

                    AccountDTO accountById = accountDAO.findById(accountId);
                    TransactionDTO newTransaction = new TransactionDTO(date, transactionAmount, accountById);

                    Boolean isSaved = transactionDAO.save(newTransaction);
                }
            } else {
                //ingrese dato valido
            }
        } else {
            Double transactionAmount = view.getTransactionAmount();

            if (transactionAmount != null){
                view.choiceAccountIdInfo();
                int accountId = AccountController.listAccountsPerPage(Paginator.SELECT, false);

                if (accountId != 0) {

                    AccountDTO accountById = accountDAO.findById(accountId);
                    switch (accountById.getAccountTypeID()){
                        case 1: if (transactionAmount <= 1000){
                            view.choiceAccountIdInfo();
                            accountId = AccountController.listAccountsPerPage(Paginator.SELECT, false);

                            if (accountId != 0) {

                                accountById = accountDAO.findById(accountId);
                                TransactionDTO newTransaction = new TransactionDTO(dtf.format(now), transactionAmount, accountById);

                                Boolean isSaved = transactionDAO.save(newTransaction);
                            } else {
                                //ingrese dato valido
                            }
                        } else {
                            //la transaccion debe ser menor a mil
                        }
                        break;
                        case 2: if (transactionAmount <= 300) {
                            view.choiceAccountIdInfo();
                            accountId = AccountController.listAccountsPerPage(Paginator.SELECT, false);

                            if (accountId != 0) {

                                accountById = accountDAO.findById(accountId);
                                TransactionDTO newTransaction = new TransactionDTO(dtf.format(now), transactionAmount, accountById);

                                Boolean isSaved = transactionDAO.save(newTransaction);
                            } else {
                                //ingrese dato valido
                            }
                        } else {
                            //la transaccion debe ser menor a mil
                        }
                        break;
                        case 3: if (transactionAmount <= 150){
                            view.choiceAccountIdInfo();
                            accountId = AccountController.listAccountsPerPage(Paginator.SELECT, false);

                            if (accountId != 0) {

                                accountById = accountDAO.findById(accountId);
                                TransactionDTO newTransaction = new TransactionDTO(dtf.format(now), transactionAmount, accountById);

                                Boolean isSaved = transactionDAO.save(newTransaction);
                            } else {
                                //ingrese dato valido
                            }
                        } else {
                            //la transaccion debe ser menor a mil
                        }
                        break;
                        default:
                            System.out.println("ERROR");
                    }
                }
            }
        }
    }


}


