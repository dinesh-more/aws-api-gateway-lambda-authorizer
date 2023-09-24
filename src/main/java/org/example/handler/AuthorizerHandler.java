package org.example.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.example.model.AuthPolicy;
import org.example.model.TokenAuthorizerContext;

import java.util.Optional;

/**
 * @author dinesh-more
 */
public class AuthorizerHandler implements RequestHandler<TokenAuthorizerContext, AuthPolicy> {
    @Override
    public AuthPolicy handleRequest(TokenAuthorizerContext tokenAuthorizerContext, Context context) {
        System.out.println("[AuthorizerHandler] TokenAuthorizerContext: "+tokenAuthorizerContext);
        String token = tokenAuthorizerContext.getAuthorizationToken();

        // Validate the incoming token and produce the principal user identifier associated with the token
        Optional<String> principalId = validateTokenAndGetPrincipalId(token);

        return principalId.map(principal -> {
            // If the token is valid, generate a policy that allows access
            String methodArn = tokenAuthorizerContext.getMethodArn();
            String[] arnPartials = methodArn.split(":");
            String region = arnPartials[3];
            String awsAccountId = arnPartials[4];
            String[] apiGatewayArnPartials = arnPartials[5].split("/");
            String restApiId = apiGatewayArnPartials[0];
            String stage = apiGatewayArnPartials[1];

            // Create and return a policy that allows access
            return new AuthPolicy(principal, AuthPolicy.PolicyDocument.getAllowAllPolicy(region, awsAccountId, restApiId, stage));
        }).orElseThrow(() -> new RuntimeException("Unauthorized")); // If token is invalid, throw Unauthorized exception
    }

    private Optional<String> validateTokenAndGetPrincipalId(String token) {
        // Implement your token validation logic here
        // For example, you can decode a JWT token or query a database

        // If validation succeeds, return the principalId; otherwise, return empty
        if (isValidToken(token)) {
            return Optional.of("dinesh-more");
        } else {
            return Optional.empty();
        }
    }

    private boolean isValidToken(String token) {
        // Implement your token validation logic here
        // Return true if the token is valid; otherwise, return false

        // Check if the token is not null and has a valid format
        return token != null && token.matches("static-secret");
    }
}
