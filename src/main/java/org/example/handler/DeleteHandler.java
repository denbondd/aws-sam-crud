package org.example.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.example.AppConfig;
import org.example.service.ProductService;

public class DeleteHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final String ID_PATH_KEY = "id";
    private final ProductService service;

    public DeleteHandler() {
        service = AppConfig.getContext().getBean(ProductService.class);
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        service.deleteProduct(input.getPathParameters().get(ID_PATH_KEY));
        return new APIGatewayProxyResponseEvent().withStatusCode(200);
    }
}
