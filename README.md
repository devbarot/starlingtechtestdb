# Read Me: Starling Bank technical test

#Task:
- Develop a “round-up” feature for Starling customers using Starling Bank's public
  developer API that is available to all customers and partners.
  
#Tech used:
- Java 8
- Spring Web
- Lombok

#Assumptions:
- All the transactions are in GBP
- Transactions are mostly integers

#Approach/Things done
- Used an MVC design to essentially allow requests to be made via Postman/Curl in order to interact
with the API.
- Implemented additional features that allow users to retrieve all goals, individual goals and create new goals.
- Used application.properties file to implement URLs to the relevant APIs provided by Starling Bank.
- All code tested with nearly 100% test coverage (apart from Bean in CoreUtils file).
- Used Lombok to remove boilerplate code in POJOs.


#How to use this API

Generate a bearer token from the developer sandbox on Starling Bank's API portal and place in 
- ```src/main/resources/application.properties```
  

Build the project using:

- ```mvn clean install```

Run the project with the following command (reverse forward slash to backslashes for Windows computers):

- ```java -jar ./target/starlingtechtestdb-0.0.1-SNAPSHOT.jar```


1) Open Postman and make a PUT request to the following URL (ensure Bearer token is available in Postman under 
Authorization):
- ```http://localhost:8080/create-goal```

To get all savings goals, use the following URL (ensure Bearer token is available in Postman under
Authorization). Replace {accountUid} with the UUID of the user's main account:
- ```https://api-sandbox.starlingbank.com/api/v2/account/{accountUid}/savings-goals```


2) To get individual savings goals, use the following URL (ensure Bearer token is available in Postman under
Authorization). Replace {accountUid} and {savingsGoalUid} with the variables retrieved form the call above:
- ```https://api-sandbox.starlingbank.com/api/v2/account/{accountUid}/savings-goals/{savingsGoalUid}```

3) To calculate savings for a transaction, do a GET request to the following URL (ensure Bearer token is 
available in Postman under Authorization). Replace {changesSince} with a timestamp to get all transactions since 
that timestamp to now. Use `2020-01-01T12:34:56.000Z`as an example:
- ```http://localhost:8080/calculate?changesSince={changesSince}```

4) Finally, to see the amount of savings once the above call has been made, retrieve the individual savings account 
again as instructed in Step 3. You should now see the values under `totalSaved` and `savedPercentage` have changed.

#What I would do to enhance the API
- Implement a way to manage the refresh token a bit better if possible
- Implement error handling
- Refactor tests to clean up repetitive code
- Fix naming conventions for classes and variables
- Implement controllers to get all savings goals etc. directly via the API to remove the need to make calls to the 
  sandbox API link and retrieve data (UUIDs) from them