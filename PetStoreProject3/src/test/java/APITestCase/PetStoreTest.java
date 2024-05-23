package APITestCase;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
//import org.testng.*;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;


public class PetStoreTest {

    @Test
    public void orderNewPetFromPetStore() {
        // Base URL of the Pet Store API
        RestAssured.baseURI = "https://petstore.swagger.io";

        // Define the new order details
        String requestBody = "{\n" +
                "  \"id\": 122,\n" +
                "  \"petId\": 123,\n" +
                "  \"quantity\": 1,\n" +
                "  \"shipDate\": \"2024-05-24T10:00:00Z\",\n" +
                "  \"status\": \"placed\",\n" +
                "  \"complete\": true\n" +
                "}";

        // Send a POST request to order the new pet
       Response res= given()
                .contentType(ContentType.JSON)
                .body(requestBody)
        .when()
                .post("https://petstore.swagger.io/v2/store/order");
               System.out.println(res.asPrettyString());
        res.then()
                .statusCode(200)
                .body("id", equalTo(122))
                .body("petId", equalTo(123))
                .body("quantity", equalTo(1))
                .body("status", equalTo("placed"))
                .body("complete", equalTo(true));
    }



@Test
public void addNewPetToPetStore() {
    // Base URL of the Pet Store API
    RestAssured.baseURI = "https://petstore.swagger.io";

    // Define the new pet details
    String requestBody = "{\n" +
            "  \"id\": 12345,\n" +
            "  \"category\": {\n" +
            "    \"id\": 1,\n" +
            "    \"name\": \"dog\"\n" +
            "  },\n" +
            "  \"name\": \"Pluto\",\n" +
            "  \"photoUrls\": [\n" +
            "    \"https://www.example.com/pluto.jpg\"\n" +
            "  ],\n" +
            "  \"tags\": [\n" +
            "    {\n" +
            "      \"id\": 1,\n" +
            "      \"name\": \"pet\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"status\": \"available\"\n" +
            "}";

    // Send a POST request to add the new pet
    Response res = given()
            .contentType(ContentType.JSON)
            .body(requestBody)
    .when()
            .post("https://petstore.swagger.io/v2/pet");
    System.out.println(res.asPrettyString());
    res.then()
            .statusCode(200)
            .body("id", equalTo(12345))
            .body("name", equalTo("Pluto"))
            .body("category.name", equalTo("dog"))
            .body("photoUrls", hasItem("https://www.example.com/pluto.jpg"))
            .body("tags.name", hasItem("pet"))
            .body("status", equalTo("available"));
}
    @Test
    public void orderSamePetByMultipleUsers() {
        // Base URL of the Pet Store API
        RestAssured.baseURI = "https://petstore.swagger.io";

        // Define the pet ID to order
        long petId = 12345;

        // Define user information
        String[] usernames = {"user1", "user2", "user3", "user4"};

        // Iterate over each user and place an order
        for (String username : usernames) {
            // Define the order details for the user
            String requestBody = "{\n" +
                    "  \"petId\": " + petId + ",\n" +
                    "  \"quantity\": 1,\n" +
                    "  \"status\": \"placed\",\n" +
                    "  \"complete\": true\n" +
                    "}";

            Response res= given()
                    .contentType("application/json")
                    .body(requestBody)
            .when()
                    .post("https://petstore.swagger.io/v2/store/order");
            System.out.println(res.asPrettyString());
            res.then()
                    .statusCode(200)
                    .body("petId", equalTo((int)petId))
                    .body("quantity", equalTo(1))
                    .body("status", equalTo("placed"))
                    .body("complete", equalTo(true));
        }
    }
}

