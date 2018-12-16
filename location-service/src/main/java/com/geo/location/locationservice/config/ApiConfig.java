package com.geo.location.locationservice.config;


import org.apache.commons.lang.text.StrSubstitutor;
import java.util.HashMap;
import java.util.Map;
import static org.apache.logging.log4j.util.Strings.isBlank;

/**
 * This is API config class responsible for
 * building the API Url. Tries to read API Key from environment
 * variable, if not found uses the default API KEY is used.
 *
 * @author  Aseem Shrestha
 *
 */
public class ApiConfig {

    private static final String API_URL = "https://maps.googleapis.com/maps/api/geocode/json?address";
    private static final String API_KEY = "AIzaSyD2WijYaqFSa9skqpqXIyy1Sy2yiFdyX6s";

    public static String getApiUrlWithAddress(String address)  {

        return buildApiUrl(address);
    }

     private static String buildApiUrl(String address) {

        String gCodeApiKey = System.getenv("gcode-api-key");
        if(isBlank(gCodeApiKey)) {
            gCodeApiKey = API_KEY;
        }
        Map valuesMap = new HashMap();
        valuesMap.put("apiurl", API_URL);
        valuesMap.put("address", address);
        valuesMap.put("gcodeKey", gCodeApiKey);
        String template = "${apiurl}=${address}&key=${gcodeKey}";
        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        return sub.replace(template);

    }
}
