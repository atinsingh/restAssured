Feature: Get Authentication Token
  Scenario: User calls api to its auth token with username and password
    Given a user exists with an username "admin" and password "admin"
    When a user authenticate by username and password
    Then the status code is 200
      And response includes the following
      | id_token 	 	    |   				|
