package org.example.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeAction;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import org.example.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class ProductRepository {

    private final AmazonDynamoDB ddb;
    private final String TABLE_NAME;

    public ProductRepository(@Autowired AmazonDynamoDB ddb, @Value("${TABLE}") String TABLE_NAME) {
        this.ddb = ddb;
        this.TABLE_NAME = TABLE_NAME;
    }

    public Product getProduct(String productId) {
        Map<String, AttributeValue> answ = ddb.getItem(TABLE_NAME, idAsMap(productId)).getItem();
        System.out.println(answ);
        return answ == null || answ.isEmpty() ? null : parseMapToProduct(answ);
    }

    public Product addProduct(Product product) {
        Map<String, AttributeValue> map = parseProductWithoutIdToMap(product);
        ddb.putItem(TABLE_NAME, parseProductWithoutIdToMap(product));
        return parseMapToProduct(map);
    }

    public Product updateProduct(Product product) {
        Map<String, AttributeValue> map = ddb.updateItem(
                TABLE_NAME,
                idAsMap(product.getProductId()),
                createUpdateMap(product),
                ReturnValue.ALL_NEW.toString()
        ).getAttributes();
        return parseMapToProduct(map);
    }

    public void deleteProduct(String productId) {
        ddb.deleteItem(TABLE_NAME, idAsMap(productId));
    }

    private static Map<String, AttributeValue> idAsMap(String productId) {
        return Map.of("productId", new AttributeValue().withS(productId));
    }

    private static Product parseMapToProduct(Map<String, AttributeValue> item) {
        return new Product(
                item.get("productId").getS(),
                item.get("name").getS(),
                Long.parseLong(item.get("price").getS())
        );
    }

    private static Map<String, AttributeValue> parseProductWithoutIdToMap(Product product) {
        Map<String, AttributeValue> acc = new HashMap<>();
        acc.put("name", new AttributeValue(product.getName()));
        acc.put("price", new AttributeValue(product.getPrice().toString()));
        acc.put("productId", new AttributeValue(UUID.randomUUID().toString()));
        return acc;
    }

    private static Map<String, AttributeValueUpdate> createUpdateMap(Product product) {
        Map<String, AttributeValueUpdate> acc = new HashMap<>();
        acc.put("name", new AttributeValueUpdate(new AttributeValue(product.getName()), AttributeAction.PUT));
        acc.put("price", new AttributeValueUpdate(new AttributeValue(product.getPrice().toString()), AttributeAction.PUT));
        return acc;
    }

}
