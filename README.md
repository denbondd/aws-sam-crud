# AWS SAM CRUD

Simple Java application to make basic DB requests. Build fully on AWS services and used Serverless Application Model for fast deployment of infrastructure. Spring was used for Dependency Injection.  

## Project stack:
- Java 11
- Spring Core 
- Spring Context
- Lombok
- AWS SDKs

## Schema
<img src="https://i.imgur.com/0tYC1pS.png" width="600" > <br>
Amazon API Gateway routes requests to lambdas, which then do operations in DynamoDB according to request.

## Set up project
Since we have SAM template, you can deploy the app to your AWS account via few commands:
> You need to have Git, Gradle and SAM CLI installed on your machine
```
git clone https://github.com/denbondd/aws-sam-crud.git
cd aws-sam-crud
sam build
sam deploy --guided
```

## License
Copyright (c) 2021 Denys Bondarenko <br>
<a href="./LICENSE">MIT License</a>