package com.geo.location.locationservice.resources.controllers;

import com.geo.location.locationservice.config.ApiConfig;
import com.geo.location.locationservice.exceptions.BadRequestException;
import com.geo.location.locationservice.generated_model.GeoLocationResponse;
import com.geo.location.locationservice.model.LocationDetails;
import com.geo.location.locationservice.resources.services.LocationService;
import com.geo.location.locationservice.utils.ResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.net.URLEncoder;
import static org.apache.commons.lang.StringUtils.isBlank;

/**
 * @author  Aseem Shrestha
 */
@RestController
@RequestMapping("/")
@Slf4j
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    /**
     * This method queries Google Maps API and returns the custom Response
     * including lat,long, address and status.
     * This class uses CaseInsensitiveHashMap as cache. No external storage is used.
     * @exception BadRequestException if address is not supplied
     * @param address lat long will be returned based in incoming address
      */
    @GetMapping(value = "/geocode", params={"address"})
    public ResponseEntity<LocationDetails> getLocationDetails(@RequestParam("address") String address) throws Exception {

        if(isBlank(address)) {
            throw new BadRequestException("Address is required!");
        }
        String addressEncoded = URLEncoder.encode(address, "UTF-8");
        // if address is in cache, serve from cache
        if (locationService.hasLocation(addressEncoded)) {
            LocationDetails locationByAddress = (LocationDetails) locationService.getLocationByAddress(addressEncoded);
            log.info("[Cache Hit - Serving location from cache]");
            return new ResponseEntity<>( locationByAddress, HttpStatus.OK);

        }
        final String fullApiUrl = ApiConfig.getApiUrlWithAddress(addressEncoded);

        log.info("final api Url :" + fullApiUrl);

        RestTemplate restTemplate = new RestTemplate();
        // query the API
        GeoLocationResponse locationDetails = restTemplate.getForObject(fullApiUrl,GeoLocationResponse.class);
       // build a custom response - we don't need everything returned by remote API
        ResponseWrapper wrapper = new ResponseWrapper(locationDetails);
       // save to cache
        locationService.saveLocation(addressEncoded, wrapper.customResponse());

        return new ResponseEntity<>( wrapper.customResponse(), HttpStatus.OK);
    }

    @GetMapping(value= "/test", params={"name"})
    public String test(@RequestParam("name") String name) {

        return new String("test:" + name);
    }

}
