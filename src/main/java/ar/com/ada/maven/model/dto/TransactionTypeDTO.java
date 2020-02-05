package ar.com.ada.maven.model.dto;

import java.util.Objects;

public class TransactionTypeDTO {
    private int id;
    private String name;

    public TransactionTypeDTO(){}

    public int getId(){return id;}
    public void setId(int id){this.id=id;}
    public String getName(){return name;}
    public void setName(String name){this.name=name;}

    @Override
    public String toString() {
        return "CountryDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionTypeDTO transactionTypeDTO = (TransactionTypeDTO) o;
        return id == transactionTypeDTO.id &&
                Objects.equals(name, transactionTypeDTO.name);
    }
}
