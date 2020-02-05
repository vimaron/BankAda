package ar.com.ada.maven.model.dto;

import java.util.Objects;

public class BranchDTO {
    private int id;
    private String identificationCode;
    private String name;
    private BankDTO bankID;

    public BranchDTO(){}

    public int getId(){return id;}
    public void setId(int id){this.id=id;}
    public String getIdentificationCode(){return identificationCode;}
    public void setIdentificationCode(String identification_code){this.identificationCode=identification_code;}
    public String getName(){return name;}
    public void setName(String name){this.name=name;}
    public BankDTO getBankID(){return bankID;}
    public void setBankID(BankDTO bankID){this.bankID=bankID;}

    @Override
    public String toString() {
        return "CountryDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                " identification code=" + identificationCode +'\''+
                " bankID="+ bankID + '\''+
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BranchDTO branchDTO = (BranchDTO) o;
        return id == branchDTO.id &&
                Objects.equals(name, branchDTO.name) &&
                Objects.equals(identificationCode, branchDTO.identificationCode) &&
                Objects.equals(bankID, branchDTO.bankID);
    }
}
