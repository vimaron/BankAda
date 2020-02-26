package ar.com.ada.maven.controller;

import ar.com.ada.maven.model.dao.AccountDAO;
import ar.com.ada.maven.model.dao.CustomerDAO;
import ar.com.ada.maven.model.dao.TransactionDAO;
import ar.com.ada.maven.model.dao.TransactionTypeDAO;
import ar.com.ada.maven.model.dto.*;
import ar.com.ada.maven.utils.Paginator;
import ar.com.ada.maven.view.AccountView;
import ar.com.ada.maven.view.TransactionView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TransactionController {

    private static TransactionView view = new TransactionView();
    private static TransactionDAO transactionDAO = new TransactionDAO();
    private static AccountDAO accountDAO = new AccountDAO();
    private static TransactionTypeDAO transactionTypeDAO = new TransactionTypeDAO();



    public static void createNewTransaction(){


    private static void createNewTransaction(){

        Date today = new Date();
        String trasactionType = view.getTransactionType();


        if (trasactionType == "1") {

            Double transactionAmount = view.getTransactionAmount();
            TransactionTypeDTO transactionTypeId = transactionTypeDAO.findById(1);

            if (transactionAmount != null){

                view.choiceAccountIdInfo();
                int accountId = AccountController.listAccountsPerPage(Paginator.SELECT, false);

                if (accountId != 0) {

                    AccountDTO accountById = accountDAO.findById(accountId);
                    TransactionDTO newTransaction = new TransactionDTO(today, transactionAmount, accountById, transactionTypeId);

                    Boolean isSaved = transactionDAO.save(newTransaction);
                    if (isSaved){
                        view.showNewTransaction(newTransaction.getDate(), newTransaction.getAmount(),
                                newTransaction.getTransactionTypeID());
                    } else
                    view.newTransactionCancelled();
                }
            } else {
                view.invalidData();
            }
        } else {
            Double transactionAmount = view.getTransactionAmount();
            TransactionTypeDTO transactionTypeId = transactionTypeDAO.findById(2);
            if (transactionAmount != null){
                view.choiceAccountIdInfo();
                int accountId = AccountController.listAccountsPerPage(Paginator.SELECT, false);

                if (accountId != 0) {

                    AccountDTO accountById = accountDAO.findById(accountId);
                    Integer idAccountType = accountById.getAccountTypeID().getId();
                    switch (idAccountType){
                        case 1: if (transactionAmount <= 1000){
                            view.choiceAccountIdInfo();
                            accountId = AccountController.listAccountsPerPage(Paginator.SELECT, false);

                            if (accountId != 0) {

                                accountById = accountDAO.findById(accountId);
                                TransactionDTO newTransaction = new TransactionDTO(today, transactionAmount,
                                        accountById, transactionTypeId);

                                Boolean isSaved = transactionDAO.save(newTransaction);
                                if (isSaved){
                                    view.showNewTransaction(newTransaction.getDate(), newTransaction.getAmount(),
                                            newTransaction.getTransactionTypeID());
                                } else
                                view.newTransactionCancelled();
                                } else {
                                view.invalidData();
                             }
                            } else {
                            view.invalidTransactionAmount("$1000");
                            }
                        break;
                        case 2: if (transactionAmount <= 300) {
                            view.choiceAccountIdInfo();
                            accountId = AccountController.listAccountsPerPage(Paginator.SELECT, false);

                            if (accountId != 0) {

                                accountById = accountDAO.findById(accountId);
                                TransactionDTO newTransaction = new TransactionDTO(today, transactionAmount,
                                        accountById, transactionTypeId);

                                Boolean isSaved = transactionDAO.save(newTransaction);
                                if (isSaved){
                                    view.showNewTransaction(newTransaction.getDate(), newTransaction.getAmount(),
                                            newTransaction.getTransactionTypeID());
                                } else
                                view.newTransactionCancelled();
                            } else {
                                view.invalidData();
                            }
                        } else {
                            view.invalidTransactionAmount("$300");
                        }
                        break;
                        case 3: if (transactionAmount <= 150){
                            view.choiceAccountIdInfo();
                            accountId = AccountController.listAccountsPerPage(Paginator.SELECT, false);

                            if (accountId != 0) {

                                accountById = accountDAO.findById(accountId);
                                TransactionDTO newTransaction = new TransactionDTO(today, transactionAmount,
                                        accountById, transactionTypeId);

                                Boolean isSaved = transactionDAO.save(newTransaction);
                                if (isSaved){
                                    view.showNewTransaction(newTransaction.getDate(), newTransaction.getAmount(),
                                            newTransaction.getTransactionTypeID());
                                } else
                                view.newTransactionCancelled();
                            } else {
                                view.invalidData();
                            }
                        } else {
                            view.invalidTransactionAmount("$150");
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


