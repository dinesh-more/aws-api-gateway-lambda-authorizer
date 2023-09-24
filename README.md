# aws-api-gateway-lambda-authorizer

# InputRequest
{
    "type":"TOKEN",
    "authorizationToken":"<Incoming bearer token>",
    "methodArn":"arn:aws:execute-api:<Region id>:<Account id>:<API id>/<Stage>/<Method>/<Resource path>"
}

//
{
"type":"TOKEN",
"authorizationToken":"static-secret",
"methodArn":"arn:aws:execute-api:us-east-1:030835415248:zqz01civl3/dev/POST/serverless"
}