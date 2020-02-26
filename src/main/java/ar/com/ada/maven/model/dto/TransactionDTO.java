package ar.com.ada.maven.model.dto;

import java.util.Date;
import java.util.Objects;

public class TransactionDTO {
    private int id;
    private Date date;
    private Double amount;
    private AccountDTO accountID;
    private TransactionTypeDTO transactionTypeID;

    public TransactionDTO(){}

    public TransactionDTO(int id, Date date, double amount, AccountDTO accountID,
                          TransactionTypeDTO transactionTypeID) {
        this.id=id;
        this.date=date;
        this.amount= amount;
        this.accountID=accountID;
        this.transactionTypeID=transactionTypeID;
    }


    public TransactionDTO(Date date, Double amount, AccountDTO accountID, TransactionTypeDTO transactionTypeID){
        this.date= date;
        this.amount= amount;
        this.accountID=accountID;
        this.transactionTypeID = transactionTypeID;
    }




    public int getId(){return id;}
    public void setId(int id){this.id=id;}
    public Date getDate(){return date;}
    public void setDate(Date date){this.date=date;}
    public Double getAmount(){return amount;}
    public void setAmount(Double amount){this.amount=amount;}
    public AccountDTO getAccountID(){return accountID;}
    public void setAccountID(AccountDTO accountID){this.accountID=accountID;}
    public TransactionTypeDTO getTransactionTypeID(){return transactionTypeID;}
    public void setTransactionTypeID(TransactionDTO transactionDTO){this.transactionTypeID=transactionTypeID;}

    @Override
    public String toString() {
        return "CountryDTO{" +
                "id=" + id +
                ", date='" + date + '\'' +
                " amount=" + amount +'\''+
                " accountID=" + accountID + '\'' +
                " transactionTypeID=" + transactionTypeID + '\''+
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionDTO transactionDTO = (TransactionDTO) o;
        return id == transactionDTO.id &&
                Objects.equals(date, transactionDTO.date) &&
                Objects.equals(amount, transactionDTO.amount) &&
                Objects.equals(accountID, transactionDTO.accountID) &&
                Objects.equals(transactionTypeID, transactionDTO.transactionTypeID);
    }

}
