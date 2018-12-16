package com.geo.location.locationservice.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;

/**
 * This class will display exceptions. Any exceptions caught by ExceptionHandler
 * will have below fields.
 * @author  Aseem Shrestha
 */
@Data
@AllArgsConstructor
public class ExceptionResponse {

    private Date timestamp;
    private String message;
    private String details;
}
