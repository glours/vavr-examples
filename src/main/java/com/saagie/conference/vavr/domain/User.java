package com.saagie.conference.vavr.domain;


import com.saagie.conference.vavr.values.UserValidation;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.control.Option;
import io.vavr.control.Validation;

public class User {

    private final String userName;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    //In real project if address in not mandatory we will user Option<Address> as type
    private final Address address;

    public User(String userName, String firstName, String lastName, String email, String password, Address address) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.address = address;
    }

    public String getUserName() { return userName; }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public String getEmail() { return email; }

    public String getPassword() { return password; }

    public Address getAddress() { return address; }

    //Prefer this kind of validation
    public Validation<Seq<String>, User> validate() {
        return UserValidation.validate(this);
    }

    public Option<Address> getAddressIfValid() throws IllegalArgumentException {
        return this.isAddressValid() ? Option.of(this.address) : Option.none();
    }

    public boolean isInvalidAddressFromRouen() {
        try {
            return !this.isAddressValid() && this.address.getCity().equals("Rouen");
        } catch (IllegalArgumentException e) {
            return this.address.getCity().equals("Rouen");
        }
    }

    public boolean isAddressValid() throws IllegalArgumentException {
        //Don't do that in your code, it's only for example usage of Try and Either object
        if(this.address.isValid()) {
            return true;
        }
        throw new IllegalArgumentException("Not a good address");
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!userName.equals(user.userName)) return false;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        int result = userName.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", address=" + address +
                '}';
    }


    public static List<User> generateUserList() {
        return List.range(0, 20)
                .map(val -> generateUser(val));
    }

    public static User generateUser(int index) {
        return new User("userName" + index, "firstName" + index, "lastName" + index,
                "userName" + index + "@saagie.com", "P@Ssw()rdUser" + index, generateAddress(index));
    }

    private static Address generateAddress(int cityIndex) {
        return new Address(cityIndex, "street name " + cityIndex, "1234" + cityIndex,
                selectCity(cityIndex), "France");
    }

    private static String selectCity(int cityIndex) {
        return cityIndex % 2 == 0 ? "Paris" : "Rouen";
    }
}
