package com.geo.location.locationservice;

import com.geo.location.locationservice.config.ApiConfig;
import com.geo.location.locationservice.generated_model.GeoLocationResponse;
import com.geo.location.locationservice.generated_model.Results;
import com.geo.location.locationservice.model.LocationDetails;
import com.geo.location.locationservice.utils.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import static java.util.Arrays.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
/**
 * @author  Aseem Shrestha
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationServiceTests {


    /*
      test to see if file exits in resource
     */
    @Test
    public void load_file_from_resource() throws IOException {
        File file = new ClassPathResource("location_not_found.json").getFile();
        assertTrue(file.exists());
    }

    /*
       load sample json from resource and test if we can successfully verify the json data
       at the same time, it tests the toObj method from Util class as well
     */
    @Test
    public void load_location_from_resource_google() throws Exception {
        String fileFromResource = Utils.getFileFromResource("location_output_sample_google.json");
        GeoLocationResponse geoLocationResponse = Utils.toObj(fileFromResource, GeoLocationResponse.class);
        assertEquals("OK", geoLocationResponse.getStatus());
        Results[] results = geoLocationResponse.getResults();
        stream(results).forEach(result -> {
            assertEquals(result.getGeometry().getLocation().getLat(), "37.4261056");
            assertEquals(result.getGeometry().getLocation().getLng(), "-122.0755653");
            assertEquals("1600 Amphitheatre Pkwy, Mountain View, CA 94043, USA", result.getFormatted_address());
        });
    }

    /*
       read the json file from resource, extract the data we need only and spit out json
       with only the data we need. Assert against the expected json output. At the same time, this
       test also tests the toJson method from Util class.
       use case : location found
     */
    @Test
    public void load_custom_location_from_resouce_found_scenario() throws Exception {
        String fileFromResource = Utils.getFileFromResource("location_output_sample_google.json");
        GeoLocationResponse geoLocationResponse = Utils.toObj(fileFromResource, GeoLocationResponse.class);
        LocationDetails locationDetails = Utils.buildCustomResponse(geoLocationResponse);
        System.out.println((Utils.toJson(locationDetails)));
        JSONAssert.assertEquals(
                Utils.toJson(locationDetails), Utils.getFileFromResource("custom_output_found.json"), JSONCompareMode.STRICT);
    }

    /*
       read the json file from resource to test against the location not found scenario.
       use case : location found
     */
    @Test
    public void load_custom_location_from_resouce_not_found_scenario() throws Exception {
        String fileFromResource = Utils.getFileFromResource("location_not_found.json");
        GeoLocationResponse geoLocationResponse = Utils.toObj(fileFromResource, GeoLocationResponse.class);
        Results[] results = geoLocationResponse.getResults();
        assertEquals(0, results.length);
        assertEquals("ZERO_RESULTS", geoLocationResponse.getStatus());
        JSONAssert.assertEquals(
                Utils.toJson(geoLocationResponse), Utils.getFileFromResource("location_not_found.json"), JSONCompareMode.STRICT);

    }
    /*
      test if proper url is built
    */
    @Test
    public void test_final_api_url() throws Exception {
        String address = URLEncoder.encode("777 Brockton Avenue, Abington MA 2351","UTF-8");
        String apiUrlWithAddress = ApiConfig.getApiUrlWithAddress(address);
        String expected = "https://maps.googleapis.com/maps/api/geocode/json?address=777%2BBrockton%2BAvenue%252C%2BAbington%2BMA%2B2351&key=AIzaSyD2WijYaqFSa9skqpqXIyy1Sy2yiFdyX6s";
        assertEquals(expected,apiUrlWithAddress);
    }

}

