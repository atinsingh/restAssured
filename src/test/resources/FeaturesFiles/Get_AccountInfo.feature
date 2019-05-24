# new feature
# Tags: optional

Feature: Testing AccountINfo API

  Scenario Outline: GET-Retrieves the account info with invalid id.
    Given user enter the invalid contactId with wrong api path.
    When User calls account info with  "<contact_id>" as path param
    Then status code  should be <status_code> in the response
    And response code should be "<code>" in response body
    And response type should be "<type>" in response body
    And Description of response should be "<description>"
    Examples:
      | contact_id | status_code |code        | type | description |
      | 100112     | 404         |not_found   |Error|Account info not found|
      |            | 500         |            |Error|No authenticationScheme was specified, and there was no DefaultChallengeScheme found.|
      |  \u0020    | 400         |invalid_id  |Error|Invalid parameter|
#
#      Scenario Outline : GET-200-Retrieves Account info by person contact ID.

Scenario Outline: Get-AccountInfo with ContactID
  Given user enter the contact ID.
  When user submit the Get request contactID "<contact_id>" as path param
  Then status code  should be <status_code> in the response
  And response body includes the serviceItemArray
  And response serviceItemArray should include "<items>"
  And response serviceItem should include userType "<user_type>"
  Examples:
    | contact_id | status_code | items      | user_type |
    | 10000011  |        200  |  5555550012| DigitalAO |
    | 10000012  |        200  |  5555550012| DigitalAU |
    | 10000013  |        200  |  5555550013| DigitalDU |