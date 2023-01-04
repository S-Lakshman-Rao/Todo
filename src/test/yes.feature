Feature: Get feature

  blah blah blah

  Scenario Outline: blah
    Given I populate tasks
    When I fire a GET request to fetch all tasks
    Then I should get a response with HTTP status code <status>
    Examples:
      | status |
      |  200   |

  Scenario Outline: Deletion
    Given I populate tasks
    Given I Delete Task
    When I Delete a already deleted task
    Then I should get a response an exception <statuss>

    Examples:
      | statuss |
      | "org.springframework.dao.EmptyResultDataAccessException: No class com.example2.demo2.model.TodoItem entity with id 1 exists"  |


