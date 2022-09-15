package stepDefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import resources.ApiResources;
import resources.PayLoad;
import resources.Utils;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

public class stepDefinition extends Utils {
	
	RequestSpecification request;
	Response response;
	static String xtoken;
	static String USER_ID;
	static String USER_PHR_ROLE;
	static String 	USER_FACILITY_ID;
	PayLoad payload = new PayLoad();
	
	@Given("Get facility payLoad")
	
	public void get_facility_pay_load() throws IOException {
	   
		request = given().spec(RequestSpecification()).body(payload.facilityMasterPayLoad());
		
	}
	
	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resourceName, String httpMethod) {
	 
		ApiResources enumResource = ApiResources.valueOf(resourceName);
		
	   if(httpMethod.equalsIgnoreCase("POST")) {
		   
		response = request.when().post(enumResource.getResource());
	   }
	   
	   else if(httpMethod.equalsIgnoreCase("GET"))
	   {
		   
		   response = request.when().get(enumResource.getResource());
	   }
	}
	
	@Then("the API call got success with status code {int}")
	public void the_api_call_got_success_with_status_code(int statusCode) {
	    assertEquals(response.getStatusCode(), statusCode);
	    
	}
	
	@Then("{string} in response body is {string}")
	public void in_response_body_is(String key, String value) {
	 
		assertEquals(getJsonPath(response, key), value);
		 
	}
	
	 @Then("^Get the x-access-token from the response body$")
	 public void get_the_xaccesstoken_from_the_response_body() throws Throwable {
	      String xtoken = getJsonPath(response, "data[0].token");
	      System.out.println("The token is " + xtoken);
	      USER_ID = getJsonPath(response, "data[0].user_details.user_id");
	      System.out.println("The User ID is "+USER_ID);
	      USER_PHR_ROLE = getJsonPath(response, "data[0].user_details.phr_role");
	      System.out.println("The UserPhrRole is "+USER_PHR_ROLE);
	      USER_FACILITY_ID = getJsonPath(response, "data[0].user_details.facility_id");
	      System.out.println("The UserFacilityId is "+USER_FACILITY_ID);
	      
	    }
	 
	 
	 
	 @When("User adds required payload and calls {string} with {string} http request")
	 public void user_adds_required_payload_and_calls_with_http_request(String resourceName, String httpMethod) throws IOException {
	     
		 get_the_payload_for_steer_aggregate_api();
		 ApiResources enumResource = ApiResources.valueOf(resourceName);
			
		   if(httpMethod.equalsIgnoreCase("POST")) {
			   
			response = request.when().post(enumResource.getResource());
		   }
		   
		   else if(httpMethod.equalsIgnoreCase("GET"))
		   {
			   
			   response = request.when().get(enumResource.getResource());
		   }
		 
	 }

	
//-----------------------------------------------------------------------------------------------------------------------------
	
	@Given("Get ValidateOtp PayLoad")
	public void get_validate_otp_pay_load() throws IOException {
	    
		request=given().spec(RequestSpecification()).body(payload.validateOTPPayLoad(getGlobalProperties("number"), getGlobalProperties("otp")));
				
	}
	
//---------------------------------------------------------------------------------------------------------------------------
	 @Given("^Get UserList payLoad$")
	 public void get_userlist_payload() throws Throwable {
	       
		 String paylod = payload.getUserListPayLoad(USER_ID, USER_PHR_ROLE, USER_FACILITY_ID);
		 System.out.println(paylod);
		 request=given().spec(RequestSpecification()).body(payload.getUserListPayLoad(USER_ID, USER_PHR_ROLE, USER_FACILITY_ID));
		 
	    }
	
	//---------------------------------------------------------------------------------------------------------------------------
	
	 @Given("Get the payload for steerAggregate Api")
	 public void get_the_payload_for_steer_aggregate_api() throws IOException {
		 
	     
		 request=given().spec(RequestSpecification()).body(payload.PayLoadForShopStreetAggregator(getGlobalProperties("userid")
				                                           ,getGlobalProperties("phrrole"), getGlobalProperties("userfacilityid")));
		 
	 }

}
