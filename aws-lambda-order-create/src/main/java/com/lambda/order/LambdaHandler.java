package com.lambda.order;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.lambda.order.model.Order;
import com.lambda.order.model.Request;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class LambdaHandler implements RequestHandler<Request, Object> {

    @Override
    public Object handleRequest(Request request, Context context) {

        AmazonDynamoDB db = AmazonDynamoDBClientBuilder.defaultClient();
        DynamoDBMapper mapper = new DynamoDBMapper(db);
        Order o = request.getOrder();
        if (o != null) {
            o.setId(ThreadLocalRandom.current().nextLong(0, 10000L));
            mapper.save(o);
            return o;
        }
        return null;
    }
}
