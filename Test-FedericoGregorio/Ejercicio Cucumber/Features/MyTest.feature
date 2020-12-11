@tag
Feature: Google Homepage Search
  I want to use this template for my feature file

@tag1
Scenario: User can search with “Google Search”
Given I am on the homepage "1" "0"
	When I type 'The name of the wind' into the search field
		And I click the Google Search button
	Then I go to the search results page
		And the first result is 'El nombre del viento - Wikipedia, la enciclopedia libre'
	When I click on the first result link
	Then I go to the 'Wikipedia' page
	
@tag2
Scenario: User can search by using the suggestions - Error
Given I am on the homepage "2" "5"
	When I type 'The name of the w' into the search field
		And the suggestions list is displayed
		And I click on the first suggestion in the list
	Then I go to the search results page
		And the first result is 'The Name of the Wind - Patrick Rothfuss'
	When I click on the first result link
		Then I go to the 'Patrick Rothfuss - The Books' page
		
@tag3
Scenario: User can search with “Google Search” - Error
Given I am on the homepage "3" "12"
	When I type 'The name of the wind' into the search field
		And I click the Google Search button
	Then I go to the search results page
		And the first result is 'The Name of the Wind - Patrick Rothfuss'
	When I click on the first result link
	Then I go to the 'Patrick Rothfuss - The Books' page