package ar.com.ada.maven.model.dto;

import java.util.Objects;

public class AccountTypeDTO {
    private int id;
    private String name;

    public AccountTypeDTO(){}

    public int getId(){return id;}
    public void setId(int id){this.id = id;}
    public String getName(){return name;}
    public void setName(String name){this.name=name;}

    @Override
    public String toString() {
        return "AccountTypeDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountTypeDTO accountTypeDTO = (AccountTypeDTO) o;
        return id == accountTypeDTO.id &&
                Objects.equals(name, accountTypeDTO.name);
    }

}
