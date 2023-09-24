# aws-api-gateway-lambda-authorizer

# InputRequest
{
    "type":"TOKEN",
    "authorizationToken":"<Incoming bearer token>",
    "methodArn":"arn:aws:execute-api:<Region id>:<Account id>:<API id>/<Stage>/<Method>/<Resource path>"
}