# Micro Service to Serverless

A continuation on the serverless study from https://github.com/catedradxc/serverlessStudy/tree/main.

This version splits all the endpoints into single functions to take full advantage of the serverless architecture. 

## How to deploy

- Run `mvn clean package`
- Upload to serverless function. 
    - If the .jar is to big upload in s3 first
- Adjust the Handler to point at the LamdaHandler
- Configure API-Gateway 
- Setup DynamoDB tables 
    - item 
    - customer
    - order

