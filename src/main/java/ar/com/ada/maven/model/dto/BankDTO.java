package ar.com.ada.maven.model.dto;


import java.util.Objects;

public class BankDTO {
    private int id;
    private String code;
    private String name;
    private CountryDTO countryID;

    public BankDTO(){}

    public int getId(){return id;}
    public void setId(int id){this.id=id;}
    public String getCode(){return code;}
    public void setCode(String code){this.code=code;}
    public String getName(){return name;}
    public void setName(String name){this.name=name;}
    public CountryDTO getCountryID(){return countryID;}
    public void setCountryID(CountryDTO countryID){this.countryID=countryID;}

    @Override
    public String toString() {
        return "CountryDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                " code=" + code +'\''+
                " countryID=" + countryID + '\''+
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankDTO bankDTO = (BankDTO) o;
        return id == bankDTO.id &&
                Objects.equals(name, bankDTO.name) &&
                Objects.equals(code, bankDTO.code) &&
                Objects.equals(countryID, bankDTO.countryID);
    }
}
