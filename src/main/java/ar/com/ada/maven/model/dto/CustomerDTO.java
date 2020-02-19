package ar.com.ada.maven.model.dto;

import java.util.Objects;

public class CustomerDTO {
    private int id;
    private String name;
    private String lastName;
    private String identificationType;
    private int identification;

    public CustomerDTO(){}

    public CustomerDTO(int id, String name, String lastName, String identificationType, int identification) {
        this.id=id;
        this.name=name;
        this.lastName=lastName;
        this.identificationType=identificationType;
        this.identification=identification;
    }

    public CustomerDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId(){return id;}
    public void setId(int id){this.id=id;}
    public String getName(){return name;}
    public void setName(String name){this.name=name;}
    public String getLastName(){return lastName;}
    public void setLastName(String lastName){this.lastName=lastName;}
    public String getIdentificationType(){return identificationType;}
    public void setIdentificationType(String identificationType){this.identificationType=identificationType;}
    public int getIdentification(){return identification;}
    public void setIdentification(int identification){this.identification=identification;}

    @Override
    public String toString() {
        return "CountryDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                " last name=" + lastName +'\''+
                " identification type="+ identificationType + '\''+
                " identification=" + identification + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerDTO customerDTO = (CustomerDTO) o;
        return id == customerDTO.id &&
                identification == customerDTO.identification &&
                Objects.equals(name, customerDTO.name) &&
                Objects.equals(lastName, customerDTO.lastName) &&
                Objects.equals(identificationType, customerDTO.identificationType);
    }

}
