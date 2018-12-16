package com.geo.location.locationservice.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.geo.location.locationservice.JacksonMapper;
import com.geo.location.locationservice.generated_model.GeoLocationResponse;
import com.geo.location.locationservice.generated_model.Results;
import com.geo.location.locationservice.model.Location;
import com.geo.location.locationservice.model.LocationAdddress;
import com.geo.location.locationservice.model.LocationDetails;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.stream;
/**
 * Util class that provides utility methods used across application.
 * @author  Aseem Shrestha
 */
public class Utils {

    // converts json to obj using jackson library - unmarshalling
    public static <T> T toObj(String json, Class<T> cl) throws java.io.IOException {
        ObjectMapper mapper = JacksonMapper.INSTANCE.getObjectMapper();
        return mapper.readValue(json, cl);
    }
    // converts obj to json using jackson library - marshalling
    public static String toJson(Object obj) throws java.io.IOException {
        ObjectMapper mapper = JacksonMapper.INSTANCE.getObjectMapper();
        return mapper.writeValueAsString(obj);
    }
   // reads file from resource
    public static String getFileFromResource(String filename) throws Exception {
        File resource = new ClassPathResource(filename).getFile();
        return new String(Files.readAllBytes(resource.toPath()));
    }
   // reads remote API and converts response to appropriate class
    public static <T> List<T> parseJsonArrayFromUrl(String content, Class<T> cl) throws IOException
    {
        ObjectMapper mapper = JacksonMapper.INSTANCE.getObjectMapper();
        URL jsonUrl = new URL(content);
        TypeFactory typeFactory = mapper.getTypeFactory();
        return mapper.readValue(jsonUrl, typeFactory.constructCollectionType(List.class, cl));
    }
    // this class is responsible for building a custom response
    public static LocationDetails buildCustomResponse(GeoLocationResponse response) {
        Results[] results = response.getResults();
        boolean isFound = true;
       // if address is not found, set isFound to false
        if(results.length == 0 && response.getStatus().equals("ZERO_RESULTS")) {
             isFound = false;
        }
        Location location = new Location();
        LocationDetails locationDetails = new LocationDetails();
        LocationAdddress locationAdddress = new LocationAdddress();
        // if found, build a wrapper and set status as FOUND
        if(isFound) {
            Arrays.stream(results).forEach(result -> {
                location.setLat(result.getGeometry().getLocation().getLat());
                location.setLng(result.getGeometry().getLocation().getLng());
                locationAdddress.setAddress(result.getFormatted_address());
                locationAdddress.setLocation(location);
                locationAdddress.setStatus(HttpStatus.FOUND);
            });
            LocationAdddress[] locationArray = {locationAdddress};
            locationDetails.setResults(locationArray);
        }
        // if not found, set status as NOT_FOUND
       else {
            locationAdddress.setStatus(HttpStatus.NOT_FOUND);
            LocationAdddress[] locationArray_notFound = {locationAdddress};
            locationDetails.setResults(locationArray_notFound);
        }
        return locationDetails;
    }
}
