# Rabobank payment initialization demo

This is a Spring boot Java 11 backend service for payment initiation sandbox API used by TPPs.

Following requirements are fulfilled:
- Validating incoming request from TPPs, check amount limit and responds back the defined response.

Input data given by RABO used in this sandbox API:
- Certificate
- PrivateKey

Requirement is to implement an API for payment initiation. Extra endpoint is implemented in order to generate valid signature for the client's payment
request.

There are 2 POST endpoints implemented:  

1. POST `localhost:8080/v1.0.0/initiate-payment`  
- @Parameter(in = ParameterIn.HEADER, description = "" ,required=true) @RequestHeader(value="X-Request-Id", required=true) UUID xRequestId
- @Parameter(in = ParameterIn.HEADER, description = "" ,required=true) @RequestHeader(value="Signature-Certificate", required=true) String signatureCertificate
- @Parameter(in = ParameterIn.HEADER, description = "" ,required=true,schema=@Schema()) @RequestHeader(value="Signature", required=true) String signature
- @Parameter(in = ParameterIn.DEFAULT, description = "The payment initiation request body", required=true) @Valid @RequestBody PaymentInitiationRequest body

2. POST  `localhost:8080/v1.0.0/get-signature` Extra implementation in order to get successful response 201 created.

- @Parameter(in = ParameterIn.HEADER, description = "" ,required=true) @RequestHeader(value="X-Request-Id", required=true) UUID xRequestId
- @Parameter(in = ParameterIn.DEFAULT, description = "The payment initiation request body", required=true) @Valid @RequestBody PaymentInitiationRequest body

All code is unit tested as well as integration tested. 

### How To Run And Test   

git clone https://github.com/madsum/rabobank-payment-demo.git       
cd rabobank-payment-demo

Launch: `mvn spring-boot:run`  
Unit Test: ` mvn test`   


### Additionally, the application is dockerized to simplify the deploying and testing process   

In order to test, launch the service only  `docker-compose up` command. Prerequisite testing: docker, docker-compose shall be installed on the host.
