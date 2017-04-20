package com.saagie.conference.vavr.domain;


public class Address {
    private final int streetNumber;
    private final String streetName;
    private final String zipCode;
    private final String city;
    private final String country;

    public Address(int streetNumber, String streetName, String zipCode, String city, String country) {
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
    }

    public int getStreetNumber() { return streetNumber; }

    public String getStreetName() { return streetName; }

    public String getZipCode() { return zipCode; }

    public String getCity() { return city; }

    public String getCountry() { return country; }

    public boolean isValid() {
        return this.zipCode.length() == 5;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (streetNumber != address.streetNumber) return false;
        if (!streetName.equals(address.streetName)) return false;
        if (!zipCode.equals(address.zipCode)) return false;
        if (!city.equals(address.city)) return false;
        return country.equals(address.country);
    }

    @Override
    public int hashCode() {
        int result = streetNumber;
        result = 31 * result + streetName.hashCode();
        result = 31 * result + zipCode.hashCode();
        result = 31 * result + city.hashCode();
        result = 31 * result + country.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Address{" +
                "streetNumber=" + streetNumber +
                ", streetName='" + streetName + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
