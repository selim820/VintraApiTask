

  Feature: Main functionalities

    @wip
    Scenario: User should be able to authenticate
      Given User provides valid username as "test" and  password as "1234"
     # When User logs out
      Then Status code should be 200
      Given User logins with valid token
      Then Status code should be 200
      #Given User logs out
      Then Status code should be 200
      Given  User stores new contact data firstname as "che",lastname as "aw"
       Then Status code should be 201
      Given User retrieves all contacts data previously created
      Then Status code should be 200
      #Given User can not create new contact for the same Firstname and same Lastname
      Given User retrieves contacts based on id
      Then Status code should be 200
      Given User updates any contact based on id,and updates name as "se",lastname as "se"
      Then Status code should be 200
      Given User retrieves contacts firstName as "se",last_name as "se"
      Then Status code should be 200
      Given User deletes contact based on id
      Then Status code should be 204
      # Given User logs out
      And User should not be perform any action

