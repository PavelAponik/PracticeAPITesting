package api.POJO;

import api.POJO.Address;
import api.POJO.Company;

public class Users {
    private Integer id;
    private String name;
    private String username;
    private String email;
    Address address;
    private String phone;
    private String website;
    Company company;

    public Users(Integer id, String name, String username, String email, Address address, String phone, String website, Company company) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.website = website;
        this.company = company;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Address getAddressObject() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }

    public Company getCompanyObject() {
        return company;
    }

}
