# Rabobank payment initialization demo

This is a Spring boot Java 11 backend service payment initialization demo application.
There were several requirements such as certificate, signature, iban account, amount, etc. validation.

There was example RSA private key, certificate, signature was given. The requirement was to only implement 1 API for payment initiation.
I made extra 1 endpoint to make valid certificate for any given payment.

There are 2 POST endpoint implemented:  

1. POST `localhost:8080/v1.0.0/initiate-payment`  
- @Parameter(in = ParameterIn.HEADER, description = "" ,required=true) @RequestHeader(value="X-Request-Id", required=true) UUID xRequestId
- @Parameter(in = ParameterIn.HEADER, description = "" ,required=true) @RequestHeader(value="Signature-Certificate", required=true) String signatureCertificate
- @Parameter(in = ParameterIn.HEADER, description = "" ,required=true,schema=@Schema()) @RequestHeader(value="Signature", required=true) String signature
- @Parameter(in = ParameterIn.DEFAULT, description = "The payment initiation request body", required=true) @Valid @RequestBody PaymentInitiationRequest body

2. POST  `localhost:8080/v1.0.0/get-signature` Extra implementation in order to get successful response 201 created.

- @Parameter(in = ParameterIn.HEADER, description = "" ,required=true) @RequestHeader(value="X-Request-Id", required=true) UUID xRequestId
- @Parameter(in = ParameterIn.DEFAULT, description = "The payment initiation request body", required=true) @Valid @RequestBody PaymentInitiationRequest body

All code is unit tested as well as integration tested. 

### Additionally, the application was dockerized.  

In order to test, launch the service only  `docker-compose up` command. Prerequisite testing: docker, docker-compose shall be installed on the host.

Quick curl request example:-   

1. POST `localhost:8080/v1.0.0/initiate-payment`
- @Parameter(in = ParameterIn.HEADER, description = "" ,required=true) @RequestHeader(value="X-Request-Id", required=true) UUID xRequestId
- @Parameter(in = ParameterIn.HEADER, description = "" ,required=true) @RequestHeader(value="Signature-Certificate", required=true) String signatureCertificate
- @Parameter(in = ParameterIn.HEADER, description = "" ,required=true,schema=@Schema()) @RequestHeader(value="Signature", required=true) String signature
- @Parameter(in = ParameterIn.DEFAULT, description = "The payment initiation request body", required=true) @Valid @RequestBody PaymentInitiationRequest body  

`
curl --location --request POST 'localhost:8080/v1.0.0/initiate-payment' \
--header 'X-Request-Id: 29318e25-cebd-498c-888a-f77672f66450' \
--header 'Signature-Certificate: -----BEGIN CERTIFICATE-----\nMIIDwjCCAqoCCQDxVbCjIKynQjANBgkqhkiG9w0BAQsFADCBojELMAkGA1UEBhMC\nTkwxEDAOBgNVBAgMB1V0cmVjaHQxEDAOBgNVBAcMB1V0cmVjaHQxETAPBgNVBAoM\nCFJhYm9iYW5rMRMwEQYDVQQLDApBc3Nlc3NtZW50MSIwIAYDVQQDDBlTYW5kYm94\nLVRQUDpleGNlbGxlbnQgVFBQMSMwIQYJKoZIhvcNAQkBFhRuby1yZXBseUByYWJv\nYmFuay5ubDAeFw0yMDAxMzAxMzIyNDlaFw0yMTAxMjkxMzIyNDlaMIGiMQswCQYD\nVQQGEwJOTDEQMA4GA1UECAwHVXRyZWNodDEQMA4GA1UEBwwHVXRyZWNodDERMA8G\nA1UECgwIUmFib2JhbmsxEzARBgNVBAsMCkFzc2Vzc21lbnQxIjAgBgNVBAMMGVNh\nbmRib3gtVFBQOmV4Y2VsbGVudCBUUFAxIzAhBgkqhkiG9w0BCQEWFG5vLXJlcGx5\nQHJhYm9iYW5rLm5sMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAryLy\nouTQr1dvMT4qvek0eZsh8g0DQQLlOgBzZwx7iInxYEAgMNxCKXiZCbmWHBYqh6lp\nPh+BBmrnBQzB+qrSNIyd4bFhfUlQ+htK08yyL9g4nyLt0LeKuxoaVWpInrB5FRzo\nEY5PPpcEXSObgr+pM71AvyJtQLxZbqTao4S7TRKecUm32Wwg+FWY/StSKlox3QmE\naxEGU7aPkaQfQs4hrtuUePwKrbkQ2hQdMpvI5oXRWzTqafvEQvND+IyLvZRqf0TS\nvIwsgtJd2tch2kqPoUwng3AmUFleJbMjFNzrWM7TH9LkKPItYtSuMTzeSe9o0SmX\nZFgcEBh5DnETZqIVuQIDAQABMA0GCSqGSIb3DQEBCwUAA4IBAQASFOkJiKQuL7fS\nErH6y5Uwj9WmmQLFnit85tjbo20jsqseTuZqLdpwBObiHxnBz7o3M73PJAXdoXkw\niMVykZrlUSEy7+FsNZ4iFppoFapHDbfBgM2WMV7VS6NK17e0zXcTGySSRzXsxw0y\nEQGaOU8RJ3Rry0HWo9M/JmYFrdBPP/3sWAt/+O4th5Jyk8RajN3fHFCAoUz4rXxh\nUZkf/9u3Q038rRBvqaA+6c0uW58XqF/QyUxuTD4er9veCniUhwIX4XBsDNxIW/rw\nBRAxOUkG4V+XqrBb75lCyea1o/9HIaq1iIKI4Day0piMOgwPEg1wF383yd0x8hRW\n4zxyHcER\n-----END CERTIFICATE-----' \
--header 'Signature: WHjIGjM9ONl40W5VPfwu63f47cGFE36ZLfl16tdkDndRF7JYauMDRj5yusfuW7aiExkk+bU3lttDLlb4lYhuX0D6Y0M3cofm0Rmz7o7pQJw4t5uyvkHtnLLATmnv+iZIRVw7XcSgQZ9nmjzpCqo8qn4bm4v6MdxuGwOzz97ipU/NRav6qAF9o1bQzsYysnsEVkDWNfQmCfNTESrY62nitVR6zeGeKtOwuaIVAgHFNDEYaIi77UNnAIZ7ZbKBCGfaFW80vKb+oJzjCjFDUWp2saUmhahxBIBZSMMzVdp6N5+2uAM4boVeYMIqqEAVuPlwOKrt8bXuxRSrAUTdrYuVSg==' \
--header 'Content-Type: application/json' \
--data-raw '{
"debtorIBAN": "NL02RABO7134384551",  
"creditorIBAN": "NL94ABNA1008270121",   
"amount": "11",
"currency": "EUR",
"endToEndId": "endToEndId"
}'
`
2. POST  `localhost:8080/v1.0.0/get-signature` Extra implementation in order to get successful response 201 created.

- @Parameter(in = ParameterIn.HEADER, description = "" ,required=true) @RequestHeader(value="X-Request-Id", required=true) UUID xRequestId
- @Parameter(in = ParameterIn.DEFAULT, description = "The payment initiation request body", required=true) @Valid @RequestBody PaymentInitiationRequest body


`
curl --location --request POST 'localhost:8080/v1.0.0/get-signature' \
--header 'X-Request-Id: 29318e25-cebd-498c-888a-f77672f66500' \
--header 'Content-Type: application/json' \
--data-raw '{
"debtorIBAN": "NL02RABO7134384113",
"creditorIBAN": "NL02RABO7134384113",
"amount": "500",
"currency": "EUR",
"endToEndId": "endToEndId"
}'
`




