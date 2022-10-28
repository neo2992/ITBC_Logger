# ITBC_Logger
The final project for IT Bootcamp. Implementation of simple logger Rest API with Springboot, JPA, Microsoft SQL database.
Endpoints
Register
HTTP Method: 'POST'
Enpoint URL: '/api/clients/register'
Request body:
{
  "username":"string",
  "password":"string",
  "email":"string",
  "logType":"admin"
}
Responses:
201 - Registered
400 - Bad Request
email must be valid
username at least 3 characters
password at least 8 characters and one letter and one number and one special
409 - Conflict
username already exist
email already exist
Login
HTTP Method: 'POST'
Endpoint URL: '/api/clients/login'
Request body:
{
  "username": "string",
  "password": "string"
}
Responses:
200 - OK
{
    "token":"string"
 }
400 - Bad Request -username or password incorect
Create log
HTTP Method: 'POST'
Endpoint URL: '/api/logs/create'
Request body:
{
  "message":"string",
  "logType": 0
}
Request headers:
'Authorization' - token
Responses:
201 - Created
400 - Bad Request -Incorrect logType
401 - Unauthorization -Incorrect token
413 - Payload too large -Message should be less than 1024 character
Search Log
HTTP Method: 'GET'
Endpoint URL: '/api/logs/search'
Request params:
'dateFrom'
'dateTo'
'message'
'logType'
Request headers:
'Authorization' - token
Responses:
200 - OK
{
  "message":"string",
  "logType":0,
  "dateFrom":"date",
  "dateTo":"date"
}
400 - Bad request
Invalid dates
Invalid logType
401 - Unauthorized
Incorrect token
Get all clients
HTTP Method: 'GET'
Endpoint URL: '/api/clients'
Request headers:
'Authorization' - token (Admin token)
Responses:
200 - OK
[
  {
    "id":"uuid",
    "username":"string",
    "email":"string",
    "logCount":0
  }
]
401 - Unauthorized
Incorrect token
403 - Frobidden
Correct token, but not admin
Change client password
HTTP Method: 'PATCH'
Endpoint URL: '/api/clients/{id}/reset-password'
Request body:
  {
    "password":"string"
  }
Request headers: -'Authorization' - token (Admin token or exact user token)
Responses:
204 - No content
401 - Unauthorized
Correct token, but not admin
403 - Forbidden
Incorrect token
