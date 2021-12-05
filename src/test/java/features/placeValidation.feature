Feature: Validating Place API's
@AddPlace @Regression
  Scenario Outline: Verify if Place is being Successfully added using AddPlaceAPI
    Given Add Place Payload "<name>" "<language>" "<address>"
    When user calls "addPlaceApi" with "POST" request
    Then the API call got success with status code 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"
    And verify place_Id created maps to "<name>" using "getPlaceApi"

  Examples:
   | name | language | address  |
   | Ahosy| english  | Word wide|
 #  | bbhose| Spanish |Sea cross |
@DeletePlace @Regression
  Scenario: Verify if delete place functionality is working
    Given DeletePlace Payload
    When user calls "deletePlaceApi" with "POST" request
    Then the API call got success with status code 200
    And "status" in response body is "OK"
