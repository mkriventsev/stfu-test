@FunctionalTest
Feature: STFUANDCLICK application

  @SmokeTest
  Scenario: Home page is open
    Given I am at the Home page
    Then Leaderboard is shown on "Home" page
    And Team name input works
    And All buttons are clickable on "Home" page

  @SmokeTest
  Scenario Outline: Team page "<TeamName>" is open
    Given I am at the "<TeamName>" Team page
    Then All buttons are clickable on "Team" page
    And Leaderboard is shown on "Team" page

    Examples:
      | TeamName         |
      | IwillTestYourApp |
      | YourTestApp      |

  @SmokeTest @RegressionTest
  Scenario Outline: Type a team <TeamName> name, click the CLICK button and I will be at Team page
    Given I am at the Home page
    When I type "<TeamName>" team name
    And Press the CLICK buttom
    Then I should be at the "<TeamName>" team page

    Examples:
      | TeamName         |
      | IwillTestYourApp |
      | YourTestApp      |


  @RegressionTest #STFU-15
  Scenario Outline: Open one link in 2 tabs and clicking
    Given I am at the "<TeamName>" Team page
    And I open second tab with "<TeamName>" Team page
    When I press the click button "<number>" times on the 1 tab
    Then team clicks counter shows incremented value by "<number>" in the 1 tab
    And team clicks in leaderboard incremented by "<number>" in the both tabs
    And team clicks counter shows incremented value by "<number>" in the 2 tab
    #STFU-15 reverse
    Then I press the click button "<number>" times on the 2 tab
    And team clicks counter shows incremented value by "<number>" in the 2 tab
    And team clicks in leaderboard incremented by "<number>" in the both tabs
    Then team clicks counter shows incremented value by "<number>" in the 1 tab

    Examples:
      | TeamName         | number |
      | IwillTestYourApp | 10     |
      | YourTestApp      | 2      |
