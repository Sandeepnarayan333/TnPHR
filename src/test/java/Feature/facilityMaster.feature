@Validate
Feature: Validating admin_api_get_facility_master API's


# Send OTP API needs to be Validated


Scenario: Validate OTP with valid number and otp
Given Get ValidateOtp PayLoad 
When user calls "ValidateOTP" with "POST" http request
Then the API call got success with status code 200
And "message" in response body is "User validated succesfully."
And "status" in response body is "SUCCESS" 
And Get the x-access-token from the response body


Scenario: Verify if facility data is being Successfully retrieved using admin_api_get_facility_master API  
Given Get facility payLoad
When user calls "getFacilityMaster" with "POST" http request
Then the API call got success with status code 200
And "message" in response body is "Success retrieving Facility Data."
And "status" in response body is "SUCCESS"

 
Scenario: Verify if Users data is being Successfully retrieved using admin_api_get_user_list API  
Given Get UserList payLoad
When user calls "getUserList" with "POST" http request
Then the API call got success with status code 200
And "message" in response body is "Success retrieving user Data."
And "status" in response body is "SUCCESS-FINAL"






