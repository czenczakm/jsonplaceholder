Feature: Post resource

#  Using JSONPlaceholder Fake Online REST API, all precondition steps are matched, so not implementing them in the scenarios :
#    - Post resource with id=1 exists in db
#    - Post resource with id=0 doesn't exists in db
#    - Post resource with id=101 doesn't exists in db
#    - User with userId=1 exists in db

  #GET

  Scenario: Verify the status code for existing request
    When User send GET to "/posts"
    Then Status code is 200

  Scenario: Verify the status code for not existing request
    When User send GET to "/post"
    Then Status code is 404

  Scenario: Validate the JSON Schema for GET request /posts
    When User send GET to "/posts"
    Then Request body matches "JSON" Schema "posts-schema.json"

  Scenario: Verify the status code for existing post resource
    When User send GET to "/posts/1"
    Then Status code is 200

  Scenario: Verify the status code for not existing post resource
    When User send GET to "/posts/0"
    Then Status code is 404

  Scenario: Verify that response media type is JSON for existing post resource
    When User send GET to "/posts/1"
    Then Response Mime Type is "application/json"

  Scenario: Verify that response for existing post resource is correct
    When User send GET to "/posts/1"
    Then The body response contains:
      | id | 1 |

  Scenario: Validate the JSON Schema for GET request /posts/1/comments
    When User send GET to "/posts/1/comments"
    Then Request body matches "JSON" Schema "comments-schema.json"

  Scenario: Verify that correct comments are returned for existing post resource
    When User send GET to "/comments?postId=1"
    Then The body response contains:
      | postId | 1 |

  Scenario: Verify that correct post resources are returned for given userId
    When User send GET to "/posts?userId=1"
    Then The body response contains:
      | userId | 1 |


  #PUT
  Scenario: Verify if failed to update not existing post resource using PUT method
    When User send PUT to "/posts/0"
    Then Status code is 404


  #PATCH
  Scenario: Verify if failed to update not existing post resource using PATCH method
    When User send PATCH to "/posts/0"
    Then Status code is 404


  #DELETE
  Scenario: Verify if failed to delete not existing post resource
    When User send DELETE to "/posts/0"
    Then Status code is 404

  Scenario: Verify if able to remove existing post resource
    When User send DELETE to "/posts/1"
    Then Status code is 200