# ITBC_Logger
The final project for IT Bootcamp. Implementation of simple logger Rest API with Springboot, Spring Data JPA, Spring Data JDBC, Microsoft SQL database.
# Endpoints
1. **Register**


- HTTP Method: 'POST'<br/>
- Endpoint URL: '/api/clients/register'<br/>
- Request body:
{
  "username":"string",
  "password":"string",
  "email":"string",
  "logType":"admin"
}<br/>
Responses:<br/>
201 - Registered<br/>
400 - Bad Request<br/>
email must be valid<br/>
username at least 3 characters<br/>
password at least 8 characters and one letter and one number and one special<br/>
409 - Conflict<br/>
username already exist<br/>
email already exist<br/>


**Login**


HTTP Method: 'POST'<br/>
Endpoint URL: '/api/clients/login'<br/>
Request body:
{
  "username": "string",
  "password": "string"
}<br/>
Responses:<br/>
200 - OK<br/>
{
    "token":"string"
 }<br/>
400 - Bad Request -username or password incorect<br/>
**Create log**


HTTP Method: 'POST'<br/>
Endpoint URL: '/api/logs/create'<br/>
Request body:
{
  "message":"string",
  "logType": 0
}<br/>
Request headers:
'Authorization' - token
Responses:<br/>
201 - Created<br/>
400 - Bad Request -Incorrect logType<br/>
401 - Unauthorization -Incorrect token<br/>
413 - Payload too large -Message should be less than 1024 character<br/>


**Search Log**


HTTP Method: 'GET'<br/>
Endpoint URL: '/api/logs/search'<br/>
Request params:
'dateFrom'
'dateTo'
'message'
'logType'<br/>
Request headers:
'Authorization' - token<br/>
Responses:<br/>
200 - OK
{
  "message":"string",
  "logType":0,
  "dateFrom":"date",
  "dateTo":"date"
}<br/>
400 - Bad request<br/>
Invalid dates<br/>
Invalid logType<br/>
401 - Unauthorized<br/>
Incorrect token<br/>


**Get all clients**


HTTP Method: 'GET'<br/>
Endpoint URL: '/api/clients'<br/>
Request headers:
'Authorization' - token (Admin token)<br/>
Responses:<br/>
200 - OK
[
  {
    "id":"uuid",
    "username":"string",
    "email":"string",
    "logCount":0
  }
]<br/>
401 - Unauthorized<br/>
Incorrect token<br/>
403 - Frobidden<br/>
Correct token, but not admin<br/>


**Change client password**


HTTP Method: 'PATCH'<br/>
Endpoint URL: '/api/clients/{id}/reset-password'<br/>
Request body:
  {
    "password":"string"
  }<br/>
Request headers: -'Authorization' - token (Admin token or exact user token)<br/>
Responses:<br/>
204 - No content<br/>
401 - Unauthorized<br/>
Correct token, but not admin<br/>
403 - Forbidden<br/>
Incorrect token<br/>
