package com.geo.location.locationservice.exceptions;

/**
 * Custom Exception class for handing bad requests
 * @author  Aseem Shrestha
 *
 */
public class BadRequestException extends Exception {

    public BadRequestException(String message) {
        super(message);

    }
}
