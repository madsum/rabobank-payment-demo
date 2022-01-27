# Rabobank payment initialization demo

This is a Spring boot Java 11 backend service payment initialization demo application.
There were several requirements such as certificate, signature, iban account, amount, etc. validation.

There was example RSA private key, certificate, signature was given. The example signature is not valid for the given certificate.
That is why I made extra endpoint to make valid certificate for any given.

There are 2 POST endpoint implemented:  

1. POST `localhost:8080/v1.0.0/initiate-payment`  

2. POST  `localhost:8080/v1.0.0/get-signature` Extra implementation in order to get successful response 201 created.

All code is unit tested as well as integration tested. 

### Additionally, the application was dockerized.  

In order to test, launch the service docker-compose up command. Prerequisite testing: docker, docker-compose shall be installed on the host.


