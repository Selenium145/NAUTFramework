@API @Rest
Feature: Rest API Calls for all basic operations

  @td_RestPost
  Scenario: Verification of POST method    
    Given user creates client instance for api call
    When user execute "POST" request
    Then user verifies response code as "201"
    And user verifies generated id
    And user verifies response "PostRequestBody" body  
  
  @API_Get
  Scenario: Verification of GET method    
    Given user creates client instance for api call
    When user execute "GET" request
    Then user verifies response code as "200"
    #And user verifies response "GetRequestBody" body

	@API_Put
  Scenario: Verification of PUT method    
    Given user creates client instance for api call
    When user execute "PUT" request
    Then user verifies response code as "200"
    And user verifies response "PutRequestBody" body   

	@API_Delete
  Scenario: Verification of DELETE method    
    Given user creates client instance for api call
    When user execute "DELETE" request
    Then user verifies response code as "204"    
    #And user resets client parameters