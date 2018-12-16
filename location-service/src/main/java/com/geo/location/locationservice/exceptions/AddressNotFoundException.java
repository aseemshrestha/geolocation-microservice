package com.geo.location.locationservice.exceptions;

/**
 * Custom Exception class for handling address not found
 * @author  Aseem Shrestha
 *
 */
public class AddressNotFoundException extends Exception {

    public AddressNotFoundException (String message) {
        super(message);
    }
}
