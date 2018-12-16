package com.geo.location.locationservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

/**
 * LocationAddress - only NotNull values will be included
 * @author  Aseem Shrestha
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocationAdddress
{
    private Location location;

    private Enum status;

    private String address;



}