package org.example.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.AppConfig;
import org.example.entity.Product;
import org.example.service.ProductService;

public class GetHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final String ID_PATH_KEY = "id";

    private final ProductService service;

    public GetHandler() {
        service = AppConfig.getContext().getBean(ProductService.class);
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        try {
            String answ = service.getProduct(input.getPathParameters().get(ID_PATH_KEY));
            if (answ == null) {
                return new APIGatewayProxyResponseEvent()
                        .withStatusCode(404);
            } else {
                return new APIGatewayProxyResponseEvent()
                        .withStatusCode(200)
                        .withBody(answ);
            }
        } catch (JsonProcessingException e) {
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(500);
        }
    }

}
