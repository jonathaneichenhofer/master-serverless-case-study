package com.lambda.example.customer;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.lambda.example.customer.model.Customer;
import com.lambda.example.customer.model.Request;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class LambdaHandler implements RequestHandler<Request, Object> {

    @Override
    public Object handleRequest(Request request, Context context) {
        AmazonDynamoDB db = AmazonDynamoDBClientBuilder.defaultClient();
        DynamoDBMapper mapper = new DynamoDBMapper(db);
        Customer c = request.getCustomer();
        
        if (c != null) {
            c.setId(ThreadLocalRandom.current().nextLong(0, 10000));
            mapper.save(c);
        }
        return c;
    }
}
