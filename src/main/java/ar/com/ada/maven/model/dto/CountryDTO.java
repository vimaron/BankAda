package ar.com.ada.maven.model.dto;

import java.util.Objects;

public class CountryDTO {
    private int id;
    private String code;
    private String name;

    public CountryDTO(){}

    public CountryDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }


    public int getId(){return id;}
    public void setId(int id){this.id=id;}
    public String getCode(){return code;}
    public void setCode(String code){this.code=code;}
    public String getName(){return name;}
    public void setName(String name){this.name=name;}

    @Override
    public String toString() {
        return "CountryDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                " code=" + code +'\''+
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryDTO countryDTO = (CountryDTO) o;
        return id == countryDTO.id &&
                Objects.equals(name, countryDTO.name) &&
                Objects.equals(code, countryDTO.code);
    }

}
