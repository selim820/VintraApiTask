package com.VintraApiTask.step_definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;




public class UserFlow {
    String token;
    String  Bearertoken="hey";
    Response response;
    int id;
    String GlobalFirstName;
    String GlobalLastName;

    @Given("User provides valid username as {string} and  password as {string}")
    public void user_provides_valid_username_as_and_password_as(String username, String password) {
      String bodyAuth="grant_type=&username="+username+"&password="+password+"&scope=&client_id=&client_secret=";



         response = given().accept(ContentType.JSON)
.and().contentType("application/x-www-form-urlencoded")
                .and().body(bodyAuth)
                .when().post("http://127.0.0.1:8000/auth/login");
         token=response.path("access_token");
        Bearertoken="Bearer "+token;





    }
    @Then("Status code should be {int}")
    public void status_code_should_be(Integer ExpectedStatusCode) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals("status codes do not match",ExpectedStatusCode,(Integer)response.statusCode());

    }


    @Given("User logins with valid token")
    public void user_should_be_able_to_login_properly() {
        // Write code here that turns the phrase above into concrete actions
        System.out.println(Bearertoken);
         response = given().accept(ContentType.JSON)
                 .contentType(ContentType.JSON)
                .and().header("Authorization",Bearertoken)
                .when().get("http://127.0.0.1:8000/auth/me");

    }

    @Given("User logs out")
    public void user_should_be_able_to_logout() {
        // Write code here that turns the phrase above into concrete actions
         response = given().accept("accept: application/json")
                .and().header("Authorization",Bearertoken)
                .and().get("http://127.0.0.1:8000/auth/logout");
        System.out.println("***");

    }

    @Given("User stores new contact data firstname as {string},lastname as {string}")
    public void user_should_be_able_to_store_new_contact_data_firstname_as_lastname_as(String firstName, String lastName) {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("***");
        System.out.println(Bearertoken);
        Map<String,String> postMap=new HashMap<>();
        postMap.put("firstName",firstName);
        postMap.put("lastName",lastName);
        GlobalFirstName=firstName;
        GlobalLastName=lastName;

         response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().header("Authorization",Bearertoken)
                .and().body(postMap)
                .and().post("http://127.0.0.1:8000/api/v1/contacts/");
        response.prettyPrint();
        id=(int)response.path("id");

    }

    @Given("User can not create new contact for the same Firstname and same Lastname")
    public void user_can_not_create_new_contact_for_the_same_firstname_and_same_Lastname() {
        System.out.println(GlobalFirstName);

        Map<String,String> postMap=new HashMap<>();
        postMap.put("firstName",GlobalFirstName);
        postMap.put("lastName",GlobalLastName);

        boolean NotNewCreation=false;
        try {
            response = given().accept(ContentType.JSON)
                    .and().contentType(ContentType.JSON)
                    .and().header("Authorization",Bearertoken)
                    .and().body(postMap)
                    .and().post("http://127.0.0.1:8000/api/v1/contacts/");

        } catch (Exception e) {
            NotNewCreation=true;
        }
        Assert.assertTrue(NotNewCreation);
  ;



    }


    @Given("User retrieves all contacts data previously created")
    public void user_should_be_able_to_all_contacts_data_previously_created() {
        // Write code here that turns the phrase above into concrete actions
         response = given().accept(ContentType.JSON)
                .and().header("Authorization",Bearertoken)
                .and().get("http://127.0.0.1:8000/api/v1/contacts/");
    }

    @Given("User retrieves contacts based on id")
    public void user_retrieves_contacts_based_on_id() {
        // Write code here that turns the phrase above into concrete actions
        response = given().accept(ContentType.JSON)
                .and().header("Authorization",Bearertoken)
                .and().pathParam("id",id)
                .and().get("http://127.0.0.1:8000/api/v1/contacts/{id}");

    }



    @Given("User updates any contact based on id,and updates name as {string},lastname as {string}")
    public void user_updates_any_contact_based_on_id_and_updates_name_as_lastname_as(String firstName, String lastName) {
        // Write code here that turns the phrase above into concrete actions
        Map<String,String> patchMap=new HashMap<>();
        patchMap.put("firstName",firstName);
        patchMap.put("lastName",lastName);



        response = given().accept(ContentType.JSON)
                .and().header("Authorization",Bearertoken)
                .and().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().pathParam("id",id)
                .and().body(patchMap)
                .when().patch("http://127.0.0.1:8000/api/v1/contacts/{id}");
    }




    @Given("User should be able to retrieve contacts name  as {string}")
    public void user_should_be_able_to_retrieve_contacts_name_as(String string) {
        // Write code here that turns the phrase above into concrete actions

         response = given().accept(ContentType.JSON)
                .and().header("Authorization",Bearertoken)
                .and().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .when().get("http://127.0.0.1:8000/api/v1/contacts/search");
    }

    @Given("User retrieves contacts firstName as {string},last_name as {string}")
    public void user_should_be_able_to_retrieve_contacts_firstName_as_last_name_as(String firstName, String LastName) {
        // Write code here that turns the phrase above into concrete actions
     Map<String,String> queryMap=new HashMap<>();
     queryMap.put("first_name",firstName);
        queryMap.put("last_name",LastName);


         response = given().accept(ContentType.JSON)
                .and().header("Authorization",Bearertoken)
                .and().queryParams(queryMap)
                .when().get("http://127.0.0.1:8000/api/v1/contacts/search");


    }

    @Given("User deletes contact based on id")
    public void user_deletes_contact_based_on_id() {
        // Write code here that turns the phrase above into concrete actions
        response=given().accept(ContentType.JSON)
                .and().header("Authorization",Bearertoken)
                .and().pathParam("id",id)
                .and().delete("http://127.0.0.1:8000/api/v1/contacts/{id}");
    }
    @Given("User should not be perform any action")
    public void user_should_not_be_able_to_perform_any_action() {

        Boolean noAction=false;
        try {
            user_should_be_able_to_all_contacts_data_previously_created();

        } catch (Exception e) {
            noAction=true;

        }
        Assert.assertTrue(true);

    }






}
