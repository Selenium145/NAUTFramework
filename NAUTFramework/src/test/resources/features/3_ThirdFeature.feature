@ThirdFeatureFile
Feature: Third Application Login
  User should able to login to application with username and password.

  @UI
  Scenario: Login to test app 4 using invalid credentials
  	Given user launches the browser
  
    Given I am on the TestApp login page
    When I enter "UserName" in the email field
    And I enter "Password" in the password field
    And I press Login button
    Then I should be on the TestApp Home page
    And I should see my FirstName "Vinoth" in user title
    
    And I press Logout button
    And user closes the browser
     