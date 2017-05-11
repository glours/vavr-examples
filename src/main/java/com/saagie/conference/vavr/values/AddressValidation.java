package com.saagie.conference.vavr.values;


import com.saagie.conference.vavr.domain.Address;
import io.vavr.control.Validation;


public class AddressValidation {

    private static final String VALID_ZIP_CODE = "\\d{5}";


    public static Validation<String, String> validate(Address address) {
        if (address == null) return Validation.invalid("Empty address");
        return validateZipCode(address.getZipCode());

    }

    private static Validation<String, String> validateZipCode(String zipCode) {
        return zipCode.matches(VALID_ZIP_CODE)
                ? Validation.valid(zipCode)
                : Validation.invalid("Invalid zip code");
    }

}
