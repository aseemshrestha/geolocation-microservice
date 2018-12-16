package com.geo.location.locationservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

/**
 * Location Model
 * @author  Aseem Shrestha
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Location
{
    private String lng;
    private String lat;

}
