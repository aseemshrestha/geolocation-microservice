package com.geo.location.locationservice;

import com.jayway.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author  Aseem Shrestha
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationControllerTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost:9092/api/location-service/geocode?address=";
    }

    @Test
    public void location_controller_test_found_1()  {
        when().get(RestAssured.baseURI + "301 Memorial Drive, Avon MA 2322")
                .then().statusCode(200).body("results[0].status",equalTo("FOUND"));
    }

    @Test
    public void location_controller_test_found_2()  {
        when().get(RestAssured.baseURI + "250 Hartford Avenue, Bellingham MA 2019")
                .then().statusCode(200).body("results[0].status",equalTo("FOUND"));
    }

    @Test
    public void location_controller_test_not_found()  {
        when().get(RestAssured.baseURI + "NOT_FOUND_ADDRESS")
                .then().statusCode(200).body("results[0].status",equalTo("NOT_FOUND"));
    }
    @Test
    public void location_controller_test_not_found_status_after_5_retries()  {
      for( int i = 0;i < 5; i++) {
          if( i < 5) {
              when().get(RestAssured.baseURI + "3011 Massachusetts Ave, Lunenburg MA 1462")
                      .then().statusCode(200).body("results[0].status", equalTo("FOUND"));
         }
         if( i == 5) {
             when().get(RestAssured.baseURI + "3011 Massachusetts Ave, Lunenburg MA 1462")
                     .then().statusCode(200).body("results[0].status", equalTo("NOT_FOUND"));
         }

      }
    }
}
