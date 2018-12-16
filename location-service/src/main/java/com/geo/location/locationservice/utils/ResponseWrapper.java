package com.geo.location.locationservice.utils;

import com.geo.location.locationservice.generated_model.GeoLocationResponse;
import com.geo.location.locationservice.model.LocationDetails;
/**
 * Wrapper class to build a custom response from original API response.
 * @author  Aseem Shrestha
 */
public class ResponseWrapper {

    private final GeoLocationResponse geoLocationResponse;

    public ResponseWrapper(GeoLocationResponse geoLocationResponse) {
        this.geoLocationResponse = geoLocationResponse;
    }

    public LocationDetails customResponse() {

        return Utils.buildCustomResponse(this.geoLocationResponse);
    }

}
