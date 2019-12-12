# STFU API test

Developed in Java with Rest Assured 

### Execution
`mvn clean test` - to run from command line. Also you may run them inside the IDE.

During the execution all request/recieve logs will be in the terminal window.

##### /src/test/java/com/stfu/api/ folder:

 `Constants.java` - Constants URIs, Paths;
 
 `STFUAPITests.java` - Api Test file;
 
 `RequestHelper.java` - helper for set base urls, etc.
 
 #### /src/test/resources folder:
 
 Schemas of JSON response:
 * `getLeaderBoardJSONSchema.json` - get current leaderboard , `GET /leaderboard` request 
 * `postClickResponseJSONSchema.json` - send 1 click for teaam, `POST /players/{userId}` request 
 
