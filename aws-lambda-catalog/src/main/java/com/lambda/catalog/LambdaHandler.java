package com.lambda.catalog;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.lambda.catalog.model.Item;
import com.lambda.catalog.model.Request;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;


public class LambdaHandler implements RequestHandler<Request, Object> {

    @Override
    public Object handleRequest(Request request, Context context) {


        AmazonDynamoDB db = AmazonDynamoDBClientBuilder.defaultClient();
        DynamoDBMapper mapper = new DynamoDBMapper(db);
        Item i = null;

        switch (request.getHttpMethod()){
            case "GET":
                if(request.getId() == null && request.getName() == null){
                    List<Item> items;
                    items = mapper.scan(Item.class, new DynamoDBScanExpression());
                    return items;
                }else {
                    if(request.getName() != null){
                        List<Item> items;
                        items = mapper.scan(Item.class, new DynamoDBScanExpression());
                        return items.stream().filter(c -> c.getName().equals(request.getName()))
                                .collect(Collectors.toList());
                    }else{
                        i = mapper.load(Item.class, request.getId());
                        return i;
                    }
                }
            case "POST":
                i = request.getItem();
                if(i != null){
                    i.setId(ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE));
                    mapper.save(i);
                }
                return i;
            case "DELETE":
                i = mapper.load(Item.class, request.getId());
                if (i != null)
                    mapper.delete(i);
                return i;
        }
        return null;
    }

}
