@Newtag
Feature: End to end testing using Scenarios adios palke


Scenario: End to End scenario of api dashboard street and shop aggregate
Given Get the payload for steerAggregate Api
When user calls "APIStreetAggregate" with "POST" http request
Then the API call got success with status code 200 
And "message" in response body is "Success retrieving user Data."

When User adds required payload and calls "APIShopAggregate" with "POST" http request
Then the API call got success with status code 200
And "message" in response body is "Success retrieving Shop Data."

