# new feature
# Tags: optional

Feature: Testing Plan,features & Add-ons

  Scenario Outline: : 200-GET-Retrieve the Addons by MSISDN.
    Given user enter the contact ID.
    When user submit the Get request msisdnEncrypted "<msisdn_encrypted>" as path param
    Then status code  should be <status_code> in response
    And response body addOnsArray size is <size>
    And response addOnsArray should include sku "<sku>"
    And response addOnsArray should include <price>
    And response addOnsArray should isEnabled "<is_enabled>"
    And response addOnsArray should isRemoveable "<is_removeable>"
    Examples:
     | msisdn_encrypted|status_code|size| sku|price|is_enabled |is_removeable|
     |5555550012  | 200        |   6 |Unlimited LD calling and text to USA |10 |true  | true  |


  #Scenario Outline: 400-GET-Retrieve the Addons by invalid msisdn.
   # Given user enter the invalid msisdn or wrong api path.
    #When user submit the Get request msisdnEncrypted  "<msisdn_encrypted>" as path param
    #Then status code  should be <status_code> in the response
    #And response code should be "<code>" in Addons response body
    #And response type should be "<type>" in Addons response body
    #And Description of response should be "<description>"
    #Examples:
     # | contact_id | status_code |code        | type | description |
      #|5555551000  | 404         |not_found   |Error |Sample file is missing (invalid path)|
      #|            | 500         |            |Error |No authenticationScheme was specified, and there was no DefaultChallengeScheme found.|
      #| \u0020     | 400         |invalid_id  |Error |Invalid parameter|
      #| 5555       | 400         |invalid_id  |Error |Invalid MSISDN|

