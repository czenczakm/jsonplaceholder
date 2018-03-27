# Test Plan /post resource

`endpoint=https://jsonplaceholder.typicode.com`

# GET
<br />**Scenario: Verify the status code for existing request**
<br />**When** User send GET to `endpoint/posts`
<br />**Then** Status code is 200

<br />**Scenario: Verify the status code for not existing request**
<br />**When** User send GET to `endpoint/post`
<br />**Then** Status code is 404

<br />**Scenario: Validate the JSON Schema for GET request /posts**
<br />**Given** The JSON Schema is:
```
{
  "type": "array",
  "items": {
    "type": "object",
    "required": [
      "userId",
      "id",
      "title",
      "body"
    ],
    "properties": {
      "userId": {
        "type": "number"
      },
      "id": {
        "type": "number"
      },
      "title": {
        "type": "string"
      },
      "body": {
        "type": "string"
      }
    }
  }
}
```
**When** User send GET to `endpoint/post`
<br />**Then** Request body matches JSON Schema

<br />**Scenario: Verify the status code for existing post resource**
<br />**Given** Post resource with `id=1` exists
<br />**When** User send GET to `endpoint/posts/1`
<br />**Then** Status code is 200

<br />**Scenario: Verify the status code for not existing post resource**
<br />**Given** Post resource with `id=0` doesn't exists
<br />**When** User send GET to `endpoint/posts/0`
<br />**Then** Status code is 404

<br />**Scenario: Verify that response media type is JSON for existing post resource**
<br />**Given** Post resource with `id=1` exists
<br />**When** User send GET to `endpoint/posts/1`
<br />**Then** Response Mime Type is `application/json`

<br />**Scenario: Verify that response for existing post resource is correct**
<br />**Given** Post resource with `id=1` exists
<br />**When** User send GET to `endpoint/posts/1`
<br />**Then** The body response contains: `"id": 1`

<br />**Scenario: Validate the JSON Schema for GET request /posts/1/comments**
<br />**Given** Post resource with `id=1` exists
<br />**And** The JSON Schema is:
```
{
  "type": "array",
  "items": {
    "type": "object",
    "required": [
      "postId",
      "id",
      "name",
      "email",
      "body"
    ],
    "properties": {
      "postId": {
        "type": "number"
      },
      "id": {
        "type": "number"
      },
      "name": {
        "type": "string"
      },
      "email": {
        "type": "string"
      },
      "body": {
        "type": "string"
      }
    }
  }
}
```
**When** User send GET to `endpoint/posts/1/comments`
<br />**Then** Request body matches JSON Schema

<br />**Scenario: Verify that correct comments are returned for existing post resource**
<br />**Given** Post resource with `id=1` exists
<br />**When** User send GET to `endpoint/comments?postId=1`
<br />**Then** The body response contains: `"postId": 1`

<br />**Scenario: Verify that correct post resources are returned for given userId**
<br />**Given** User with `userId=1` exists
<br />**When** User send GET to `endpoint/posts?userId=1`
<br />**Then** The body response contains: `"userId": 1`


# POST
<br />**Scenario: Verify if failed to create the post resource with duplicate id**
<br />**Given** Post resource with `id=1` exists
<br />**When** User send POST to `endpoint/posts` with JSON body:
```
{
    "userId": 1,
    "id": 1,
    "title": "some title",
    "body": "some body"
}
```
**Then** Status code is 500
<br />**And** Received error message is: "Error: Insert failed, duplicate id"

<br />**Scenario: Verify if able to create new post resource**
<br />**Given** Post resource with `id=101` doesn't exists
<br />**When** User send POST to `endpoint/posts` with JSON in body:
```
{
    "userId": 1,
    "id": 101,
    "title": "some tilte",
    "body": "some body"
}
```
**Then** Status code is 201
<br />**And** In response user get the same JSON as in POST request


# PUT
<br />**Scenario: Verify if failed to update not existing post resource using PUT method**
<br />**Given** Post resource with `id=0` doesn't exists
<br />**When** User send PUT to `endpoint/posts/0`
<br />**Then** Status code is 404

<br />**Scenario: Verify if able to update existing post resource using PUT method (one field)**
<br />**Given** Post resource with `id=1` exists
<br />**When** User send PUT to `endpoint/posts/1` with JSON in body:
```
{
	    "body": "new title"
}
```
**Then** Status code is 200
<br />**And** User receive response containing JSON only updated body and id:
```
{
    "body": "new tilte",
    "id": 1
}
```

<br />**Scenario: Verify if able to update existing post resource using PUT method (all fields)**
<br />**Given** Post resource with `id=1` exists
<br />**When** User send PUT to `endpoint/posts/1` with JSON in body:
```
{
    "userId": 1,
    "id": 1,
    "title": "new title",
    "body": "new body"
}
```
**Then** Status code is 200
<br />**And** User receive response containing JSON:
```
{
    "userId": 1,
    "id": 1,
    "title": "new title",
    "body": "new body"
}
```

# PATCH
<br />**Scenario: Verify if failed to update not existing post resource using PATCH method**
<br />**Given** Post resource with `id=0` doesn't exists
<br />**When** User send PATCH to `endpoint/posts/0`
<br />**Then** Status code is 404

<br />**Scenario: Verify if able to update existing post resource using PATH method**
<br />**Given** Post resource with `id=1` exists
<br />**When** User send PATCH to `endpoint/posts/1` with JSON in body:
```
{
	    "body": "new title"
}
```
**Then** Status code is 200
<br />**And** User receive response containing JSON with updated post resource body:
```
{
    "userId": 1,
    "id": 1,
    "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
    "body": "new tilte"
}
```

# DELETE
<br />**Scenario: Verify if failed to delete not existing post resource**
<br />**Given** Post resource with `id=0` doesn't exists
<br />**When** User send DELETE to `endpoint/posts/0`
<br />**Then** Status code is 404

<br />**Scenario: Verify if able to remove existing post resource**
<br />**Given** Post resource with `id=1` exists
<br />**When** User send DELETE to `endpoint/posts/1`
<br />**Then** Status code is 200
