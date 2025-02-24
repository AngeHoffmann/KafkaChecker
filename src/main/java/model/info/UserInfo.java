package model.info;

import java.util.List;

public class UserInfo {
    private Integer id;
    private String name;
    private List<Address> addresses;
    private List<Phone> phones;

    public UserInfo(Integer id, String name, List<Address> addresses, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.addresses = addresses;
        this.phones = phones;
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", addresses=" + addresses +
            ", phones=" + phones +
            '}';
    }
}
