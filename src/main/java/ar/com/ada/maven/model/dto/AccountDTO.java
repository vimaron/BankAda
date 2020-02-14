package ar.com.ada.maven.model.dto;

import java.util.Objects;

public class AccountDTO{
    private Integer id;
    private int number;
    private double balance;
    private String iban;
    private CustomerDTO customerID;
    private AccountTypeDTO accountTypeID;
    private BranchDTO branchID;

    public AccountDTO(){}

    public AccountDTO(int id, int number, double balance, String iban,
                      CustomerDTO customerID, AccountTypeDTO accountType, BranchDTO branchID) {
        this.id=id;
        this.number=number;
        this.balance=balance;
        this.iban=iban;
        this.customerID=customerID;
        this.accountTypeID=accountType;
        this.branchID=branchID;
    }

    public Integer getId(){return id;}
    public void setId(int id){this.id=id;}
    public int getNumber(){return number;}
    public void setNumber(int number){this.number=number;}
    public double getBalance(){return balance;}
    public void setBalance(double balance){this.balance=balance;}
    public String getIban(){return iban;}
    public void setIban(String iban){this.iban=iban;}
    public CustomerDTO getCustomerID(){return customerID;}
    public void setCustomerID(CustomerDTO customerID){this.customerID= customerID;}
    public AccountTypeDTO getAccountTypeID(){return accountTypeID;}
    public void setAccountTypeID(AccountTypeDTO accountTypeID){this.accountTypeID=accountTypeID;}
    public BranchDTO getBranchID(){return branchID;}
    public void setBranchID(BranchDTO branchID){this.branchID=branchID;}

    @Override
    public String toString() {
        return "CountryDTO{" +
                "id=" + id +
                ", number='" + number + '\'' +
                " balance=" + balance +'\''+
                " iban="+ iban + '\''+
                " customerID=" + customerID + '\'' +
                " accountTypeID=" + accountTypeID + '\'' +
                " branchID=" + branchID + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDTO accountDTO = (AccountDTO) o;
        return id == accountDTO.id &&
                number == accountDTO.number &&
                balance == accountDTO.balance &&
                Objects.equals(iban, accountDTO.iban) &&
                Objects.equals(customerID, accountDTO.customerID) &&
                Objects.equals(accountTypeID, accountDTO.accountTypeID) &&
                Objects.equals(branchID, accountDTO.branchID);
    }
}
