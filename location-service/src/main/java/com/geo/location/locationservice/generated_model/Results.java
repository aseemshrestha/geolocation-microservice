package com.geo.location.locationservice.generated_model;

import lombok.Getter;

@Getter
public class Results
{
    private String place_id;

    private Address_components[] address_components;

    private String formatted_address;

    private Plus_code plus_code;

    private String[] types;

    private Geometry geometry;

}